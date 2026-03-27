package mg.projet.restapi.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "livre")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Livre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_typelivre")
    private TypeLivre typeLivre;

    private String titre;
    private String sousTitre;
    private String saison;
    private String auteur;
    private LocalDate dateEdition;
    private String description;
    private String image;
    private String document;
    private Integer etat = 0;

    /** Constructeur sans id */
    public Livre(TypeLivre typeLivre, String titre, String sousTitre, String saison, String auteur,
            LocalDate dateEdition, String description, String image, String document, Integer etat) {
        this.typeLivre = typeLivre;
        this.titre = titre;
        this.sousTitre = sousTitre;
        this.saison = saison;
        this.auteur = auteur;
        this.dateEdition = dateEdition;
        this.description = description;
        this.image = image;
        this.document = document;
        this.etat = etat;
    }
}
