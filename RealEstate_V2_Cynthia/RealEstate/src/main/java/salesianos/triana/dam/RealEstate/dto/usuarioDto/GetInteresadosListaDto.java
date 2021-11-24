
package salesianos.triana.dam.RealEstate.dto.usuarioDto;

import lombok.*;

import java.util.UUID;

@NoArgsConstructor @AllArgsConstructor @Builder
@Getter @Setter
public class GetInteresadosListaDto {

    private UUID id;
    private String fullName, direccion, email, telefono, avatar;

}