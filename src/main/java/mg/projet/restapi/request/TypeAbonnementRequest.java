package mg.projet.restapi.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TypeAbonnementRequest(
        @NotBlank String typeAbonnement,
        @NotNull Double prix) {
}
