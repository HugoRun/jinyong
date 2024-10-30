package com.pub.filter;

import javax.servlet.*;
import java.io.IOException;
import java.util.Date;

public class FilterDisplayFilter implements Filter {

    public void destroy() {


    }

    public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws IOException, ServletException {
        Date dt = new Date();


    }

    public void init(FilterConfig arg0) throws ServletException {

    }

}
