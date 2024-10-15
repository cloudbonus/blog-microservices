package com.github.api_gateway.service.exception.impl

import com.github.api_gateway.service.exception.ExceptionEnum

/**
 * @author Raman Haurylau
 */
class CustomException(val exceptionEnum: ExceptionEnum) : RuntimeException()