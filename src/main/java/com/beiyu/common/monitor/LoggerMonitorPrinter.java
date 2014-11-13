package com.beiyu.common.monitor;

import java.util.Formatter;

import org.slf4j.Logger;
/**
 * 日志系统的打印器
 * @author beiyu.lwm
 *
 */
public class LoggerMonitorPrinter implements MonitorPrinter{
	private Logger logger;
    private LogLevel logLevel;

    public LoggerMonitorPrinter(Logger logger, LogLevel logLevel) {
        this.logger = logger;
        this.logLevel = logLevel;
    }

    public LoggerMonitorPrinter(Logger logger, String logLevel) {
        this.logger = logger;
        this.logLevel = LogLevel.getLogLevel(logLevel);
    }

    public void print(MonitorProcessor monitorProcess) {
        if (logger != null) {
            logLevel.log(this, monitorProcess);
        }
    }

    private String getContent(MonitorProcessor monitorProcess) {
        Formatter f = new Formatter();
        monitorProcess.printHeader(f);
        monitorProcess.printContent(f);
        return f.out().toString();
    }

    public static enum LogLevel {
        TRACE() {
            public void log(LoggerMonitorPrinter printer, MonitorProcessor monitorProcess) {
                if (printer.logger.isTraceEnabled()) {
                    printer.logger.trace(printer.getContent(monitorProcess));
                }
            }
        }, DEBUG() {
            public void log(LoggerMonitorPrinter printer, MonitorProcessor monitorProcess) {
                if (printer.logger.isDebugEnabled()) {
                    printer.logger.debug(printer.getContent(monitorProcess));
                }
            }
        }, INFO() {
            public void log(LoggerMonitorPrinter printer, MonitorProcessor monitorProcess) {
                if (printer.logger.isInfoEnabled()) {
                    printer.logger.info(printer.getContent(monitorProcess));
                }
            }
        }, WARN() {
            public void log(LoggerMonitorPrinter printer, MonitorProcessor monitorProcess) {
                if (printer.logger.isWarnEnabled()) {
                    printer.logger.warn(printer.getContent(monitorProcess));
                }
            }
        }, ERROR() {
            public void log(LoggerMonitorPrinter printer, MonitorProcessor monitorProcess) {
                if (printer.logger.isErrorEnabled()) {
                    printer.logger.error(printer.getContent(monitorProcess));
                }
            }
        };

        public void log(LoggerMonitorPrinter printer, MonitorProcessor monitorProcess) {
            if (printer.logger.isInfoEnabled()) {
                printer.logger.info(printer.getContent(monitorProcess));
            }
        }

        public static LogLevel getLogLevel(String logLevel) {

            if (logLevel == null || logLevel.trim().equals("")) {
                return INFO;
            }
            logLevel = logLevel.trim().toUpperCase();
            if (TRACE.name().equals(logLevel)) {
                return TRACE;
            } else if (DEBUG.name().equals(logLevel)) {
                return DEBUG;
            } else if (WARN.name().equals(logLevel)) {
                return WARN;
            } else if (INFO.name().equals(logLevel)) {
                return INFO;
            } else if (ERROR.name().equals(logLevel)) {
                return ERROR;
            } else {
                return INFO;
            }
        }
    }
}
