package com.example.amllapi.security.filter;

import com.example.amllapi.security.ApiLoginHandler;
import com.example.amllapi.util.JwtUtil;
import com.google.gson.Gson;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

@Log4j2
public class JwtValidFilter extends OncePerRequestFilter {

    /**
     * description: 메서드 필터로 체크 하지 않을 경로나 메서드 지정
     * @param request current HTTP request
     * @return
     * @throws ServletException
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }
        String path = request.getRequestURI();

        //호출 하지 않는 경로 설정
        if (path.startsWith("/api/member/test")) {
            return true;
        }

        return false;
    }

    /**
     * description: 모든 요청에 대해 체크
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info("JwtValidFilter --------------");

        String authHeader = request.getHeader("Authorization");

        try {
            String accessToken = authHeader.substring(7);
            Map<String, Object> claims = JwtUtil.validateToken(accessToken);

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            log.error("JwtValidFilter Error ----------- {}",e.getMessage());

            Gson gson = new Gson();
            String msg = gson.toJson(Map.of("error", "ERROR_TOKEN"));

            ApiLoginHandler loginHandler = new ApiLoginHandler();
            loginHandler.showJsonContent(msg, response);

        }


    }
}
