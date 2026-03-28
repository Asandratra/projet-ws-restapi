package mg.projet.restapi.exception;

public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException(String message){
        super(message);
    }
}
