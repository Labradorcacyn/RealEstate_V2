package salesianos.triana.dam.RealEstate.dto.InmobiliariaDto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class GetInmobiliariasDto {

    private String nombre, email, telefono;
    private Long id;
}
