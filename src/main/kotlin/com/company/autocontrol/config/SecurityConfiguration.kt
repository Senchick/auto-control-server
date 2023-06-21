package com.company.autocontrol.config

import com.company.autocontrol.dto.UserDto
import com.company.autocontrol.dto.error.ErrorResponse
import com.company.autocontrol.enums.Role
import com.company.autocontrol.security.UserDetailsServiceImpl
import com.company.autocontrol.service.UserService
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfiguration {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @Value("\${autocontrol.admin.login}")
    private lateinit var adminLogin: String

    @Value("\${autocontrol.admin.password}")
    private lateinit var adminPassword: String

    @Value("\${autocontrol.admin.firstname}")
    private lateinit var adminFirstname: String

    @Value("\${autocontrol.admin.surname}")
    private lateinit var adminSurname: String

    @Value("\${autocontrol.admin.department}")
    private lateinit var adminDepartment: String

    @Autowired
    private lateinit var userDetailsServiceImpl: UserDetailsServiceImpl

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.cors { it.disable() }
            .csrf { it.disable() }
            .formLogin { it.disable() }

        http.exceptionHandling()
            .accessDeniedHandler { _, response, _ ->
                response.contentType = "application/json"
                response.status = HttpServletResponse.SC_FORBIDDEN

                val errorResponse = ErrorResponse<Nothing>(
                    message = "Access Denied"
                )

                response.outputStream.println(ObjectMapper().writeValueAsString(errorResponse))
            }
            .authenticationEntryPoint { _, response, _ ->
                response.contentType = "application/json"
                response.status = HttpServletResponse.SC_UNAUTHORIZED

                val errorResponse = ErrorResponse<Nothing>(
                    message = "Bad Credentials"
                )

                response.outputStream.println(ObjectMapper().writeValueAsString(errorResponse))
            }

        http.authorizeHttpRequests { authorize ->
            authorize.requestMatchers("/v1/user/**")
                .hasAnyAuthority(Role.USER.roleName, Role.MODER.roleName, Role.ADMIN.roleName)

            authorize.requestMatchers("/v1/moder/**")
                .hasAnyAuthority(Role.MODER.roleName, Role.ADMIN.roleName)

            authorize.requestMatchers("/v1/admin/**")
                .hasAnyAuthority(Role.ADMIN.roleName)

            authorize.anyRequest()
                .authenticated()
        }.httpBasic()

        return http.build()
    }

    @Bean
    fun authenticationProvider(): AuthenticationProvider {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setPasswordEncoder(passwordEncoder())
        authProvider.setUserDetailsService(userDetailsServiceImpl)

        return authProvider
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
                        department = adminDepartment,
                        role = Role.ADMIN
                    )
                )
            }
        }
    }
}
