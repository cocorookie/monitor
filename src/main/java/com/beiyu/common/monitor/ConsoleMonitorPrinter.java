package com.beiyu.common.monitor;

import java.util.Formatter;

/**
 * ����־��ӡ������̨
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
