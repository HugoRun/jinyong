/**
 *
 */
package com.pub.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EncodeURLFilter implements Filter {
    private String encode = "UTF-8";

    public void destroy() {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // request.setCharacterEncoding(this.encode);
        // response.setContentType("text/html;charset= " + encode);
        HttpServletRequest httpServletRequest = ((HttpServletRequest) request);
        HttpServletResponse httpServletResponse = ((HttpServletResponse) response);
        httpServletResponse.encodeURL(httpServletRequest.getRequestURI());
        chain.doFilter(httpServletRequest, httpServletResponse);
    }

    public void init(FilterConfig config) throws ServletException {
        this.encode = config.getInitParameter("encode ");
    }

}
