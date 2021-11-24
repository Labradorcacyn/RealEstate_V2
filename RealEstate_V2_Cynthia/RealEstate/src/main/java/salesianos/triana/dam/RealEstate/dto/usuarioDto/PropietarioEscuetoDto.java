package salesianos.triana.dam.RealEstate.dto.usuarioDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class PropietarioEscuetoDto {
    private UUID id;
    private String fullName;
}
