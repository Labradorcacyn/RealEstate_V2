package salesianos.triana.dam.RealEstate.dto.interesaDto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
public class InteresaDto {
    private String mensaje;
    private LocalDateTime createDate;
}
