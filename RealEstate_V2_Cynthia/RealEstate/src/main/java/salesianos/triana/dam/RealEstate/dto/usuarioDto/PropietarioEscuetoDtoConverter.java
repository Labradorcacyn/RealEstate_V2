package salesianos.triana.dam.RealEstate.dto.usuarioDto;

import org.springframework.stereotype.Component;
import salesianos.triana.dam.RealEstate.users.model.Usuario;

@Component
public class PropietarioEscuetoDtoConverter {

    public PropietarioEscuetoDto propietarioToPropietarioEscuetoDto(Usuario p){
        return PropietarioEscuetoDto.builder()
                .id(p.getId())
                .fullName(p.getFullName())
                .build();
    }

}
