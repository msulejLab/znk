package pl.lab.znk.web.error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.lab.znk.exception.NotFoundException;
import pl.lab.znk.exception.UserAlreadyExistsException;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(value = NotFoundException.class)
    protected ResponseEntity<?> handleNotFoundException(NotFoundException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(HttpStatus.NOT_FOUND.value());
        errorResponse.setMessage(exception.getMessage());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);

        return new ResponseEntity<>(errorResponse, httpHeaders, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = UserAlreadyExistsException.class)
    protected ResponseEntity<?> handleUserAlreadyExistsException(UserAlreadyExistsException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(HttpStatus.CONFLICT.value());
        errorResponse.setMessage(exception.getMessage());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);

        return new ResponseEntity<>(errorResponse, httpHeaders, HttpStatus.CONFLICT);
    }
}
