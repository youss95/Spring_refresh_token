package com.example.amllapi.security;

import com.example.amllapi.dto.MemberDto;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class ApiLoginHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        MemberDto memberDto = (MemberDto)authentication.getPrincipal();

        Map<String, Object> claims = memberDto.getClaims();

        claims.put("accessToken", "test");
        claims.put("refreshToken", "test");

        Gson gson = new Gson();
        String jsonStr = gson.toJson(claims);

        showJsonContent(jsonStr, response);
    }


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        Gson gson = new Gson();
        String jsonStr = gson.toJson(Map.of("error", "ERROR_LOGIN"));

        showJsonContent(jsonStr,response);


    }

    private void showJsonContent(String str, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();
        printWriter.println(str);
        printWriter.close();
    }
}
