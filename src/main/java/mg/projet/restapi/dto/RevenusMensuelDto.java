package mg.projet.restapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RevenusMensuelDto {
    private int annee;
    private int mois;
    private Double revenusAbonnements;
    private Double revenusLivres;
    private Double totalRevenus;
}
