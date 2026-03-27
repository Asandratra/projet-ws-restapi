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
public class LivreDto {
    private Long id;
    private Long typeLivreId;
    private String typeLivreNom;
    private String titre;
    private String sousTitre;
    private String saison;
    private String auteur;
    private LocalDate dateEdition;
    private String description;
    private String image;
    private String document;
    private Integer etat;
}
