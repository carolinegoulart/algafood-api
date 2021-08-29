package com.algaworks.algafoodapi.api.exceptionhandler;

import com.algaworks.algafoodapi.domain.exception.BusinessException;
import com.algaworks.algafoodapi.domain.exception.EntityInUseException;
import com.algaworks.algafoodapi.domain.exception.EntityNotFoundException;
import com.algaworks.algafoodapi.domain.exception.ValidationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String MESSAGE_INVALID_PAYLOAD = "Invalid payload. Please check the fields and their values";
    private static final String MESSAGE_RESOURCE_NOT_FOUND = "Resource not found";

    // captures all uncaught exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaughtExceptions(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ProblemType problemType = ProblemType.INTERNAL_SERVER_ERROR;
        String detail = "An unexpected error has occurred";

        // this is needed when developing
        ex.printStackTrace();

        Problem problem = createProblemBuilder(status, problemType, detail).build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    // separates each error into a different JSON object in the response
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        return handleValidationInternal(ex, ex.getBindingResult(), headers, status, request);
    }

    // separates each error into a different JSON object in the response, but for the ValidationException (PATCH)
    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity<Object> handleValidationException(ValidationException ex, WebRequest request) {
        return handleValidationInternal(ex, ex.getBindingResult(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    private ResponseEntity<Object> handleValidationInternal(Exception ex, BindingResult bindingResult, HttpHeaders headers,
                                                            HttpStatus status, WebRequest request) {
        ProblemType problemType = ProblemType.INVALID_DATA;
        String detail = "One or more fields are invalid";

        List<Problem.Object> problemObjects = bindingResult.getAllErrors().stream()
                .map(objectError -> {
                    String name = objectError.getObjectName();
                    if (objectError instanceof FieldError) {
                        name = ((FieldError) objectError).getField();
                    }
                    return Problem.Object.builder()
                            .name(name)
                            .userMessage(objectError.getDefaultMessage())
                            .build();
                })
                .collect(Collectors.toList());

        Problem problem = createProblemBuilder(status, problemType, detail)
                .objects(problemObjects)
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    // we need spring.mvc.throw-exception-if-no-handler-found: true and spring.web.resources.add-mappings: false
    // for this method to work. this method was invoked when we called an URL that does not exist
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
                                                                   HttpStatus status, WebRequest request) {
        ProblemType problemType = ProblemType.RESOURCE_NOT_FOUND;
        String detail = String.format(MESSAGE_RESOURCE_NOT_FOUND, ex.getRequestURL());
        Problem problem = createProblemBuilder(status, problemType, detail).build();
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        if (rootCause instanceof InvalidFormatException) {
            return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
        }

        // Generic handling
        ProblemType problemType = ProblemType.MESSAGE_NOT_READABLE;
        Problem problem = createProblemBuilder(status, problemType, MESSAGE_INVALID_PAYLOAD).build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    // The main methods purpose of this method is to personalize the detail
    private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers,
                                                                 HttpStatus status, WebRequest request) {
        String path = joinPath(ex.getPath());
        ProblemType problemType = ProblemType.MESSAGE_NOT_READABLE;

        String detail = String.format(MESSAGE_INVALID_PAYLOAD, path, ex.getValue());
        Problem problem = createProblemBuilder(status, problemType, detail).build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    private ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex,
                                                                             WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemType problemType = ProblemType.RESOURCE_NOT_FOUND;
        Problem problem = createProblemBuilder(status, problemType, MESSAGE_RESOURCE_NOT_FOUND).build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handlerEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ProblemType problemType = ProblemType.RESOURCE_NOT_FOUND;
        String detail = ex.getMessage();
        Problem problem = createProblemBuilder(status, problemType, detail).build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> businessExceptionHandler(BusinessException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemType problemType = ProblemType.BUSINESS_EXCEPTION;
        String detail = ex.getMessage();
        Problem problem = createProblemBuilder(status, problemType, detail).build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntityInUseException.class)
    public ResponseEntity<?> entityInUseExceptionHandler(EntityInUseException ex, WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        ProblemType problemType = ProblemType.ENTITY_IN_USE;
        String detail = ex.getMessage();
        Problem problem = createProblemBuilder(status, problemType, detail).build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    // All the exceptions that are in the class ResponseEntityExceptionHandler reach this method
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
        if (body == null) {
            body = Problem.builder()
                    .timestamp(OffsetDateTime.now())
                    .title(status.getReasonPhrase())
                    .status(status.value())
                    .build();
        } else if (body instanceof String) {
            body = Problem.builder()
                    .timestamp(OffsetDateTime.now())
                    .title((String) body)
                    .status(status.value())
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private Problem.ProblemBuilder createProblemBuilder(HttpStatus status,
                                                        ProblemType problemType,
                                                        String detail) {
        return Problem.builder()
                .timestamp(OffsetDateTime.now())
                .status(status.value())
                .type(problemType.getUri())
                .title(problemType.getTitle())
                .detail(detail);
    }

    private String joinPath(List<JsonMappingException.Reference> references) {
        return references.stream()
                .map(JsonMappingException.Reference::getFieldName)
                .collect(Collectors.joining("."));
    }
}