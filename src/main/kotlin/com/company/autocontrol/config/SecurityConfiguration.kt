package com.company.autocontrol.config

import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfiguration {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.cors().and().httpBasic().and().csrf().disable()

        http.authorizeHttpRequests().requestMatchers("/api/**").permitAll()

        http.authorizeHttpRequests().anyRequest().authenticated()

        http.exceptionHandling { exceptions ->
            exceptions
                .authenticationEntryPoint { request, response, authException ->
                    response.sendError(HttpServletResponse.SC_FORBIDDEN)
                }
        }

        return http.build()
    }
}
