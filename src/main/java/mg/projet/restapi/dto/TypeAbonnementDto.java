package mg.projet.restapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TypeAbonnementDto {
    private Long id;
    private String typeAbonnement;
    private Double prix;
}
