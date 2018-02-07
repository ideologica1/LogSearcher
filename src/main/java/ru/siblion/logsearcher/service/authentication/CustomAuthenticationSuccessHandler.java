package ru.siblion.logsearcher.service.authentication;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private Logger logger = Logger.getLogger(CustomAuthenticationSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse, Authentication authentication)
            throws IOException {
        HttpSession session = httpServletRequest.getSession(false);
        if (session != null) {
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            String currentUsername = authentication.getName();
            MDC.put("username", currentUsername);
            logger.info(" has been logged in");
            httpServletResponse.sendRedirect("form");
        }
    }
}