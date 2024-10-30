package com.pub.filter;

import java.io.ByteArrayOutputStream; 
import java.io.IOException; 
import java.io.PrintWriter; 
import javax.servlet.http.HttpServletResponse; 
import javax.servlet.http.HttpServletResponseWrapper ;

import org.apache.log4j.Logger;

/** 
* @see http://bianbian.sunshow.net/ 
* @author dannyzhu, bianbian 
* @version 1.0 
*/ 
public class WrapperResponse extends HttpServletResponseWrapper 
{ 
	Logger logger = Logger.getLogger("log.service");
	
    private MyPrintWriter	 tmpWriter;
    private ByteArrayOutputStream output;
    
    public WrapperResponse(HttpServletResponse httpServletResponse)
    {
	super(httpServletResponse);
	output = new ByteArrayOutputStream();
	tmpWriter = new MyPrintWriter(output);
    }

    @Override
	public void finalize() throws Throwable
    {
	super.finalize();
	output.close();
	tmpWriter.close();
    }

    public String getContent()
    {
	try
	{
	    tmpWriter.flush(); // ˢ�¸����Ļ��壬�꿴java.io.Writer.flush()

	   
	    String s = tmpWriter.getByteArrayOutputStream().toString("GBK");

	    // �˴��ɸ�����Ҫ���ж�������Լ�Writer�����ò���
	    // ����tmpWriter.getByteArrayOutputStream().reset()
	    return s;
	}
	catch (Exception e)
	{
	    return "UnsupportedEncoding";
	}
    }

    // ����getWriter()������ʹ�������Լ������Writer
    @Override
	public PrintWriter getWriter() throws IOException
    {
	return tmpWriter;
    }

    public void close() throws IOException
    {
	tmpWriter.close();
    }

    private static class MyPrintWriter extends PrintWriter
    {
	ByteArrayOutputStream myOutput; //�˼�Ϊ���response�������Ķ���

	public MyPrintWriter(ByteArrayOutputStream output)
	{
	    super(output);
	    myOutput = output;
	}
	public ByteArrayOutputStream getByteArrayOutputStream()
	{
	    return myOutput;
	}
    }
  
}
