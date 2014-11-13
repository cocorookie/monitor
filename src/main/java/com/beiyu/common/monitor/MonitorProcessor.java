package com.beiyu.common.monitor;

import java.util.Formatter;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicReference;
/**
 * 监控记录的处理器
 * 维护了一个所有监控记录的Map
 * 这里维护了记录的新建与更新，同时还会处理记录的打印操作
 * @author beiyu.lwm
 *
 */
public abstract class MonitorProcessor {
    private Map<String, AtomicReference<MonitorRecord>> monitorMap;


    public MonitorProcessor() {
        monitorMap = new ConcurrentSkipListMap<String, AtomicReference<MonitorRecord>>();
    }

    public void success(String monitorName, long costTime) {
        MonitorRecord record = getMonitorRecord(monitorName);
        if (record != null) {
            record.success(costTime);
        }
    }

    public void failure(String monitorName, long costTime) {
        MonitorRecord record = getMonitorRecord(monitorName);
        if (record != null) {
            record.failure(costTime);
        }
    }

    public Iterator<MonitorRecord> getMonitorRecords() {
        final Iterator<String> values = monitorMap.keySet().iterator();
        return new Iterator<MonitorRecord>() {
            public boolean hasNext() {
                return values.hasNext();
            }

            public MonitorRecord next() {
                String key = values.next();
                if (key != null) {
                    return monitorMap.get(key).get();
                } else {
                    return null;
                }
            }

            public void remove() {
                values.remove();
            }
        };
    }


    private MonitorRecord getMonitorRecord(String monitorName) {
        MonitorRecord oldRecord = null;
        MonitorRecord newRecord = null;
        while (true) {
            AtomicReference<MonitorRecord> ref = monitorMap.get(monitorName);
            if (ref == null) {
                newRecord = new ServiceMonitorRecord(monitorName);
                ref = new AtomicReference<MonitorRecord>();
            } else {
                newRecord = ref.get();
                break;
            }

            if (ref.compareAndSet(null, newRecord)) {
                monitorMap.put(monitorName, ref);
                break;
            }
        }
        return newRecord;
    }

    public void clear() {
        for (AtomicReference<MonitorRecord> ref : monitorMap.values()) {
            MonitorRecord record = ref.get();
            if (record != null) {
                record.clear();
            }
        }
    }


    public abstract void printHeader(Formatter formatter);

    public abstract void printContent(Formatter formatter);
}
