package mg.projet.restapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MensualUserAmountDto {
    private UserDto utilisateur;
    private Double totalSubscription;
    private Double totalPayment;
    private int year;
    private int month;
}
