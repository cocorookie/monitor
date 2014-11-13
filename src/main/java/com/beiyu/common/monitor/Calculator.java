package com.beiyu.common.monitor;

/**
 * 统计序列数据的计算器，可以根据具体的数据类型选择对应的计算器
 * @author beiyu.lwm
 */
public interface Calculator<T> {

    /**
     * 添加一条数据
     *
     * @param value
     */
    public void add(T value);

    /**
     * 获取汇总数据
     *
     * @return
     */
    public T getSum();

    /**
     * 获取平均数
     *
     * @return
     */
    public float getAvg();

    /**
     * 获取最大数
     *
     * @return
     */
    public T getMax();

    /**
     * 获取最小数
     *
     * @return
     */
    public T getMin();

    /**
     * 获取添加的个数
     *
     * @return
     */
    public int getCount();

    /**
     * 重置数据
     */
    public void reset();
}
