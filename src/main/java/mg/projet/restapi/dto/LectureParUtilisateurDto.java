package mg.projet.restapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LectureParUtilisateurDto {
    private Long utilisateurId;
    private String utilisateurNom;
    private String utilisateurPrenom;
    private Long totalLectures;
}
