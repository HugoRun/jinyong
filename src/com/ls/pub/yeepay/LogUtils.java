package com.ls.pub.yeepay;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * log宸ュ叿
 * @author lu.li
 *
 */
public class LogUtils {
	
	private static Log log = LogFactory.getLog(LogUtils.class);
	
	public static void debug(String msg) {
		log.debug(msg);
	}
	
	public static void error(String msg,Throwable e) {
		log.error(msg,e);
	}
	
	public static void info(String msg) {
		log.error(msg);
	}
}
