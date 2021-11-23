package salesianos.triana.dam.RealEstate.dto.viviendaDto;

import lombok.*;
import salesianos.triana.dam.RealEstate.model.Tipo;

@NoArgsConstructor @AllArgsConstructor @Builder
@Getter @Setter
public class ViviendasTopDto {

    private Long id;
    private Tipo tipo;
    private String titulo, direccion, provincia, poblacion, avatar;
    private float precio;
    private double metrosCuadrados;
    private int numeroBanos;
    private int numeroHabitaciones;
    private int numeroInteresados;

}
