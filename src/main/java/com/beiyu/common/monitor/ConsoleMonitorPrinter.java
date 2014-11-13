package com.beiyu.common.monitor;

import java.util.Formatter;

/**
 * 把日志打印到控制台
 * @author beiyu.lwm
 *
 */
public class ConsoleMonitorPrinter implements MonitorPrinter {

    public void print(MonitorProcessor monitorProcess) {
        Formatter f = new Formatter();
        monitorProcess.printHeader(f);
        monitorProcess.printContent(f);
        f.flush();
        System.out.println(f.out());
    }

}
