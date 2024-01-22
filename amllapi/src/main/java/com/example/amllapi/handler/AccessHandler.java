package com.example.amllapi.handler;

import com.example.amllapi.security.ApiLoginHandler;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.util.Map;

/**
 * 접근제한 상황에 대해 메시지 전달
 */
public class AccessHandler implements AccessDeniedHandler{
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        Gson gson = new Gson();

        String msg = gson.toJson(Map.of("error", "ACCESS_DENIED"));
        ApiLoginHandler apiLoginHandler = new ApiLoginHandler();
        apiLoginHandler.showJsonContent(msg,response);


    }
}
