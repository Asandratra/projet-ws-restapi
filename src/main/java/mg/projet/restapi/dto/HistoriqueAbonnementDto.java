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
public class HistoriqueAbonnementDto {
    private Long id;
    private LocalDate datePaiement;
    private Long typeAbonnementId;
    private String typeAbonnementNom;
    private Double typeAbonnementPrix;
    private Long modePaiementId;
    private String modePaiementNom;
    private Long utilisateurId;
    private String utilisateurNom;
    private String utilisateurPrenom;
    private LocalDate dateExpiration;
}
