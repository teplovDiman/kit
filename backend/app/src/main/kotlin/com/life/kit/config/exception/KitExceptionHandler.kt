package com.life.kit.config.exception

import com.life.kit.common.KitHelper
import mu.KotlinLogging
import org.apache.commons.lang3.StringUtils
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.stereotype.Service
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.util.HtmlUtils
import javax.servlet.http.HttpServletRequest
import javax.validation.ConstraintViolation
import javax.validation.ConstraintViolationException

private val log = KotlinLogging.logger {}

@RestControllerAdvice
class KitExceptionHandler (

  private val kitHelper: KitHelper

) {

  companion object {
    private const val JSR_VALIDATION_MESSAGE = "Validation errors: %s"
    private const val POST_BODY_MISSING = "Required request body is missing"
    private const val BODY_MALFORMED = "JSON parse error:"
  }

  @ExceptionHandler(MethodArgumentNotValidException::class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  fun handleBusinessValidation(request: HttpServletRequest, methodArgumentNotValidException: MethodArgumentNotValidException): ErrorResponse {
    val allErrors = methodArgumentNotValidException.bindingResult.allErrors
    return ErrorResponse(kitHelper.getLocalDateTimeNow(),
      request.requestURI,
      HttpStatus.BAD_REQUEST.value(),
      ErrorType.VALIDATION.value,
      String.format(JSR_VALIDATION_MESSAGE, allErrors.size),
      allErrors.map { x: ObjectError -> getInnerErrors(x) })
  }

  @ExceptionHandler(ConstraintViolationException::class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  fun handleBusinessValidation(request: HttpServletRequest, constraintViolationException: ConstraintViolationException): ErrorResponse {
    val errors = constraintViolationException.constraintViolations.map { violation: ConstraintViolation<*> -> getInnerErrors(violation) }
    return ErrorResponse(kitHelper.getLocalDateTimeNow(),
      request.requestURI,
      HttpStatus.BAD_REQUEST.value(),
      ErrorType.VALIDATION.value,
      String.format(JSR_VALIDATION_MESSAGE, constraintViolationException.constraintViolations.size),
      errors)
  }

  @ExceptionHandler(HttpMessageNotReadableException::class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  fun handleBodyIsNull(request: HttpServletRequest, httpMessageNotReadableException: HttpMessageNotReadableException): ErrorResponse {
    // TODO: This method is the crutch entirely. See more information in the KIT-43 issue
    if (httpMessageNotReadableException.message == null) {
      return handleUnexpectedException(request, httpMessageNotReadableException)
    }
    val message: String = when {
      httpMessageNotReadableException.message!!.startsWith(POST_BODY_MISSING) -> {
        POST_BODY_MISSING
      }
      httpMessageNotReadableException.message!!.startsWith(BODY_MALFORMED) -> {
        httpMessageNotReadableException.message!!
      }
      else -> {
        return handleUnexpectedException(request, httpMessageNotReadableException)
      }
    }
    return ErrorResponse(
      kitHelper.getLocalDateTimeNow(),
      request.requestURI,
      HttpStatus.BAD_REQUEST.value(),
      ErrorType.VALIDATION.value,
      String.format(JSR_VALIDATION_MESSAGE, 1),
      listOf(InnerError(message)))
  }

  @ExceptionHandler(RuntimeException::class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  fun handleUnexpectedException(request: HttpServletRequest, runtimeException: RuntimeException): ErrorResponse {
    if (log.isErrorEnabled) {
      log.error("Internal Server Error. URI Path: {}", request.requestURI)
      log.error("Internal Server Error. Message: {}", HtmlUtils.htmlEscape(runtimeException.message?: ""))
      log.error("Internal Server Error. StackTrace: {}", runtimeException.stackTrace)
    }
    return ErrorResponse(kitHelper.getLocalDateTimeNow(),
      request.requestURI,
      HttpStatus.INTERNAL_SERVER_ERROR.value(),
      ErrorType.INTERNAL.value,
    "Internal Server Error. Please contact with support.")
  }

  private fun getInnerErrors(objectError: ObjectError): InnerError {
    return InnerError(objectError.defaultMessage, getTarget(objectError))
  }

  private fun getInnerErrors(violation: ConstraintViolation<*>): InnerError {
    return InnerError(violation.message, getTarget(violation))
  }

  private fun getTarget(violation: ConstraintViolation<*>): String {
    return if (violation.rootBeanClass.annotations.any { x -> x is Service}) {
      StringUtils.substringAfter(violation.propertyPath.toString(), ".")
    } else {
      violation.propertyPath.toString()
    }
  }

  private fun getTarget(objectError: ObjectError): String {
    return if (objectError is FieldError) objectError.field else objectError.objectName
  }
}
