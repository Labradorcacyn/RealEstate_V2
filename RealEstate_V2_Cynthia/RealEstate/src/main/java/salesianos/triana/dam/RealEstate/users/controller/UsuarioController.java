package salesianos.triana.dam.RealEstate.users.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import salesianos.triana.dam.RealEstate.dto.usuarioDto.*;
import salesianos.triana.dam.RealEstate.security.dto.JwtUserResponse;
import salesianos.triana.dam.RealEstate.security.dto.LoginDto;
import salesianos.triana.dam.RealEstate.security.jwt.JwtProvider;
import salesianos.triana.dam.RealEstate.users.dto.CreateGestorDto;
import salesianos.triana.dam.RealEstate.users.dto.CreateUserDto;
import salesianos.triana.dam.RealEstate.users.dto.GetUserDto;
import salesianos.triana.dam.RealEstate.users.dto.UserDtoConverter;
import salesianos.triana.dam.RealEstate.users.model.UserRole;
import salesianos.triana.dam.RealEstate.users.model.Usuario;
import salesianos.triana.dam.RealEstate.users.service.UsuarioService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor

@Tag(name = "Controlador de los controller")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final GetPropietarioDtoConverter getPropietarioDtoConverter;
    private final PropietarioEscuetoDtoConverter propietarioEscuetoDtoConverter;
    private final PropietarioViviendaDtoConverter propietarioViviendaDtoConverter;
    private final GetInteresadosListaDtoConverter getInteresadosListaDtoConverter;
    private final GetInteresadosDtoConverter dtoConverter;
    private final DetailInteresadoDtoConverter detailInteresadoDtoConverter;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final UserDtoConverter userDtoConverter;

    /********************************************CONTROLADORES AUTH*******************************************************/

    @Operation(summary = "Login usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se realiza el login correctamente",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "No se realiza el login",
                    content = @Content)
    })
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginDto.getEmail(),
                                loginDto.getPassword()
                        )
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);


        Usuario user = (Usuario) authentication.getPrincipal();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(convertUserToJwtUserResponse(user, jwt));

    }

    @Operation(summary = "Devuelve los datos del usuario Logeado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Te devuelve los datos",
                    content = { @Content(mediaType = "application/json")})
    })
    @GetMapping("/me")
    public ResponseEntity<?> quienSoyYo(@AuthenticationPrincipal Usuario user){
        return ResponseEntity.ok(convertUserToJwtUserResponse(user, null));
    }

    private JwtUserResponse convertUserToJwtUserResponse(Usuario user, String jwt) {
        return JwtUserResponse.builder()
                .fullName(user.getFullName())
                .email(user.getEmail())
                .avatar(user.getAvatar())
                .role(user.getRole().name())
                .token(jwt)
                .build();
    }

    @PostMapping("/auth/register/user")
    public ResponseEntity<GetUserDto> newUser (@RequestBody CreateUserDto createUserDto){
        Usuario saved = usuarioService.registrarUsuario(createUserDto);

        if (saved == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.ok(userDtoConverter.converUserToGetUserDto(saved));
    }

    @PostMapping("/auth/register/admin")
    public ResponseEntity<GetUserDto> newAdmin (@RequestBody CreateUserDto createUserDto){
        Usuario saved = usuarioService.registrarAdmin(createUserDto);

        if (saved == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.ok(userDtoConverter.converUserToGetUserDto(saved));

    }

    @PostMapping("/auth/register/gestor")
    public ResponseEntity<GetUserDto> newGestor (@RequestBody CreateGestorDto createGestorDto){
        Usuario saved = usuarioService.registrarGestor(createGestorDto);

        if (saved == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.ok(userDtoConverter.converUserToGetUserDto(saved));
    }

    /*@PostMapping("/auth/register/user")
    public ResponseEntity<GetUserDto> newUser(@RequestBody CreateUserDto newUser){
        try{
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(userDtoConverter.converUserToGetUserDto(usuarioService.newUSer(newUser)));
        }catch (DataIntegrityViolationException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }
*/
    /*****************************************CONTROLADORES PROPIETARIO****************************************************/

    @Operation(summary = "Devuelve la lista de propietarios paginada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado las 9 primero propietarios por defecto",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "No se encuentra la lista de propietarios",
                    content = @Content)
    })
    @GetMapping("/propietario/")
    public ResponseEntity<?> getAllProp(@PageableDefault(size = 9, page = 0) Pageable pageable){
        Page<Usuario> lista = usuarioService.findAll(pageable);

        if(lista.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            Page<GetPropietarioDto> listadto = lista.map(x -> getPropietarioDtoConverter.propietarioToDto(x));
            return ResponseEntity.ok().body(listadto);
        }
    }

    @Operation(summary = "Devuelve los detalles de un propietario y su lista de viviendas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado el propietario",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "No se encuentra el propietario",
                    content = @Content)
    })
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/propietario/{id}")
    public ResponseEntity <PropietarioViendaDto> getDetailPropietario(@PathVariable ("id") UUID id, @AuthenticationPrincipal Usuario usuario){

        Optional<Usuario> u = usuarioService.findById(id);
        Usuario propietario = null;

        if(u.isPresent()) {
            propietario = u.get();

            if (usuario.getRole().equals(UserRole.ADMIN) || usuario.getId().equals(propietario.getId())) {
                PropietarioViendaDto propietarioViendaDto = propietarioViviendaDtoConverter.propietarioToPropietarioVviendaDto(propietario);
                return ResponseEntity.ok().body(propietarioViendaDto);
            }else {
                return ResponseEntity.status(403).build();
            }
        }
            return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Devuelve la lista de propietarios SIN paginar y de manera escueta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado los propietarios",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "No se encuentra la lista de propietarios",
                    content = @Content)
    })
    @GetMapping("/propietario/lista")
    public ResponseEntity<List<PropietarioEscuetoDto>> allInteresado(){
        List<Usuario> propietarios= usuarioService.findAll();
        if (propietarios.isEmpty())
            return  ResponseEntity.notFound().build();
        return ResponseEntity.ok().body( propietarios.stream().
                map(propietarioEscuetoDtoConverter::propietarioToPropietarioEscuetoDto).collect(Collectors.toList()));
    }

    @Operation(summary = "Borra un propietario y sus viviendas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha borrado con exito",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "No se encuentra el propietario",
                    content = @Content)
    })
    @DeleteMapping("/propietario/{id}")
    public ResponseEntity<?> deletePropietario(@PathVariable("id") UUID idProp, @AuthenticationPrincipal Usuario usuario) {
        Optional<Usuario> optPropietario = usuarioService.findById(idProp);
        Usuario propietario = null;

        if (optPropietario.isPresent()) {
            propietario = optPropietario.get();

            if (usuario.getRole().equals(UserRole.ADMIN) || usuario.getId().equals(propietario.getId())) {
                usuarioService.deleteById(idProp);
                //return ResponseEntity.ok().build();
                return ResponseEntity.noContent().build();

            } else {
                return ResponseEntity.status(403).build();
            }
        }
        return ResponseEntity.noContent().build();
    }
    /******************************************CONTROLADORES INTERESA*****************************************************/

    /*@Operation(summary = "Muestra la lista de todos los interesados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ningun interesado",
                    content = @Content),
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado todos los interesados",
                    content = {
                            @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = GetInteresadosListaDto.class)))
                    })
    })

    @GetMapping("/interesado/")
    public ResponseEntity<List<GetInteresadosListaDto>> getAllInteresados() {
        List<Usuario> lista = usuarioService.getInteresados();
        if(lista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<GetInteresadosListaDto> dtos = lista.stream().map(getInteresadosListaDtoConverter::interesadoToGetInteresadosListaDto).toList();
        return ResponseEntity.ok(dtos);
    }*/

    @Operation(summary = "Encuentra y devuelve un interesado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado al interesado",
                    content = @Content),
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado al interesado",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = DetailInteresadoDto.class))
                    })
    })
    @GetMapping("/interesado/{id}")
    public ResponseEntity<DetailInteresadoDto> getInteresado(
            @PathVariable UUID id
    ) {
        if(usuarioService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        DetailInteresadoDto dto = detailInteresadoDtoConverter.interesadoToDetailInteresadoDto(usuarioService.findById(id).get());
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Obtiene la lista de interesados")
    @GetMapping("/interesado/count")
    public List<GetInteresadosDto> allInteresados(){
        GetInteresadosDto allInteresados = new GetInteresadosDto();
        return usuarioService.findAll().stream().map(dtoConverter::InteresadoToGetInteresadosDto).collect(Collectors.toList());
    }
}


