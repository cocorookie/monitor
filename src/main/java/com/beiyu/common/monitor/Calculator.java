package com.beiyu.common.monitor;

/**
 * ͳ���������ݵļ����������Ը��ݾ������������ѡ���Ӧ�ļ�����
 * @author beiyu.lwm
 */
public interface Calculator<T> {

    /**
     * ���һ������
     *
     * @param value
     */
    public void add(T value);

    /**
     * ��ȡ��������
     *
     * @return
     */
    public T getSum();

    /**
     * ��ȡƽ����
     *
     * @return
     */
    public float getAvg();

    /**
     * ��ȡ�����
     *
     * @return
     */
    public T getMax();

    /**
     * ��ȡ��С��
     *
     * @return
     */
    public T getMin();

    /**
     * ��ȡ��ӵĸ���
     *
     * @return
     */
    public int getCount();

    /**
     * ��������
     */
    public void reset();
}
