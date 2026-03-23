package mg.projet.restapi.request;

import jakarta.validation.constraints.NotBlank;

public record CreateRoleRequest(
    @NotBlank String name
) {

}
