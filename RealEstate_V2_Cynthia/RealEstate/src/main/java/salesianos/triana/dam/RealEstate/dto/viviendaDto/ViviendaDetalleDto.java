package salesianos.triana.dam.RealEstate.dto.viviendaDto;

import lombok.Builder;
import lombok.Getter;
import salesianos.triana.dam.RealEstate.model.Tipo;

@Getter
@Builder
public class ViviendaDetalleDto {

    private String titulo, descripcion, avatar, latLng, direccion, codPostal, poblacion, provincia;
    private Boolean tienePiscina, tieneAscensor, tieneGaraje;
    private Integer numHabitaciones, numBanos;
    private Float precio;
    private Double metrosCuadrados;
    private Tipo tipo;
}
