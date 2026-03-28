package mg.projet.restapi.exception;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ExceptionMessage> handleInvalidRequestException(InvalidRequestException e, WebRequest request){
        ExceptionMessage message = new ExceptionMessage();
        message.setStatus(400);
        message.setError("INVALID REQUEST ERROR");
        message.setMessage(e.getMessage());
        message.setTimestamp(Instant.now());
        message.setUri(request.getContextPath());

        return new ResponseEntity<ExceptionMessage>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ExceptionMessage> handleAuthentificationException(AuthenticationException e, WebRequest request){
        ExceptionMessage message = new ExceptionMessage();
        message.setStatus(401);
        message.setError("AUTHENTICATION ERROR");
        message.setMessage(e.getMessage());
        message.setTimestamp(Instant.now());
        message.setUri(request.getContextPath());

        return new ResponseEntity<ExceptionMessage>(message, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionMessage> handleAccessDeniedException(AccessDeniedException e, WebRequest request){
        ExceptionMessage message = new ExceptionMessage();
        message.setStatus(403);
        message.setError("FORBIDDEN ERROR");
        message.setMessage(e.getMessage());
        message.setTimestamp(Instant.now());
        message.setUri(request.getContextPath());

        return new ResponseEntity<ExceptionMessage>(message, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionMessage> handleNotFoundException(NotFoundException e, WebRequest request){
        ExceptionMessage message = new ExceptionMessage();
        message.setStatus(404);
        message.setError("RESOURCE NOT FOUND ERROR");
        message.setMessage(e.getMessage());
        message.setTimestamp(Instant.now());
        message.setUri(request.getContextPath());

        return new ResponseEntity<ExceptionMessage>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionMessage> handleUnhandledException(Exception e, WebRequest request){
        ExceptionMessage message = new ExceptionMessage();
        message.setStatus(500);
        message.setError("SERVER ERROR");
        message.setMessage(e.getMessage());
        message.setTimestamp(Instant.now());
        message.setUri(request.getContextPath());

        return new ResponseEntity<ExceptionMessage>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
