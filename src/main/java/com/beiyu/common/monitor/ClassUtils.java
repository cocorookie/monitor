package com.beiyu.common.monitor;

import java.util.LinkedList;
import java.util.List;

public class ClassUtils {
	 public static List<Class<?>> getAllSuperClass(Class<?> clazz) {
        List<Class<?>> list = new LinkedList<Class<?>>();
        getAllSuperClass(clazz, list);
        return list;
    }

    public static void getAllSuperClass(Class<?> clazz, List<Class<?>> list) {
        if (clazz != null) {
            Class<?> superClass = clazz.getSuperclass();
            if (superClass != null) {
                list.add(superClass);
                getAllSuperClass(superClass, list);
            }
        }
    }

    public static List<Class<?>> getAllInterface(Class<?> clazz) {
        List<Class<?>> list = new LinkedList<Class<?>>();
        getAllInterface(clazz, list);
        return list;
    }

    public static void getAllInterface(Class<?> clazz, List<Class<?>> list) {
        if (clazz != null) {
            Class<?>[] interfaceClasses = clazz.getInterfaces();
            for (int i = 0; i < interfaceClasses.length; i++) {
                Class<?> interfaceClass = interfaceClasses[i];
                list.add(interfaceClass);
                getAllInterface(interfaceClass, list);
            }
        }
    }
}
