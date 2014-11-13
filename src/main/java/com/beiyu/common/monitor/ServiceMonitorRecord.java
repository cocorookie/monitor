package com.beiyu.common.monitor;

import java.util.concurrent.atomic.AtomicInteger;
/**
 * 对于服务的监控记录，会记录该服务成功次数，失败次数，成功率等
 * @author beiyu.lwm
 *
 */
public class ServiceMonitorRecord implements MonitorRecord<Long> {
    private String monitorName;
    private Calculator costCalculator;
    private AtomicInteger success;
    private AtomicInteger failure;

    public ServiceMonitorRecord(String monitorName) {
        this.monitorName = monitorName;
        costCalculator = new LongCalculator();
        success = new AtomicInteger();
        failure = new AtomicInteger();
    }

    public void success(Long cost) {
        costCalculator.add(cost);
        success.incrementAndGet();
    }

    public void failed() {
        failure.incrementAndGet();
    }

    public void failure(Long cost) {
        costCalculator.add(cost);
        failure.incrementAndGet();
    }

    public void clear() {
        costCalculator.reset();
        success.set(0);
        failure.set(0);
    }

    public String getMonitorName() {
        return monitorName;
    }

    /**
     * 总调用次数
     *
     * @return
     */
    public int getSum() {
        return success.get() + failure.get();
    }

    public int getSuccess() {
        return success.get();
    }

    public int getFailure() {
        return failure.get();
    }

    public float getSuccessPercent() {
        int sum = getSum();
        if (sum == 0) {
            return 0;
        }
        return ((float) getSuccess() / (float) sum) * 100;
    }

    public float getFailurePercent() {
        int sum = getSum();
        if (sum == 0) {
            return 0;
        }
        return ((float) getFailure() / (float) getSum()) * 100;
    }

    public Calculator getCalculator() {
        return costCalculator;
    }

    public int compareTo(MonitorRecord<Long> o) {
        if (monitorName != null && o.getMonitorName() != null) {
            return monitorName.compareTo(o.getMonitorName());
        }
        return 0;
    }
}
