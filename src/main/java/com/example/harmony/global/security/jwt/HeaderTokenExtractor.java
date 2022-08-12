package com.example.harmony.global.security.jwt;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;

@Component
public class HeaderTokenExtractor {

    /*
     * HEADER_PREFIX
     * header Authorization token 값의 표준이되는 변수
     */
    public final String HEADER_PREFIX = "Bearer ";

    public String extract(String header, HttpServletRequest request) {
        /*
         * header 값이 비어있거나 또는 HEADER_PREFIX 값보다 짧은 경우 예외처리
         */
        if (header == null || header.equals("") || header.length() < HEADER_PREFIX.length()) {
            System.out.println("error request : " + request.getRequestURI());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "올바른 JWT 정보가 아닙니다.");
        }

        /*
         * Token 값 존재하는 경우 (bearer ) 부분만 제거 후 token 값 반환
         */
        return header.substring(
                HEADER_PREFIX.length(),
                header.length()
        );
    }
}
