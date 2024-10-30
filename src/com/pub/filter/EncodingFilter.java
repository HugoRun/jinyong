package com.pub.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;


/**
 * <p>Title: 系统</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: zznode</p>
 *
 * @author
 * @version 1.0
 * @CreatedTime: 2007-10-31
 */
public class EncodingFilter implements Filter {

    Logger logger = Logger.getLogger("log.servcie");
    private FilterConfig filterCfg;

    /**
     *
     */
    public EncodingFilter() {
        super();
        // TODO 自动生成构造函数存根
    }

    /* （非 Javadoc）
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    public void init(FilterConfig arg0) throws ServletException {
        this.filterCfg = arg0;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) {
        HttpServletRequest req = (HttpServletRequest) request;

        long begin_time = System.currentTimeMillis();
        logger.debug("#####本次请求的响应开始时间:" + (begin_time) + "#####");
        // System.out.println("#####本次请求的响应开始时间:"+ (begin_time)+"#####");
        try {

            /*java.io.BufferedReader read = req.getReader();
            String line = read.readLine();
                String[] params = line.split("&");
                String[] temp = null;
            if( params!=null )
            {
                for(int i=0;i<params.length;i++)
                {
                    temp = params[i].split("=");
                    if( temp.length==2)
                    {
                        request.setAttribute(temp[0],temp[1]);
                    }
                }
            }*/

            String encoding = filterCfg.getInitParameter("encoding");
            if (encoding == null || "".equals(encoding)) {
                HttpServletRequest hreq = (HttpServletRequest) request;

                String clientEn = hreq.getHeader("accept-language");
                if ("zh-tw".equals(clientEn)) {
                    encoding = "Big5";
                } else if ("zh-cn".equals(clientEn)) {
                    encoding = "UTF-8";
                } else {
                    encoding = "ISO8859_1";
                }
            }
            request.setCharacterEncoding(encoding);
            filterChain.doFilter(request, response);

            long endTime = System.currentTimeMillis();
        } catch (Exception e) {
            e.printStackTrace();
        }
        long end_time = System.currentTimeMillis();
        logger.debug("#####本次请求的响应结束时间:" + (begin_time) + "#####");
        logger.debug("#####本次请求的响应时间:" + (end_time - begin_time) + "毫秒#####");
        // System.out.println("#####本次请求的响应结束时间:"+ (begin_time)+"#####");
        // System.out.println("#####本次请求的响应时间:"+ (end_time-begin_time)+"毫秒#####");
    }

    public void destroy() {
        // TODO 自动生成方法存根

    }

}
