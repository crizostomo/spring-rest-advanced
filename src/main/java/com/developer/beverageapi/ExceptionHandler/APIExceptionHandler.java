package com.developer.beverageapi.ExceptionHandler;

import com.developer.beverageapi.domain.exception.BusinessException;
import com.developer.beverageapi.domain.exception.EntityInUseException;
import com.developer.beverageapi.domain.exception.EntityNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class APIExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(
            EntityNotFoundException ex, WebRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        ProblemType problemType = ProblemType.ENTITY_NOT_FOUND;
        String detail = ex.getMessage();

        APIError error = createProblemBuilder(status, problemType, detail).build();

//        APIError error = APIError.builder()
//                .status(status.value())
//                .type("https://google.com.br/entity-not-found-exception")
//                .title("Entity not found")
//                .detail(ex.getMessage())
//                .build();

        return handleExceptionInternal(ex, error, new HttpHeaders(),
                status, request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(
            BusinessException ex, WebRequest request) {

        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(),
                HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(EntityInUseException.class)
    public ResponseEntity<?> handleEntityInUseException(
            EntityInUseException ex, WebRequest request) {

        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(),
                HttpStatus.CONFLICT, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        if (body == null) {
            body = APIError.builder()
                    .title(status.getReasonPhrase())
                    .status(status.value())
                    .build();
        } else if (body instanceof String) {
            body = APIError.builder()
                    .title((String) body)
                    .status(status.value())
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private APIError.APIErrorBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail) {
        return APIError.builder()
                .status(status.value())
                .type(problemType.getUri())
                .title(problemType.getTitle())
                .detail(detail);
    }
}
