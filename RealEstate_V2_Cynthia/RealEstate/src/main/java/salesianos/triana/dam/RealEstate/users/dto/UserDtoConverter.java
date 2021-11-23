package salesianos.triana.dam.RealEstate.users.dto;

import org.springframework.stereotype.Component;
import salesianos.triana.dam.RealEstate.users.model.UserRole;
import salesianos.triana.dam.RealEstate.users.model.Usuario;

import java.util.stream.Collectors;

@Component
public class UserDtoConverter {

    public GetUserDto converUserToGetUserDto(Usuario user){
        return GetUserDto.builder()
                .email(user.getEmail())
                .avatar(user.getAvatar())
                .roles(user.getRole().stream()
                        .map(UserRole::name)
                        .collect(Collectors.toSet()))
                .build();
    }
}
