package com.pub.filter;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author dannyzhu, bianbian
 * @version 1.0
 * @see http://bianbian.sunshow.net/
 */
public class WrapperResponse extends HttpServletResponseWrapper {
    private final MyPrintWriter tmpWriter;
    private final ByteArrayOutputStream output;
    Logger logger = Logger.getLogger("log.service");

    public WrapperResponse(HttpServletResponse httpServletResponse) {
        super(httpServletResponse);
        output = new ByteArrayOutputStream();
        tmpWriter = new MyPrintWriter(output);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        output.close();
        tmpWriter.close();
    }

    public String getContent() {
        try {
            // 刷新该流的缓冲，详看java.io.Writer.flush()
            tmpWriter.flush();
            // 此处可根据需要进行对输出流以及Writer的重置操作
            // 比如tmpWriter.getByteArrayOutputStream().reset()
            return tmpWriter.getByteArrayOutputStream().toString("GBK");
        } catch (Exception e) {
            return "UnsupportedEncoding";
        }
    }

    // 覆盖getWriter()方法，使用我们自己定义的Writer
    @Override
    public PrintWriter getWriter() throws IOException {
        return tmpWriter;
    }

    public void close() throws IOException {
        tmpWriter.close();
    }

    private static class MyPrintWriter extends PrintWriter {
        // 此即为存放response输入流的对象
        ByteArrayOutputStream myOutput;

        public MyPrintWriter(ByteArrayOutputStream output) {
            super(output);
            myOutput = output;
        }

        public ByteArrayOutputStream getByteArrayOutputStream() {
            return myOutput;
        }
    }

}
