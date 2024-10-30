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
	    tmpWriter.flush(); // 刷新该流的缓冲，详看java.io.Writer.flush()

	   
	    String s = tmpWriter.getByteArrayOutputStream().toString("GBK");

	    // 此处可根据需要进行对输出流以及Writer的重置操作
	    // 比如tmpWriter.getByteArrayOutputStream().reset()
	    return s;
	}
	catch (Exception e)
	{
	    return "UnsupportedEncoding";
	}
    }

    // 覆盖getWriter()方法，使用我们自己定义的Writer
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
	ByteArrayOutputStream myOutput; //此即为存放response输入流的对象

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
