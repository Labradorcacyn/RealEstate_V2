package salesianos.triana.dam.RealEstate.dto.securityDto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class JwtUserResponse {
    private String email;
    private String avatar;
    private String role;
    private String token;
}
