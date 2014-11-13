package com.beiyu.common.monitor;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MonitorCollector {
private final static String MONITOR_PRINT_THREAD = "Auction_Monitor_Thread";

private MonitorProcessor current;
private MonitorProcessor next;
private MonitorPrinter printer;
private long printPeriod = 60000;//默认一分钟打印一次
private boolean isDaemon = true;
private ScheduledExecutorService scheduledExecutorService;

public MonitorCollector(long printPeriod, boolean isDaemon, MonitorPrinter printer, Class<? extends MonitorProcessor> processType) throws Exception {
    current = processType.newInstance();
    next = processType.newInstance();
    scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
    this.printPeriod = printPeriod;
    this.isDaemon = isDaemon;
    this.printer = printer;
    start();
}

/**
 * 启动Monitor日志输出定时任务
 */
    void start() {
        Thread printThread = new Thread() {
            @Override
            public void run() {
                print();
            }
        };
        printThread.setDaemon(isDaemon);
        printThread.setName(MONITOR_PRINT_THREAD);
        scheduledExecutorService.scheduleAtFixedRate(printThread, 1000, printPeriod, TimeUnit.MILLISECONDS);
    }


    public void success(String monitorName, long cost) {
        current.success(monitorName, cost);
    }

    public void failure(String monitorName, long cost) {
        current.failure(monitorName, cost);
    }

    public void print() {
        MonitorProcessor tmp = current;
        current = next;
        next = tmp;
        printer.print(next);
        next.clear();
    }

    public void stop() {
        scheduledExecutorService.shutdown();
    }
}
