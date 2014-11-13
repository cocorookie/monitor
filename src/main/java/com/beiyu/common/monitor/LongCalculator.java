package com.beiyu.common.monitor;

public class LongCalculator implements Calculator<Long>{
  	private long max;
    private long min;
    private long sum;
    private int count;

    public LongCalculator() {
        reset();
    }

    public void add(Long value) {
        sum += value;
        count++;
        max = Math.max(max, value);
        min = Math.min(min, value);
    }

    public Long getSum() {
        return sum;
    }

    public float getAvg() {
        if (count == 0) {
            return 0;
        }

        return (float) sum / (float) count;
    }

    public Long getMax() {
        if (max == Long.MIN_VALUE) {
            return Long.valueOf(0);
        }
        return max;
    }

    public Long getMin() {
        if (max == Long.MAX_VALUE) {
            return Long.valueOf(0);
        }
        return min;
    }

    public int getCount() {
        return count;
    }

    public void reset() {
        max = Long.MIN_VALUE;
        min = Long.MAX_VALUE;
        sum = 0;
        count = 0;
    }

    @Override
    public String toString() {
        return "max=" + getMax() +
                ", min=" + getMin() +
                ", avg=" + getAvg() +
                ", sum=" + getSum() +
                ", count=" + getCount();
    }
}
