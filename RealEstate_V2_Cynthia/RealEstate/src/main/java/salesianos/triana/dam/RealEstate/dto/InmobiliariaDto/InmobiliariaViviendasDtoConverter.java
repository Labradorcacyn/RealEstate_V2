package salesianos.triana.dam.RealEstate.dto.InmobiliariaDto;

import org.springframework.stereotype.Component;
import salesianos.triana.dam.RealEstate.model.Inmobiliaria;


@Component
public class InmobiliariaViviendasDtoConverter {

    public InmobiliariaViviendasDto inmobiliriaToInmoViviDto(Inmobiliaria i) {
        return InmobiliariaViviendasDto.builder()
                .nombre(i.getNombre())
                .id(i.getId())
                .email(i.getEmail())
                .telefono(i.getTelefono())
                .viviendas(i.getViviendas().stream().map(v -> new ViviendaListaDto(v.getId(), v.getTitulo(), v.getTipo(),
                        v.getDireccion(), v.getProvincia(), v.getPoblacion(), v.getAvatar(), v.getPrecio(),
                        v.getMetrosCuadrados(), v.getNumBanos(), v.getNumHabitaciones())).toList())
                .build();
    }
}