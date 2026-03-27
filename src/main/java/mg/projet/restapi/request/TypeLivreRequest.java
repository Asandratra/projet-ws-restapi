package mg.projet.restapi.request;

import jakarta.validation.constraints.NotBlank;

public record TypeLivreRequest(@NotBlank String type_livre) {
}
