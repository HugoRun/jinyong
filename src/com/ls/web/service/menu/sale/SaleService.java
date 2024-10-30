package com.ls.web.service.menu.sale;

import java.sql.Connection;

import org.apache.log4j.Logger;

import com.ls.web.service.menu.MenuService;

/**
 * 功能:卖出操作
 * @author 刘帅
 * 8:28:00 AM
 */
public class SaleService
{
	Connection jygameConn = null;
	Connection jygameUserConn = null;

	public static Logger logger = Logger.getLogger("log.service");

	public SaleService(Connection jygameConn) {
		this.jygameConn = jygameConn;
	}

	public SaleService(Connection jygameConn, Connection jygameUserConn) {
		this.jygameConn = jygameConn;
		this.jygameUserConn = jygameUserConn;
	}
	


}
