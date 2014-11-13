package com.beiyu.common.monitor;
/**
 * 所有监控记录的打印接口
 * @author beiyu.lwm
 *
 */
public interface MonitorPrinter {
	/**
	 * 会打印processor里的所有MonitorRecord记录
	 * @param monitorProcess
	 */
    public void print(MonitorProcessor monitorProcess);
}
