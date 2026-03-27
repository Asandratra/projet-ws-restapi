package mg.projet.restapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ConsultationLivreDto {
    private Long livreId;
    private String livreTitre;
    private Long totalConsultations;
}
