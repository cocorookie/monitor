package com.beiyu.common.monitor;
/**
 * 一条监控监控记录
 * 例如对于一个方法的监控，就是一条监控记录
 * @author beiyu.lwm
 *
 * @param <T>
 */
public interface MonitorRecord<T> extends Comparable<MonitorRecord<T>> {

    public String getMonitorName();

    public void clear();

    public void success(T value);

    public void failure(T value);
}
