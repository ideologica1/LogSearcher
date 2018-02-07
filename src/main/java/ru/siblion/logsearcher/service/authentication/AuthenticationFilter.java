package ru.siblion.logsearcher.service.authentication;

import org.apache.log4j.MDC;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Component
public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            MDC.put("username", authentication.getName());
        }
        try {

            chain.doFilter(req, resp);
        } finally {
            if (authentication != null) {
                MDC.remove("username");
            }
        }
    }

    @Override
    public void destroy() {
    }

}