package com.company.autocontrol.config

import com.company.autocontrol.dto.UserDto
import com.company.autocontrol.enums.Role
import com.company.autocontrol.service.UserService
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfiguration {
    @Value("\${autocontrol.admin.login}")
    private lateinit var adminLogin: String

    @Value("\${autocontrol.admin.password}")
    private lateinit var adminPassword: String

    @Value("\${autocontrol.admin.firstname}")
    private lateinit var adminFirstname: String

    @Value("\${autocontrol.admin.surname}")
    private lateinit var adminSurname: String

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.cors()
            .and()
            .csrf()
            .disable()

        http.authorizeHttpRequests()
            .requestMatchers("/v1/user/**")
            .hasAnyRole(Role.USER.name, Role.MODER.name, Role.ADMIN.name)
            .requestMatchers("/v1/moder/**")
            .hasAnyRole(Role.MODER.name, Role.ADMIN.name)
            .requestMatchers("/v1/admin/**")
            .hasAnyRole(Role.ADMIN.name)
            .anyRequest()
            .authenticated().and().httpBasic()

        http.exceptionHandling { exceptions ->
            exceptions
                .authenticationEntryPoint { request, response, authException ->
                    response.sendError(HttpServletResponse.SC_FORBIDDEN)
                }
        }

        return http.build()
    }

    @Bean
    fun adminRunner(userService: UserService): ApplicationRunner {
        return ApplicationRunner {
            if (userService.findByLogin(adminLogin) == null) {
                userService.registerUser(
                    UserDto(
                        login = adminLogin,
                        password = adminPassword,
                        firstname = adminFirstname,
                        surname = adminSurname,
                        role = Role.ADMIN
                    )
                )
            }
        }
    }
}
