package com.beiyu.common.monitor;
/**
 * һ����ؼ�ؼ�¼
 * �������һ�������ļ�أ�����һ����ؼ�¼
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
