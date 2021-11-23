package salesianos.triana.dam.RealEstate.dto.viviendaDto;

import org.springframework.stereotype.Component;
import salesianos.triana.dam.RealEstate.dto.InmobiliariaDto.ViviendaListaDto;
import salesianos.triana.dam.RealEstate.model.Vivienda;

@Component
public class ViviendaListaDtoConverter {

    public ViviendaListaDto viviendaToViviendaListaDto(Vivienda v){
       return ViviendaListaDto.builder()
               .titulo(v.getTitulo())
               .precio(v.getPrecio())
               .metrosCuadrados(v.getMetrosCuadrados())
               .numeroBanos(v.getNumBanos())
               .numeroHabitaciones(v.getNumHabitaciones())
               .tipo(v.getTipo())
               .direccion(v.getDireccion())
               .poblacion(v.getPoblacion())
               .provincia(v.getProvincia())
               .id(v.getId())
               .avatar(v.getAvatar())
               .build();
    }
}
