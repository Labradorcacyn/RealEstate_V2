package salesianos.triana.dam.RealEstate.dto.usuarioDto;

import org.springframework.stereotype.Component;
import salesianos.triana.dam.RealEstate.users.model.Usuario;

@Component
public class GetPropietarioDtoConverter {

    public GetPropietarioDto propietarioToDto(Usuario p){
        return GetPropietarioDto.builder()
                .id(p.getId())
                .nombre(p.getNombre())
                .apellidos(p.getApellidos())
                .avatar(p.getAvatar())
                .direccion(p.getDireccion())
                .email(p.getEmail())
                .telefono(p.getTelefono())
                .build();
    }

    public Usuario dtoToPropietario(GetPropietarioDto pdto){
        return Usuario.builder()
                .id(pdto.getId())
                .nombre(pdto.getNombre())
                .apellidos(pdto.getApellidos())
                .avatar(pdto.getAvatar())
                .direccion(pdto.getDireccion())
                .email(pdto.getEmail())
                .telefono(pdto.getTelefono())
                .build();
    }
}
