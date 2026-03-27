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
@Table(name = "mode_paiement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModePaiement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mode;

    /** Constructeur sans id */
    public ModePaiement(String mode) {
        this.mode = mode;
    }
}
