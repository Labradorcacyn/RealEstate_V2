package salesianos.triana.dam.RealEstate.dto.viviendaDto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailViviendaDto {

    private Long id;

    private String titulo, direccion, avatar;
}
