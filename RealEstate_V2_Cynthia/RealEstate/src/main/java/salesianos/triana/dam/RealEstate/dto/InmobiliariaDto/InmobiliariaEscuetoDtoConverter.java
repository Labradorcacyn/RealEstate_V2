package salesianos.triana.dam.RealEstate.dto.InmobiliariaDto;

import org.springframework.stereotype.Component;
import salesianos.triana.dam.RealEstate.model.Inmobiliaria;

@Component
public class InmobiliariaEscuetoDtoConverter {

    public InmobiliariaEscuetoDto inmobiliariaToInmobiliariaEscuetoDto(Inmobiliaria i){
        return InmobiliariaEscuetoDto.builder()
                .id(i.getId())
                .nombre(i.getNombre())
                .build();
    }
}
// No me borres plox