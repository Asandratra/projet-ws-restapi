package mg.projet.restapi.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public record HistoriqueAbonnementRequest(
        @NotNull Long utilisateurId,
        @NotNull Long typeAbonnementId,
        @NotNull Long modePaiementId,
        @NotNull LocalDate datePaiement,
        @NotNull LocalDate dateExpiration) {
}
