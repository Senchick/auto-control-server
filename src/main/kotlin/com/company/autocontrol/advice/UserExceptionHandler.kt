package com.company.autocontrol.advice

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.web.bind.annotation.ControllerAdvice

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
class UserExceptionHandler {


}