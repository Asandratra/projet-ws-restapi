package mg.projet.restapi.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
    @Email String mail,
    @NotBlank String mdp,
    Long milliseconds
) {

}
