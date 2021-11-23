package salesianos.triana.dam.RealEstate.dto.InmobiliariaDto;

import org.springframework.stereotype.Component;
import salesianos.triana.dam.RealEstate.model.Inmobiliaria;

@Component
public class GetInmobiliasDtoConverter {

    public GetInmobiliariasDto inmobiliariaToInmobiliariasDto(Inmobiliaria i){
        return GetInmobiliariasDto.builder()
                .nombre(i.getNombre())
                .email(i.getEmail())
                .telefono(i.getTelefono())
                .id(i.getId())
                .build();
    }

    public Inmobiliaria dtoToInmobiliaria(GetInmobiliariasDto idto){
        return Inmobiliaria.builder()
                .nombre(idto.getNombre())
                .email(idto.getEmail())
                .telefono(idto.getTelefono())
                .id(idto.getId())
                .build();
    }
}
