package salesianos.triana.dam.RealEstate.users.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import salesianos.triana.dam.RealEstate.users.dto.CreateUserDto;
import salesianos.triana.dam.RealEstate.users.model.UserRole;
import salesianos.triana.dam.RealEstate.users.model.Usuario;
import salesianos.triana.dam.RealEstate.users.repository.UsuarioRepository;
import salesianos.triana.dam.RealEstate.service.base.BaseService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor  // Para poder inyectar
public class UsuarioService extends BaseService<Usuario, UUID, UsuarioRepository>
    implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;

    public List<Usuario> getInteresados() {return usuarioRepository.findByIdNotNull();}

    public Optional<Usuario> findByEmail (String email){
        return this.repository.findFirstByEmail(email);
    }
/*
    //Método para crear usuario
    public Usuario newUSer(CreateUserDto user){ //Recogemos nuestro CreateUserDto
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Stream.of(UserRole.PROPIETARIO).collect(Collectors.toSet()));
        return save(user);

        //Comprobamos si las contraseñas cohinciden
        //Creamos un usuario a partir del dto para almacenarlo a través del servicio
        if(user.getPassword().contentEquals(user.getPassword2())){
            Usuario usuario = Usuario.builder()
                    .email(user.getEmail())
                    .password(passwordEncoder.encode(user.getPassword()))
                    .avatar(user.getAvatar())
                    .role(Stream.of(UserRole.PROPIETARIO).collect(Collectors.toSet()))
                    .build();
            try{
                return save(usuario);
            }catch (DataIntegrityViolationException ex){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ese usuario ya existe");
            }

        }else{
            throw new NewUserWithDifferentPasswordException();
        }
    }*/

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.repository.findFirstByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException(email + " no encontrado"));
    }
}