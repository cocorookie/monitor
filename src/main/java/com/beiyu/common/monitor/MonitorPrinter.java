package com.beiyu.common.monitor;
/**
 * ���м�ؼ�¼�Ĵ�ӡ�ӿ�
 * @author beiyu.lwm
 *
 */
public interface MonitorPrinter {
	/**
	 * ���ӡprocessor�������MonitorRecord��¼
	 * @param monitorProcess
	 */
    public void print(MonitorProcessor monitorProcess);
}
