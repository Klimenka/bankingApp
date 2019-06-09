package nl.inholland.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.util.logging.Logger;

@Component
@Order(1)
public class LargeRequestFilter implements Filter {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        int size = servletRequest.getContentLength();
        logger.info("Request size: " + size);
        if (size > 10000000) {
            logger.severe("Request with size " + size + "was rejected!");
            throw new ServletException("Request is too large");
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

}
