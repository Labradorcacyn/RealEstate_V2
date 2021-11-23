package salesianos.triana.dam.RealEstate.dto.InmobiliariaDto;

import lombok.Builder;
import lombok.Getter;
import salesianos.triana.dam.RealEstate.model.Tipo;

@Getter
@Builder
public class ViviendaListaDto {

    private Long id;
    private String titulo;
    private Tipo tipo;
    private String direccion,provincia,poblacion,avatar;
    private float precio;
    private double metrosCuadrados;
    private int numeroBanos;
    private int numeroHabitaciones;

}
