package mg.projet.restapi.exception;

import java.security.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExceptionMessage {
    private int status;
    private String message;
    private String error;
    private Timestamp timestamp;
}
