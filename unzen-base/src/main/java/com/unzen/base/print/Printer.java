package com.unzen.base.print;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author langhsu on 2015/9/6.
 */
public class Printer {
    private static Logger log = LoggerFactory.getLogger("mblog");

    public static void info(String message) {
        log.info(message);
    }

    public static void warn(String message) {
        log.warn(message);
    }

    public static void error(String message) {
        log.error(message);
    }

    public static void error(String message, Throwable t) {
        log.error(message, t);
    }
}
