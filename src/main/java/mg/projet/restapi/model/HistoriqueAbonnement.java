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
@Table(name = "historique_abonnement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistoriqueAbonnement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate datePaiement;

    @ManyToOne
    @JoinColumn(name = "id_typeabonnement")
    private TypeAbonnement typeAbonnement;

    @ManyToOne
    @JoinColumn(name = "id_modepaiement")
    private ModePaiement modePaiement;

    @ManyToOne
    @JoinColumn(name = "id_utilisateur")
    private User utilisateur;

    private LocalDate dateExpiration;
}
