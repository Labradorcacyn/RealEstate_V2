package salesianos.triana.dam.RealEstate.dto.usuarioDto;

import lombok.*;
import salesianos.triana.dam.RealEstate.dto.viviendaDto.DetailViviendaDto;

import java.util.List;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class PropietarioViendaDto {

    // Propietario
    private UUID id;

    private String fullName,direccionPropietario,email,telefono,avatarPropietario;

    // Vivienda
    private List<DetailViviendaDto> vivienda;
}
