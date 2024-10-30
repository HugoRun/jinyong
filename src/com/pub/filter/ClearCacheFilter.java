package com.pub.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>Title: 系统</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: zznode</p>
 *
 * @author
 * @version 1.0 dd鍗曠偣
 * @CreatedTime: 2007-10-31
 */
public class ClearCacheFilter implements Filter {
    private FilterConfig filterConfig;

    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) {
        try {
            HttpServletResponse res = (HttpServletResponse) response;
            res.setHeader("Cache-Control", "no-cache");
            res.setHeader("Cache-Control", "max-age=0");
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public FilterConfig getFilterConfig() {
        return this.filterConfig;
    }

    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    public void destroy() {
    }
}
