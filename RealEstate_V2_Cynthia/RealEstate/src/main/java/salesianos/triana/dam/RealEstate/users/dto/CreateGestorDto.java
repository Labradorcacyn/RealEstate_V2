package salesianos.triana.dam.RealEstate.users.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CreateGestorDto {

    private Long inmobiliaria;
    private String fullName;
    private String direccion;
    private String telefono;
    private String email;
    private String avatar;
    private String password;
    private String password2;
}
