package mg.projet.restapi.request;

public record AssignRoleRequest(
    Long idUser,
    Long idRole
) {

}
