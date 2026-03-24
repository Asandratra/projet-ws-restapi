package mg.projet.restapi.dto;

import java.sql.Timestamp;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mg.projet.restapi.model.Role;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserDto {
    
    private Long id;
    private Timestamp dateentree;
    private String nom;
    private String prenom;
    private String telephone;;
    private String mail;
    private int etat;

    private List<Role> roles;

}
