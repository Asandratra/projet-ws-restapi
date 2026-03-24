package mg.projet.restapi.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserRequest(
    @NotBlank String nom,
    @NotBlank String prenom,
    @NotBlank String telephone,
    @Email String mail,
    @NotBlank String mdp,
    @NotBlank String confirmMdp
) {

}
