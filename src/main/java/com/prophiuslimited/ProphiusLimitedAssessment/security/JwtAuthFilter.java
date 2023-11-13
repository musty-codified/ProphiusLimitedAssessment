package com.prophiuslimited.ProphiusLimitedAssessment.security;

import com.prophiuslimited.ProphiusLimitedAssessment.utils.AppUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final CustomUserDetailService userDetailsService;
    private final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);
    private final JwtUtils jwtUtils;
    private final AppUtils appUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");


        final String userEmail;
        final String jwtToken;

        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwtToken = authHeader.substring(7);
        userEmail = jwtUtils.extractUsername(jwtToken);
        logger.info("Token: " + jwtToken);
        System.out.println(userEmail);

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            AppUserDetails userDetails = (AppUserDetails) userDetailsService.loadUserByUsername(userEmail);
            appUtil.print(userDetails);
            final boolean isTokenValid = jwtUtils.isTokenValid(jwtToken, userDetails);
            if (isTokenValid) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
//                appUtil.print(userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);

                String userId = jwtUtils.getClaim(jwtToken, "userId").toString();
                request.setAttribute("userId", userId);
            }
        }
            filterChain.doFilter(request, response);
        }
    }
