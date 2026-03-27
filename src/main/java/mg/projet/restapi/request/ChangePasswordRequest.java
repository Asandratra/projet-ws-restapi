package mg.projet.restapi.request;

import jakarta.validation.constraints.NotBlank;

public record ChangePasswordRequest(
    @NotBlank String mdp,
    @NotBlank String confirmMdp
) {}
