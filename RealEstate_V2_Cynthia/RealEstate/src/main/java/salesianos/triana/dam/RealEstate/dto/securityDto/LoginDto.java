package salesianos.triana.dam.RealEstate.dto.securityDto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class LoginDto {

    private String email;
    private String password;
}
