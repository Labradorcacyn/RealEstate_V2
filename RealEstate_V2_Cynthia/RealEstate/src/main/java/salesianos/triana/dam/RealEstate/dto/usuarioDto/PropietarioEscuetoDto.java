package salesianos.triana.dam.RealEstate.dto.usuarioDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class PropietarioEscuetoDto {
    private Long id;
    private String nombre;
    private String apellido;
}