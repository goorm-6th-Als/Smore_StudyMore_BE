package com.als.SMore.user.login.util;

import com.als.SMore.user.login.service.UserInfoService;
import com.als.SMore.user.mypage.dto.response.MessageResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.als.SMore.user.login.util.ErrorCode.ACCESS_DENIED;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final UserInfoService userInfoService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // 여기서 부터 다음 if문 부분은 삭제하거나 추후에 정리해야합니다.
            String authorization = request.getHeader("Authorization");
            if(authorization.startsWith("test")){
                long id = Long.parseLong(authorization.substring(4));
                log.info("id : {}",id);

                OAuth2User userDetails = userInfoService.loadUserByUserPk(id);

                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                AbstractAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                securityContext.setAuthentication(authenticationToken);
                SecurityContextHolder.setContext(securityContext);
            }else{
                String token = parseBearerToken(request);

                if ( token == null){
                    filterChain.doFilter(request,response);
                    return;
                }

                String userId = tokenProvider.validate(token);
                if(userId == null){
                    filterChain.doFilter(request,response);
                    return;
                }

                // 사용자의 jwt 에서 key 가지고 와서 db에
                OAuth2User userDetails = userInfoService.loadUserByUserPk(Long.parseLong(userId));

                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                AbstractAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                securityContext.setAuthentication(authenticationToken);
                SecurityContextHolder.setContext(securityContext);
            }

        } catch (SecurityException | MalformedJwtException e) {
            log.info("JWT가 올바르게 구성되지 않았습니다.");
            request.setAttribute("exception", ErrorCode.WRONG_TYPE_TOKEN.getCode());
            return;
        } catch (ExpiredJwtException e) {
            log.info("JWT가 만료됨");
            request.setAttribute("exception", ErrorCode.EXPIRED_TOKEN.getCode());
            return;
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT");
            request.setAttribute("exception", ErrorCode.UNSUPPORTED_TOKEN.getCode());
            return;
        } catch (IllegalArgumentException e) {
            log.info("JWT의 클래엠이 null 또는 비어 있음");
            request.setAttribute("exception", ErrorCode.WRONG_TYPE_TOKEN.getCode());
            return;
        } catch (Exception exception){
            exception.printStackTrace();
            log.info("오류 발생");
            setResponse(response,ACCESS_DENIED,HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        filterChain.doFilter(request,response);
    }

    private String parseBearerToken(HttpServletRequest request){
        String authorization = request.getHeader("Authorization");

        boolean hasAuthorization = StringUtils.hasText(authorization);
        if(!hasAuthorization) return null;

        boolean isBearer = authorization.startsWith("Bearer ");
        if (!isBearer) return null;

        String token = authorization.substring(7);
        return token;
    }

    private void setResponse(HttpServletResponse response, ErrorCode exceptionCode,int httpStatusCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(httpStatusCode);

        response.getWriter().print(new ObjectMapper().writeValueAsString(MessageResponse.builder().message(exceptionCode.getMessage() ).build()));
    }
}
