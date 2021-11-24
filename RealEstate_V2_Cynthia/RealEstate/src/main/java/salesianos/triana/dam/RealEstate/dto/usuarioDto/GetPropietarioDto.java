package salesianos.triana.dam.RealEstate.dto.usuarioDto;

import lombok.*;

import java.util.UUID;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetPropietarioDto {
    private UUID id;
    private String fullName;
    private String direccion;
    private String email;
    private String telefono;
    private String avatar;
}
