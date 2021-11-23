package salesianos.triana.dam.RealEstate.dto.InmobiliariaDto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InmobiliariaViviendasDto {

    private String nombre, email, telefono;
    private Long id;

    private List<ViviendaListaDto> viviendas;
}