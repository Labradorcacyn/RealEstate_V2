package salesianos.triana.dam.RealEstate.users.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import salesianos.triana.dam.RealEstate.model.Inmobiliaria;
import salesianos.triana.dam.RealEstate.service.InmobiliariaService;
import salesianos.triana.dam.RealEstate.users.dto.CreateGestorDto;
import salesianos.triana.dam.RealEstate.users.dto.CreateUserDto;
import salesianos.triana.dam.RealEstate.users.model.UserRole;
import salesianos.triana.dam.RealEstate.users.model.Usuario;
import salesianos.triana.dam.RealEstate.users.repository.UsuarioRepository;
import salesianos.triana.dam.RealEstate.service.base.BaseService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor  // Para poder inyectar
public class UsuarioService extends BaseService<Usuario, UUID, UsuarioRepository>
    implements UserDetailsService {

    private final InmobiliariaService inmobiliariaService;
    private final PasswordEncoder passwordEncoder;

    public Optional<Usuario> findByEmail (String email){
        return this.repository.findFirstByEmail(email);
    }

    public List<Usuario> loadUserByRole(UserRole role){return this.repository.findByRole(role);};

    //public Page<Usuario> findAllByRole(UserRole userRole, Pageable pageable){return this.findAllByRole(userRole, pageable);};

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

    public Usuario registrarUsuario(CreateUserDto createUserDto){
        if(createUserDto.getPassword().contentEquals((createUserDto.getPassword2()))){
            Usuario usuario = Usuario.builder()
                    .fullName(createUserDto.getFullName())
                    .avatar(createUserDto.getAvatar())
                    .email(createUserDto.getEmail())
                    .direccion(createUserDto.getDireccion())
                    .telefono(createUserDto.getTelefono())
                    .password(passwordEncoder.encode(createUserDto.getPassword()))
                    .role(UserRole.PROPIETARIO)
                    .build();
            try{
                return save(usuario);
            }catch (DataIntegrityViolationException ex){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ese usuario ya existe");
            }
        }else{
            return null;
        }
    }

    public Usuario registrarAdmin(CreateUserDto createUserDto){
        if(createUserDto.getPassword().contentEquals((createUserDto.getPassword2()))){
            Usuario usuario = Usuario.builder()
                    .fullName(createUserDto.getFullName())
                    .avatar(createUserDto.getAvatar())
                    .email(createUserDto.getEmail())
                    .direccion(createUserDto.getDireccion())
                    .telefono(createUserDto.getTelefono())
                    .password(passwordEncoder.encode(createUserDto.getPassword()))
                    .role(UserRole.ADMIN)
                    .build();
            try{
                return save(usuario);
            }catch (DataIntegrityViolationException ex){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ese usuario ya existe");
            }
        }else{
            return null;
        }
    }
    public Usuario registrarGestor(CreateGestorDto createGestorDto){
        if(createGestorDto.getPassword().contentEquals((createGestorDto.getPassword2()))){
            Usuario usuario = Usuario.builder()
                    .fullName(createGestorDto.getFullName())
                    .avatar(createGestorDto.getAvatar())
                    .email(createGestorDto.getEmail())
                    .direccion(createGestorDto.getDireccion())
                    .telefono(createGestorDto.getTelefono())
                    .password(passwordEncoder.encode(createGestorDto.getPassword()))
                    .role(UserRole.GESTOR)
                    .inmobiliaria_prop(null)
                    .build();
            Optional <Inmobiliaria> inmobiliaria = inmobiliariaService.findById(createGestorDto.getInmobiliaria());

            if(inmobiliaria.isPresent()){
                usuario.addInmobiliariaToPropietario(inmobiliaria.get());
            }

            try{
                return save(usuario);
            }catch (DataIntegrityViolationException ex){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ese usuario ya existe");
            }
        }else{
            return null;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.repository.findFirstByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException(email + " no encontrado"));
    }

}