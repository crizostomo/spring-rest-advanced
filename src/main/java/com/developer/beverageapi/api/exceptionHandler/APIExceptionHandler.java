package com.developer.beverageapi.api.exceptionHandler;

import com.developer.beverageapi.domain.exception.BusinessException;
import com.developer.beverageapi.domain.exception.EntityInUseException;
import com.developer.beverageapi.domain.exception.EntityNotFoundException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class APIExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String GENERIC_MESSAGE_ERROR = "A System Error Has Occurred. Please Try Again Later. "
            + "If the problem continues, contact us";

    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
                                                                      HttpHeaders headers, HttpStatus status,
                                                                      WebRequest request) {
        return ResponseEntity.status(status).headers(headers).build();
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
                                                         WebRequest request) {

        return handleValidationInternal(ex, headers, status, request, ex.getBindingResult());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ProblemType problemType = ProblemType.SYSTEM_ERROR;
        String detail = GENERIC_MESSAGE_ERROR;

        // Important to put the printStackTrace (At least for now, since we aren't doing any logging)
        log.error(ex.getMessage(), ex);

        APIError error = createProblemBuilder(status, problemType, detail)
                .userMessage(GENERIC_MESSAGE_ERROR)
                .build();

        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleEntityNotFound(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        ProblemType problemType = ProblemType.ACCESS_DENIED;
        String detail = GENERIC_MESSAGE_ERROR;

        APIError error = createProblemBuilder(status, problemType, detail)
                .userMessage(GENERIC_MESSAGE_ERROR)
                .userMessage("You do not have permission to execute this operation")
                .build();

        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleValidationInternal(ex, headers, status, request, ex.getBindingResult());
    }

    private ResponseEntity<Object> handleValidationInternal(Exception ex, HttpHeaders headers, HttpStatus status,
                                                            WebRequest request, BindingResult bindingResult) {
        ProblemType problemType = ProblemType.INVALID_DATA;
        String detail = "One or more fields are wrong. Fill them correctly and try again";

        List<APIError.Object> problemObjects = bindingResult.getAllErrors().stream()
                .map(objectError -> {
                    String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());

                    String name = objectError.getObjectName();

                    if (objectError instanceof FieldError) {
                        name = ((FieldError) objectError).getField();
                    }

                    return APIError.Object.builder()
                            .name(name)
                            .userMessage(message)
                            .build();
                })
                .collect(Collectors.toList());

        APIError problem = createProblemBuilder(status, problemType, detail)
                .userMessage(detail)
                .objects(problemObjects)
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
                                                                   HttpStatus status, WebRequest request) {

        ProblemType problemType = ProblemType.RESOURCE_NOT_FOUND;
        String detail = String.format("The resource %s, that you tried to access does not exist.",
                ex.getRequestURL());

        APIError error = createProblemBuilder(status, problemType, detail)
                .userMessage(GENERIC_MESSAGE_ERROR)
                .build();

        return handleExceptionInternal(ex, error, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
                                                        HttpStatus status, WebRequest request) {

        if (ex instanceof MethodArgumentTypeMismatchException) {
            return handleMethodArgumentTypeMismatch(
                    (MethodArgumentTypeMismatchException) ex, headers, status, request);
        }

        return super.handleTypeMismatch(ex, headers, status, request);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        ProblemType problemType = ProblemType.INVALID_PARAMETER;

        String detail = String.format("The property '%s' received the value" +
                "'%s', which is invalid. Please correct it and inform a compatible " +
                "value with the type %s.", ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

        APIError error = createProblemBuilder(status, problemType, detail)
                .userMessage(GENERIC_MESSAGE_ERROR)
                .build();

        return handleExceptionInternal(ex, error, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        if (rootCause instanceof InvalidFormatException) {
            return handleInvalidFormatException((InvalidFormatException) rootCause,
                    headers, status, request);
        } else if (rootCause instanceof PropertyBindingException) {
            return handlePropertyBindingException((PropertyBindingException) rootCause, headers, status, request);
        }

        ProblemType problemType = ProblemType.MESSAGE_NOT_READABLE;
        String detail = "The body is invalid. Verify syntax error";

        APIError error = createProblemBuilder(status, problemType, detail)
                .userMessage(GENERIC_MESSAGE_ERROR)
                .build();

        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers,
                                                                HttpStatus status, WebRequest request) {

//        String path = ex.getPath().stream()
//                .map(ref -> ref.getFieldName())
//                .collect(Collectors.joining("."));

        String path = joinPath(ex.getPath());

        ProblemType problemType = ProblemType.MESSAGE_NOT_READABLE;
        String detail = String.format("The property '%s' received the value" +
                "'%s', which is invalid. Please correct it and inform a compatible " +
                "value with the type %s.", path, ex.getValue(), ex.getTargetType());

        APIError error = createProblemBuilder(status, problemType, detail)
                .userMessage(GENERIC_MESSAGE_ERROR)
                .build();

        return handleExceptionInternal(ex, error, headers, status, request);
    }

    private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {

//        String path = ex.getPath().stream()
//                .map(ref -> ref.getFieldName())
//                .collect(Collectors.joining("."));

        String path = joinPath(ex.getPath());

        ProblemType problemType = ProblemType.FIELD_NOT_ALLOWED;
        String detail = String.format("The property '%s' doest not exist " +
                "Please verify your input ", path);

        APIError error = createProblemBuilder(status, problemType, detail)
                .userMessage(GENERIC_MESSAGE_ERROR)
                .build();

        return handleExceptionInternal(ex, error, headers, status, request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(
            EntityNotFoundException ex, WebRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        ProblemType problemType = ProblemType.RESOURCE_NOT_FOUND;
        String detail = ex.getMessage();

        APIError error = createProblemBuilder(status, problemType, detail)
                .userMessage(GENERIC_MESSAGE_ERROR)
                .build();

        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(
            BusinessException ex, WebRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemType problemType = ProblemType.RESOURCE_NOT_FOUND;
        String detail = ex.getMessage();

        APIError error = createProblemBuilder(status, problemType, detail)
                .userMessage(GENERIC_MESSAGE_ERROR)
                .build();

        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntityInUseException.class)
    public ResponseEntity<?> handleEntityInUseException(
            EntityInUseException ex, WebRequest request) {

        HttpStatus status = HttpStatus.CONFLICT;
        ProblemType problemType = ProblemType.ENTITY_IN_USE;
        String detail = ex.getMessage();

        APIError error = createProblemBuilder(status, problemType, detail)
                .userMessage(GENERIC_MESSAGE_ERROR)
                .build();

        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {

        if (body == null) {
            body = APIError.builder()
                    .timestamp(OffsetDateTime.now())
                    .title(status.getReasonPhrase())
                    .status(status.value())
                    .userMessage(GENERIC_MESSAGE_ERROR)
                    .build();
        } else if (body instanceof String) {
            body = APIError.builder()
                    .timestamp(OffsetDateTime.now())
                    .title((String) body)
                    .status(status.value())
                    .userMessage(GENERIC_MESSAGE_ERROR)
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private APIError.APIErrorBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail) {

        return APIError.builder()
                .timestamp(OffsetDateTime.now())
                .status(status.value())
                .type(problemType.getUri())
                .title(problemType.getTitle())
                .detail(detail);
    }

    private String joinPath(List<Reference> references) {
        return references.stream()
                .map(ref -> ref.getFieldName())
                .collect(Collectors.joining(".")); //
    }

}
