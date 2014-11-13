package com.beiyu.common.monitor;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.adapter.AdvisorAdapterRegistry;
import org.springframework.aop.framework.adapter.GlobalAdvisorAdapterRegistry;
import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
import org.springframework.beans.BeansException;

public class MonitorAutoProxyCreator extends AbstractAutoProxyCreator {
    /**
     * Default is global AdvisorAdapterRegistry
     */
    private AdvisorAdapterRegistry advisorAdapterRegistry = GlobalAdvisorAdapterRegistry.getInstance();

    @Override
    protected Object[] getAdvicesAndAdvisorsForBean(Class beanClass, String beanName, TargetSource customTargetSource) throws BeansException {
        List<Class<?>> list = new LinkedList<Class<?>>();
        //��ȡBean���м̳еĳ����ʵ�ֵĽӿ�
        ClassUtils.getAllInterface(beanClass, list);
        ClassUtils.getAllSuperClass(beanClass, list);
        list.add(beanClass);

        //��ȡ������Ҫ������Method��Ӧ��Monitor��Ϣ
        Map<String, Monitor> monitorMap = new HashMap<String, Monitor>();
        for (Class<?> clazz : list) {
            Monitor monitor = clazz.getAnnotation(Monitor.class);
            if (monitor != null) {
                return PROXY_WITHOUT_ADDITIONAL_INTERCEPTORS;
            }
        }
        return DO_NOT_PROXY;
    }
}