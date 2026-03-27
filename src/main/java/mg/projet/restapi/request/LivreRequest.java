package mg.projet.restapi.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;

public record LivreRequest(
        Long typeLivreId,
        @NotBlank String titre,
        String sousTitre,
        String saison,
        String auteur,
        LocalDate dateEdition,
        String description,
        String image,
        String document,
        Integer etat) {
}
