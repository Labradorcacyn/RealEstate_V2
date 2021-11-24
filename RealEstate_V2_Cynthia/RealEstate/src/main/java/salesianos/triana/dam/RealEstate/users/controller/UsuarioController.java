package salesianos.triana.dam.RealEstate.users.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import salesianos.triana.dam.RealEstate.dto.usuarioDto.*;
import salesianos.triana.dam.RealEstate.users.dto.CreateUserDto;
import salesianos.triana.dam.RealEstate.users.dto.GetUserDto;
import salesianos.triana.dam.RealEstate.users.dto.UserDtoConverter;
import salesianos.triana.dam.RealEstate.users.model.Usuario;
import salesianos.triana.dam.RealEstate.users.service.UsuarioService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/propietario")
@Tag(name = "Controlador de los controller")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final GetPropietarioDtoConverter getPropietarioDtoConverter;
    private final PropietarioEscuetoDtoConverter propietarioEscuetoDtoConverter;
    private final PropietarioViviendaDtoConverter propietarioViviendaDtoConverter;
    private final UserDtoConverter userDtoConverter;

    @Operation(summary = "Devuelve la lista de propietarios paginada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado las 9 primero propietarios por defecto",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "No se encuentra la lista de propietarios",
                    content = @Content)
    })
    @GetMapping("/")
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
    @GetMapping("/{id}")
    public ResponseEntity <PropietarioViendaDto> getDetailPropietario(@PathVariable ("id") UUID id){
        Optional<Usuario> p = usuarioService.findById(id);
        if(p.isEmpty())
            return ResponseEntity.noContent().build();
        PropietarioViendaDto propietarioViendaDto = propietarioViviendaDtoConverter.propietarioToPropietarioVviendaDto(p.get());
        return ResponseEntity.ok().body(propietarioViendaDto);

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
    @GetMapping("/lista")
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
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePropietario(@PathVariable("id") UUID idProp) {
        if (usuarioService.findById(idProp).isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            usuarioService.deleteById(idProp);
            return ResponseEntity.ok().build();
        }
    }

    /******************************************************************************************************/

    @PostMapping("/nuevo")
    public ResponseEntity<GetUserDto> newUser(@RequestBody CreateUserDto newUser){
        try{
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(userDtoConverter.converUserToGetUserDto(usuarioService.newUSer(newUser)));
        }catch (DataIntegrityViolationException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

}