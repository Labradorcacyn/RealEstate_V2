package salesianos.triana.dam.RealEstate.error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import salesianos.triana.dam.RealEstate.error.exceptions.NewUserWithDifferentPasswordException;

@RestControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NewUserWithDifferentPasswordException.class)
    public ResponseEntity<ApiError> handleNewUserErrors(Exception ex){
        return buildErrorResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiError apiError = new ApiError(status, ex.getMessage());
        return ResponseEntity.status(status).headers(headers).body(apiError);
    }

    private ResponseEntity<ApiError> buildErrorResponseEntity(HttpStatus status, String mensaje){
        return ResponseEntity.status(status)
                .body(ApiError.builder()
                        .estado(status)
                        .mensaje(mensaje)
                        .build());
    }
}