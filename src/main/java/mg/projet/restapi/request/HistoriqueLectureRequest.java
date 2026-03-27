package mg.projet.restapi.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public record HistoriqueLectureRequest(
        @NotNull Long livreId,
        @NotNull Long utilisateurId,
        @NotNull LocalDate dateLecture) {
}
