package com.example.bookorder.security;

import com.example.bookorder.utils.ErrorMessages;
import com.example.bookorder.utils.ResultObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class MyBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx)
            throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ResultObject resultObject = ResultObject.failure(ErrorMessages.TOKEN_ERROR);
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(mapper.writeValueAsString(resultObject));
    }

    @Override
    public void afterPropertiesSet() {
        setRealmName("realm");
        super.afterPropertiesSet();
    }
}
