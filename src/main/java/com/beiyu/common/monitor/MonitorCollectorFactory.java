package com.beiyu.common.monitor;

public class MonitorCollectorFactory {
	public static MonitorCollectorFactory factory = new MonitorCollectorFactory();

    public MonitorCollector getServiceMonitorCollector(long printPeriod, boolean isDaemon, MonitorPrinter printer) throws Exception {
        return new MonitorCollector(printPeriod, isDaemon, printer, ServiceMonitorProcessor.class);
    }
}
