package com.dingdong.picmap.config.jwt;

import com.dingdong.picmap.config.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final SecurityUtils securityUtils;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = jwtTokenProvider.resolveToken(request);

        if (token != null) {
            jwtTokenProvider.validateToken(token);
            securityUtils.setAuthenticationByToken(token, request.getRequestURI());
        }
        chain.doFilter(request, response);
    }
}
