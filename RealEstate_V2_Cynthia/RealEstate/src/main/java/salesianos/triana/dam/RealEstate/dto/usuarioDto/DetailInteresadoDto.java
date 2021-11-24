package salesianos.triana.dam.RealEstate.dto.usuarioDto;

import lombok.*;
import salesianos.triana.dam.RealEstate.dto.InmobiliariaDto.ViviendaListaDto;

import java.util.List;

@NoArgsConstructor @AllArgsConstructor @Builder
@Getter @Setter
public class DetailInteresadoDto {

    private String fullName, direccion, email, telefono, avatar;
    private List<ViviendaListaDto> viviendas;

}
