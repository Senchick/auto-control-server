package com.company.autocontrol.config

import com.company.autocontrol.enums.Role
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfiguration {

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.cors()
            .and()
            .httpBasic()
            .and()
            .csrf()
            .disable()

        http.authorizeHttpRequests()
            .requestMatchers("/v1/user/**")
            .hasAnyRole(Role.USER.name, Role.ADMIN.name)
            .anyRequest()
            .authenticated()

        http.authorizeHttpRequests()
            .requestMatchers("/v1/admin/**")
            .hasAnyRole(Role.ADMIN.name)
            .anyRequest()
            .authenticated()

        http.authorizeHttpRequests()
            .anyRequest()
            .authenticated()

        http.exceptionHandling { exceptions ->
            exceptions
                .authenticationEntryPoint { request, response, authException ->
                    response.sendError(HttpServletResponse.SC_FORBIDDEN)
                }
        }

        return http.build()
    }
}
