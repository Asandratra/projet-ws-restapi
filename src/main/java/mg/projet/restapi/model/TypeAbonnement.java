package mg.projet.restapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "type_abonnement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TypeAbonnement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String typeAbonnement;
    private Double prix;

    /** Constructeur sans id */
    public TypeAbonnement(String typeAbonnement, Double prix) {
        this.typeAbonnement = typeAbonnement;
        this.prix = prix;
    }
}
