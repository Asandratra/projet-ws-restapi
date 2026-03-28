package mg.projet.restapi.request;

import jakarta.validation.constraints.NotNull;

public record AssignRoleRequest(
    @NotNull Long idUser,
    @NotNull Long idRole
) {

}
