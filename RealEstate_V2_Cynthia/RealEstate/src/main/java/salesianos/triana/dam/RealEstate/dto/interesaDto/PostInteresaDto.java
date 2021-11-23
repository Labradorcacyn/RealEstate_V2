package salesianos.triana.dam.RealEstate.dto.interesaDto;

import lombok.*;

@NoArgsConstructor @AllArgsConstructor @Builder
@Getter @Setter
public class PostInteresaDto {

    private String nombre, apellidos, direccion, email, telefono, avatar, mensaje;

}
