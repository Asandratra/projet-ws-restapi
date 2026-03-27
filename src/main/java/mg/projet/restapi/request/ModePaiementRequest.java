package mg.projet.restapi.request;

import jakarta.validation.constraints.NotBlank;

public record ModePaiementRequest(
        @NotBlank String mode) {
}
