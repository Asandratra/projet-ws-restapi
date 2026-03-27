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
public class HistoriquePaiementLivreDto {
    private Long id;
    private LocalDate dateLecture;
    private Long livreId;
    private String livreTitre;
    private Long utilisateurId;
    private String utilisateurNom;
    private String utilisateurPrenom;
    private Double prix;
}
