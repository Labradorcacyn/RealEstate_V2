package salesianos.triana.dam.RealEstate.dto.usuarioDto;

import lombok.*;
import salesianos.triana.dam.RealEstate.dto.viviendaDto.DetailViviendaDto;

import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class PropietarioViendaDto {

    // Propietario
    private Long id;

    private String nombre,apellidos,direccionPropietario,email,telefono,avatarPropietario;

    // Vivienda
    private List<DetailViviendaDto> vivienda;
}
