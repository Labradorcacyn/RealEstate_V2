package salesianos.triana.dam.RealEstate.dto.viviendaDto;

import org.springframework.stereotype.Component;
import salesianos.triana.dam.RealEstate.model.Vivienda;

@Component
public class ViviendasTopDtoConverter {

    public ViviendasTopDto viviendaToViviendasTopDto(Vivienda v) {
        return ViviendasTopDto.builder()
                .id(v.getId())
                .tipo(v.getTipo())
                .titulo(v.getTitulo())
                .direccion(v.getDireccion())
                .provincia(v.getProvincia())
                .poblacion(v.getPoblacion())
                .avatar(v.getAvatar())
                .precio(v.getPrecio())
                .metrosCuadrados(v.getMetrosCuadrados())
                .numeroBanos(v.getNumBanos())
                .numeroHabitaciones(v.getNumHabitaciones())
                .numeroInteresados(v.getIntereses().size())
                .build();
    }

}
