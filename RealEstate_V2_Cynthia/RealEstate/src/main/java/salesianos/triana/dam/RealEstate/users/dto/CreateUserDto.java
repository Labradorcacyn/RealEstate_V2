package salesianos.triana.dam.RealEstate.users.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class CreateUserDto {

    private String email;
    private String password;
    private String avatar;
    private String password2;
}
