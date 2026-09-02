package com.example.spwork.interceptor;

import com.example.spwork.config.JwtUtil;
import com.example.spwork.context.UserContext;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT 认证拦截器
 * 从请求头 Authorization: Bearer <token> 中取出令牌并校验
 * 校验通过：把 stuId 写入 UserContext（ThreadLocal），放行
 * 校验失败：返回 401 JSON
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        // 放行 CORS 预检请求（OPTIONS），预检不带 Authorization
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            writeUnauthorized(response, "未登录或Token缺失");
            return false;
        }

        String token = header.substring(7);
        try {
            Claims claims = jwtUtil.parseToken(token);
            Integer stuId = Integer.valueOf(claims.getSubject());
            UserContext.setStuId(stuId);
            request.setAttribute("stuId", stuId);
            return true;
        } catch (ExpiredJwtException e) {
            writeUnauthorized(response, "Token已过期");
            return false;
        } catch (Exception e) {
            writeUnauthorized(response, "Token无效");
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 请求结束后清除 ThreadLocal，防止内存泄漏和线程复用串数据
        UserContext.clear();
    }

    private void writeUnauthorized(HttpServletResponse response, String msg) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"code\":401,\"msg\":\"" + msg + "\"}");
    }
}
