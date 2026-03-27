package mg.projet.restapi.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ValiditeAbonnementDto {
    private Long utilisateurId;
    private String utilisateurNom;
    private boolean abonnementActif;
    private String typeAbonnement;
    private LocalDate dateExpiration;
    private String message;
}
