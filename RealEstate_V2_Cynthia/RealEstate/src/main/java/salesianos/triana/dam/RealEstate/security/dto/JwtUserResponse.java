package salesianos.triana.dam.RealEstate.security.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class JwtUserResponse {
    private String fullName;
    private String email;
    private String avatar;
    private String role;
    private String token;
}