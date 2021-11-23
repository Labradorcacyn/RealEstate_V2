
package salesianos.triana.dam.RealEstate.dto.usuarioDto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetInteresadosDto {
    private Long id;
    private String nombre;
    private String apellidos;
}