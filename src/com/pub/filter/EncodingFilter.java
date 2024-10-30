package com.pub.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;


/**
 * <p>Title: ϵͳ</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: zznode</p>
 * @author  
 * @CreatedTime: 2007-10-31
 * @version 1.0
 */
public class EncodingFilter implements Filter {

    private FilterConfig filterCfg;

    Logger logger = Logger.getLogger("log.servcie");
    /**
     * 
     */
    public EncodingFilter() {
        super();
        // TODO �Զ����ɹ��캯�����
    }

    /* ���� Javadoc��
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    public void init(FilterConfig arg0) throws ServletException {
        this.filterCfg = arg0;
    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain filterChain) {
    	HttpServletRequest req = (HttpServletRequest) request;
    	
    	long begin_time = System.currentTimeMillis();
    	logger.debug("#####�����������Ӧ��ʼʱ��:"+ (begin_time)+"#####");
    	//System.out.println("#####�����������Ӧ��ʼʱ��:"+ (begin_time)+"#####");
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
        logger.debug("#####�����������Ӧ����ʱ��:"+ (begin_time)+"#####");
        logger.debug("#####�����������Ӧʱ��:"+ (end_time-begin_time)+"����#####");
        //System.out.println("#####�����������Ӧ����ʱ��:"+ (begin_time)+"#####");
       //System.out.println("#####�����������Ӧʱ��:"+ (end_time-begin_time)+"����#####");
    }
    public void destroy() {
        // TODO �Զ����ɷ������

    }

}