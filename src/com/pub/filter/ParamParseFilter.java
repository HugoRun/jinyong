package com.pub.filter;

import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.Channel;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class ParamParseFilter implements Filter {

    public void destroy() {
        // TODO Auto-generated method stub

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        // 空中网解析参数
        if (GameConfig.getChannelId() == Channel.AIR) {
            HttpServletRequest request = (HttpServletRequest) servletRequest;

            java.io.BufferedReader read = request.getReader();
            String line = read.readLine();
            if (line != null) {
                ParameterRequestWrapper requestWrapper = new ParameterRequestWrapper(request, line);
                chain.doFilter(requestWrapper, servletResponse);
                return;
            }
        }
        chain.doFilter(servletRequest, servletResponse);
    }

    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub

    }

}
