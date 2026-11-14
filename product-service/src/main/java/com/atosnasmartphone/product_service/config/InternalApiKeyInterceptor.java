package com.atosnasmartphone.product_service.config;

import com.atosnasmartphone.product_service.exception.InternalAccessDeniedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@Component
public class InternalApiKeyInterceptor implements HandlerInterceptor {
    public static final String HEADER = "X-Internal-Key";

    @Value("${internal.api-key}")
    private String expectedKey;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String provided = request.getHeader(HEADER);
        if (provided == null || expectedKey == null || expectedKey.isBlank()
                || !MessageDigest.isEqual(
                provided.getBytes(StandardCharsets.UTF_8),
                expectedKey.getBytes(StandardCharsets.UTF_8))) {
            throw new InternalAccessDeniedException();
        }
        return true;
    }
}
