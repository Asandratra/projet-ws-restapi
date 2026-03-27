package mg.projet.restapi.request;

public record UpdateUserRequest(
    String nom,
    String prenom,
    String telephone,
    String mail
) {

}
