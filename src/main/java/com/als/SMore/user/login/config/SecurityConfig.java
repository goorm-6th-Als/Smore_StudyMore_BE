package com.als.SMore.user.login.config;

import com.als.SMore.user.login.service.KakaoMemberDetailsService;
import com.als.SMore.user.login.util.JwtFilter;
import com.als.SMore.user.login.util.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final KakaoMemberDetailsService kakaoMemberDetailsService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(cors -> cors.configurationSource(corsConfiguration()));
        http.httpBasic(HttpBasicConfigurer::disable);
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer.disable());

//        http.authorizeHttpRequests(request -> request
//                        .anyRequest().authenticated()
//                )
//                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        http
                .authorizeRequests()
                    .anyRequest().permitAll() // 모든 요청을 허용(개발 끝나면 없어져야 합니당)
                    .and()
                .oauth2Login(oauth2 -> oauth2
                        .authorizationEndpoint(endpoint -> endpoint.baseUri("/oauth2/authorization/kakao"))
                        .redirectionEndpoint(endpoint -> endpoint.baseUri("/oauth2/callback/*"))
                        .userInfoEndpoint(endpoint -> endpoint.userService(kakaoMemberDetailsService))
                        .successHandler(oAuth2SuccessHandler)
                )
                .csrf(csrf -> csrf.disable()); // CSRF 보호 비활성화 (개발 끝나면 없어져야 합니당)
        return http.build();
    }

//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration config = new CorsConfiguration();
//
//        config.setAllowCredentials(true);
//        config.setAllowedOrigins(List.of("http://localhost:3000"));
//        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
//        config.setAllowedHeaders(List.of("*"));
//        config.setExposedHeaders(List.of("*"));
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", config);
//        return source;
//    }

    @Bean
    public CorsConfigurationSource corsConfiguration() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
        config.setAllowedMethods(Collections.singletonList("*"));
        config.setAllowCredentials(true);
        config.setAllowedHeaders(Collections.singletonList("*"));
        config.setMaxAge(3600L);
        config.setExposedHeaders(Arrays.asList("Authorization", "X-Refresh-Token"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
