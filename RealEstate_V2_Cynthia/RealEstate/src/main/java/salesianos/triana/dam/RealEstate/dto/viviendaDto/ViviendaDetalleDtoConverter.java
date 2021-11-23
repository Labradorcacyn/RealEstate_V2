package salesianos.triana.dam.RealEstate.dto.viviendaDto;

import org.springframework.stereotype.Component;
import salesianos.triana.dam.RealEstate.model.Vivienda;

@Component
public class ViviendaDetalleDtoConverter {

    public ViviendaDetalleDto viviendaToDetalleDto (Vivienda v){
        return ViviendaDetalleDto.builder()
                .titulo(v.getTitulo())
                .direccion(v.getDireccion())
                .avatar(v.getAvatar())
                .latLng(v.getLatLng())
                .descripcion(v.getDescripcion())
                .codPostal(v.getCodPostal())
                .poblacion(v.getPoblacion())
                .provincia(v.getProvincia())
                .tienePiscina(v.getTienePiscina())
                .tieneAscensor(v.getTieneAscensor())
                .tieneGaraje(v.getTieneGaraje())
                .numHabitaciones(v.getNumHabitaciones())
                .numBanos(v.getNumBanos())
                .precio(v.getPrecio())
                .tipo(v.getTipo())
                .metrosCuadrados(v.getMetrosCuadrados())
                .build();
    }
}
