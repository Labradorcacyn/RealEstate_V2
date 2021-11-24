package salesianos.triana.dam.RealEstate.users.dto;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class GetUserDto {
    //Información que le daremos al usuario cuando cree un nuevo usuario por ejemplo

    private String fullName;
    private String email;
    private String avatar;
    private String role;
}
