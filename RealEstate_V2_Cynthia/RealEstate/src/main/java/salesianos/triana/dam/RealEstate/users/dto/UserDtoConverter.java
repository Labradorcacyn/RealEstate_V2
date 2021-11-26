package salesianos.triana.dam.RealEstate.users.dto;

import org.springframework.stereotype.Component;
import salesianos.triana.dam.RealEstate.users.model.Usuario;

@Component
public class UserDtoConverter {

    public GetUserDto converUserToGetUserDto(Usuario user){
        return GetUserDto.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .avatar(user.getAvatar())
                .role(user.getRole().name())
                .build();
    }

}
