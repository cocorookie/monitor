package com.beiyu.common.monitor;

import java.text.DecimalFormat;
import java.util.Formatter;
import java.util.Iterator;

public class ServiceMonitorProcessor extends MonitorProcessor {
    private static DecimalFormat avgFormat = new DecimalFormat("0.00");
    private static DecimalFormat percentFormat = new DecimalFormat("0.00");

    @Override
    public void printHeader(Formatter formatter) {
        formatter.format("%-80s %8s %8s %8s %8s %6s %8s\n", "monitor", "total", "success", "failure", "average", "max", "failPct");
    }

    @Override
    public void printContent(Formatter formatter) {
        Iterator<MonitorRecord> values = getMonitorRecords();
        while (values.hasNext()) {
            ServiceMonitorRecord record = (ServiceMonitorRecord) values.next();
            Calculator<?> cal = record.getCalculator();
            formatter.format("%-80s %8d %8d %8d %8s %6d %8s\n", record.getMonitorName(), record.getSum(), record.getSuccess(), record.getFailure(), avgFormat.format(cal.getAvg()), cal.getMax(), percentFormat.format(record.getFailurePercent()) + "%");
        }
    }
}
