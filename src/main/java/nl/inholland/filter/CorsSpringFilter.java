package nl.inholland.filter;

import com.thetransactioncompany.cors.CORSConfiguration;
import com.thetransactioncompany.cors.CORSFilter;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Component
public class CorsSpringFilter implements javax.servlet.Filter {
    static CORSFilter corsFilter;

    public CorsSpringFilter(CORSConfiguration corsConfiguration) throws ServletException {
        corsFilter = new CORSFilter(corsConfiguration);
    }

    public CorsSpringFilter() {
        super();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        corsFilter.doFilter(request, response, chain);
    }

    @Override
    public void destroy() {
        corsFilter.destroy();
    }
}
