package salesianos.triana.dam.RealEstate.dto.usuarioDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import salesianos.triana.dam.RealEstate.dto.viviendaDto.ViviendaListaDtoConverter;
import salesianos.triana.dam.RealEstate.users.model.Usuario;

@Component
public class DetailInteresadoDtoConverter {

    @Autowired
    private ViviendaListaDtoConverter viviendaListaDtoConverter;

    public DetailInteresadoDto interesadoToDetailInteresadoDto(Usuario i){
        return DetailInteresadoDto.builder()
                .fullName(i.getFullName())
                .direccion(i.getDireccion())
                .email(i.getEmail())
                .telefono(i.getTelefono())
                .avatar(i.getAvatar())
                .viviendas(i.getIntereses().stream().map(m -> {
                    return viviendaListaDtoConverter.viviendaToViviendaListaDto(m.getVivienda());
                }).toList())
                .build();
    }

}
