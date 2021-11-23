
package salesianos.triana.dam.RealEstate.dto.usuarioDto;

import org.springframework.stereotype.Component;
import salesianos.triana.dam.RealEstate.users.model.Usuario;

@Component
public class GetInteresadosDtoConverter {
    public GetInteresadosDto InteresadoToGetInteresadosDto(Usuario i){
        return GetInteresadosDto.builder()
                .id(i.getId())
                .nombre(i.getNombre())
                .apellidos(i.getApellidos())
                .build();
    }
}