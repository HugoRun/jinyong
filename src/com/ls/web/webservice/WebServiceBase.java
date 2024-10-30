package com.ls.web.webservice;

import java.awt.List;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;

/**
 * @author ls
 * webservice的父类，提供了一些发送json数据的基础功能
 */
public class WebServiceBase extends Action
{
	/**
	 * 发送数据
	 * @param response
	 * @param data
	 * @return
	 */
	private ActionForward sendData(HttpServletResponse response,String data)
	{
		try
		{
			response.getWriter().print(data);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 发送object数据
	 * @param response
	 * @param object
	 * @return
	 */
	public ActionForward sendJsonObject(HttpServletResponse response,Object object)
	{
		JSONObject json_object = JSONObject.fromObject( object );
		return this.sendData(response, json_object.toString());
	}
	
	/**
	 * 发送集合数据
	 * @param response
	 * @param list
	 * @return
	 */
	public ActionForward sendJsonArray(HttpServletResponse response,List list)
	{
		JSONArray json_array = JSONArray.fromObject( list );
		return this.sendData(response, json_array.toString());
	}
}
