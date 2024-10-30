package com.pub.filter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>Title: ϵͳ</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: zznode</p>
 * @author 
 * @CreatedTime: 2007-10-31
 * @version 1.0 dd单点
 */
public class ClearCacheFilter implements Filter {
    private FilterConfig filterConfig;

    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain filterChain) {
        try {
            HttpServletResponse res = (HttpServletResponse) response;
            res.setHeader("Cache-Control", "no-cache");
            res.setHeader("Cache-Control", "max-age=0");
            filterChain.doFilter(request, response);
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    public FilterConfig getFilterConfig() {
        return this.filterConfig;
    }

    public void destroy() {
    }
}
