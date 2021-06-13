package ua.training.myWeb.filters;

import org.apache.log4j.Logger;

import javax.servlet.*;
import java.io.IOException;
/**
 * Encoding filter.
 *
 *
 */
public class EncodingFilter implements Filter {

    private static final Logger log = Logger.getLogger(AuthFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        log.debug("Filter started");
        servletResponse.setContentType("text/html");
        servletResponse.setCharacterEncoding("UTF-8");
        servletRequest.setCharacterEncoding("UTF-8");

        filterChain.doFilter(servletRequest, servletResponse);
        log.debug("Filter ended");
    }
}
