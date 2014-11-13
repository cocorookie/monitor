package com.beiyu.common.monitor;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;

/**
 * 标识需要的监控Pointcut<br/>
 * 当作用在接口/类上时表明整个类的所有方法需要被监控<br/>
 *
 * @author liwenmao88@gmail.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Monitor {

    /**
     * 是否忽略该监控项<br/>
     * 该参数同时在Type和Method上生效 默认为false，即需要监控；为true时表明不需要监控
     *
     * @return
     */
    boolean ignore() default false;

    /**
     * 监控方法前面打印prefix
     *
     * @return
     */
    String prefix() default "";

}