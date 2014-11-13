package com.beiyu.common.monitor;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;

/**
 * ��ʶ��Ҫ�ļ��Pointcut<br/>
 * �������ڽӿ�/����ʱ��������������з�����Ҫ�����<br/>
 *
 * @author liwenmao88@gmail.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Monitor {

    /**
     * �Ƿ���Ըü����<br/>
     * �ò���ͬʱ��Type��Method����Ч Ĭ��Ϊfalse������Ҫ��أ�Ϊtrueʱ��������Ҫ���
     *
     * @return
     */
    boolean ignore() default false;

    /**
     * ��ط���ǰ���ӡprefix
     *
     * @return
     */
    String prefix() default "";

}