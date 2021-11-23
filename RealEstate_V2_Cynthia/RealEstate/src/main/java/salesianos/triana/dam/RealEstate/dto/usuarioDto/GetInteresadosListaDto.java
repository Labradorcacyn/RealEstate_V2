
package salesianos.triana.dam.RealEstate.dto.usuarioDto;

import lombok.*;

@NoArgsConstructor @AllArgsConstructor @Builder
@Getter @Setter
public class GetInteresadosListaDto {

    private Long id;
    private String nombre, apellidos, direccion, email, telefono, avatar;

}