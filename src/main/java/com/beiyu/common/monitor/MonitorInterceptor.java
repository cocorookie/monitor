package com.beiyu.common.monitor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.adapter.AdvisorAdapterRegistry;
import org.springframework.aop.framework.adapter.GlobalAdvisorAdapterRegistry;

public class MonitorInterceptor implements MethodInterceptor {

    private AdvisorAdapterRegistry advisorAdapterRegistry = GlobalAdvisorAdapterRegistry.getInstance();

    private Map<Method, MonitorTarget> monitorCache;
    private MonitorCollector monitorCollector;

    public MonitorInterceptor(long period) throws Exception {
        monitorCache = new ConcurrentHashMap<Method, MonitorTarget>();
        monitorCollector = MonitorCollectorFactory.factory.getServiceMonitorCollector(period, false, new ConsoleMonitorPrinter());
    }

    public MonitorInterceptor(long period, String loggerName, String logLevel) throws Exception {
        monitorCache = new ConcurrentHashMap<Method, MonitorTarget>();
        Logger logger = LoggerFactory.getLogger(loggerName);
        LoggerMonitorPrinter printer = new LoggerMonitorPrinter(logger, logLevel);
        monitorCollector = MonitorCollectorFactory.factory.getServiceMonitorCollector(period, false, printer);
    }

    public Object invoke(MethodInvocation invocation) throws Throwable {
        MonitorTarget monitor = getMonitor(invocation.getMethod(), invocation.getThis().getClass());
        if (monitor == null || monitor.getMonitor() == null || monitor.getMonitor().ignore()) {
            return invocation.proceed();
        } else {
            long start = System.currentTimeMillis();
            long cost = 0;
            try {
                Object result = invocation.proceed();
                cost = System.currentTimeMillis() - start;
                monitorCollector.success(monitor.getTargetSign(), cost);
                return result;
            } catch (Throwable t) {
                monitorCollector.failure(monitor.getTargetSign(), cost);
                throw t;
            }
        }
    }

    private MonitorTarget getMonitor(Method method, Class<?> targetClass) {
        MonitorTarget target = monitorCache.get(method);

        if (target == null) {
            target = new MonitorTarget();
            Monitor monitor = getClassAnnotation(Monitor.class, targetClass, method);
            if (monitor != null) {
                target.setMonitor(monitor);
                target.setTargetSign(getMonitorTargetSign(monitor, method, targetClass));
            }
            monitorCache.put(method, target);
        }
        return target;
    }


    private <T extends Annotation> T getClassAnnotation(Class<T> annotationClass, Class<?> targetClass, Method method) {
        if (targetClass != null) {
            try {
                Method superMethod = targetClass.getDeclaredMethod(method.getName(), method.getParameterTypes());
                T annotation = superMethod.getAnnotation(annotationClass);
                if (annotation != null) {
                    return annotation;
                }
            } catch (NoSuchMethodException e) {
            }

            T annotation = targetClass.getAnnotation(annotationClass);
            if (annotation == null) {
                Class<?>[] interfaces = targetClass.getInterfaces();
                for (int i = 0; i < interfaces.length; i++) {
                    annotation = getClassAnnotation(annotationClass, interfaces[i], method);
                    if (annotation != null) {
                        return annotation;
                    }
                }
                annotation = getClassAnnotation(annotationClass, targetClass.getSuperclass(), method);
                if (annotation != null) {
                    return annotation;
                }
            } else {
                return annotation;
            }
        }
        return null;
    }

    private String getMonitorTargetSign(Monitor monitor, Method method, Class<?> targetClass) {
        String className = targetClass.getSimpleName();
        String methodName = method.getName();
        String argumentsAbbreviation = getArgumentsAbbreviation(method.getParameterTypes());

        String prefixString = className;
        String prefix = monitor.prefix();
        if (prefix != null && !prefix.equals("")) {
            prefixString = prefix;
        }

        StringBuilder builder = new StringBuilder(prefixString).append(":").append(methodName);
        if (argumentsAbbreviation != null && !argumentsAbbreviation.equals("")) {
            builder.append("~").append(argumentsAbbreviation);
        }
        return builder.toString();
    }

    private String getMonitorTargetSign(Monitor monitor, Class<?> invocationClass, Method method) {
        String className = invocationClass.getSimpleName();
        String methodName = method.getName();
        String argumentsAbbreviation = getArgumentsAbbreviation(method.getParameterTypes());

        StringBuilder builder = new StringBuilder(monitor.prefix()).append(className).append(":").append(methodName);
        if (argumentsAbbreviation != null && !argumentsAbbreviation.equals("")) {
            builder.append("~").append(argumentsAbbreviation);
        }
        return builder.toString();
    }

    private String getArgumentsAbbreviation(Class[] arguments) {
        if (arguments != null) {
            StringBuilder sb = new StringBuilder();
            for (Class argClass : arguments) {
                String classSimpleName = argClass.getSimpleName();
//                String abbreviation = nameMapping.get(classSimpleName);
//                if (StringUtil.isBlank(abbreviation)) {
                String abbreviation = classSimpleName.substring(0, 1).toUpperCase();
//                }
                sb.append(abbreviation);
            }
            return sb.toString();
        }
        return "";
    }

    class MonitorTarget {
        private Monitor monitor;
        private String targetSign;

        public Monitor getMonitor() {
            return monitor;
        }

        public void setMonitor(Monitor monitor) {
            this.monitor = monitor;
        }

        public String getTargetSign() {
            return targetSign;
        }

        public void setTargetSign(String targetSign) {
            this.targetSign = targetSign;
        }
    }
}
