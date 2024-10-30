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
 * webservice�ĸ��࣬�ṩ��һЩ����json���ݵĻ�������
 */
public class WebServiceBase extends Action
{
	/**
	 * ��������
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
	 * ����object����
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
	 * ���ͼ�������
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
