package salesianos.triana.dam.RealEstate.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import salesianos.triana.dam.RealEstate.dto.InmobiliariaDto.*;
import salesianos.triana.dam.RealEstate.model.Inmobiliaria;
import salesianos.triana.dam.RealEstate.service.InmobiliariaService;
import salesianos.triana.dam.RealEstate.users.model.UserRole;
import salesianos.triana.dam.RealEstate.users.model.Usuario;
import salesianos.triana.dam.RealEstate.users.service.UsuarioService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inmobiliaria")
@Tag(name = "Controlador de los inmobiliaria")
public class InmobiliariaController {

    private final InmobiliariaService inmobiliariaService;
    private final GetInmobiliasDtoConverter getInmobiliasDtoConverter;
    private final InmobiliariaEscuetoDtoConverter inmobiliariaEscuetoDtoConverter;
    private final InmobiliariaViviendasDtoConverter inmobiliariaViviendasDtoConverter;
    private final UsuarioService usuarioService;

    @Operation(summary = "Devuelve la lista de inmobiliarias paginada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado las 9 primeras inmobiliarias por defecto",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "No se encuentra la lista de inmobiliarias",
                    content = @Content)
    })
    @GetMapping("/")
    public ResponseEntity<?> getListInmobiliaria(@PageableDefault(size = 9 ,page = 0) Pageable p) {
        Page<Inmobiliaria> lista = inmobiliariaService.findAll(p);
        if (lista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Page<GetInmobiliariasDto> listaDto = lista.map(i -> getInmobiliasDtoConverter.inmobiliariaToInmobiliariasDto(i));
        return ResponseEntity.ok(listaDto);
    }

    @Operation(summary = "Crea una nueva inmobiliaria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado correctamente",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500",
                    description = "No se ha podido crear la inmobiliaria",
                    content = @Content)
    })
    @PostMapping("/")
    public ResponseEntity<Inmobiliaria> addInmo(@RequestBody GetInmobiliariasDto i){
        if (i.getNombre().isEmpty() || i.getEmail().isEmpty() || i.getTelefono().isEmpty())
            return ResponseEntity.badRequest().build();
        Inmobiliaria nueva = getInmobiliasDtoConverter.dtoToInmobiliaria(i);
        return ResponseEntity.status(HttpStatus.CREATED).body(inmobiliariaService.save(nueva));
    }

    @Operation(summary = "Borra una inmobiliaria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha borrado correctamente",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(description = "No se encuentra la Inmobiliaria",
                    responseCode = "404",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeInmo(@PathVariable Long id, @AuthenticationPrincipal Usuario usuario){
        Optional<Inmobiliaria> inmo = inmobiliariaService.findById(id);

        if(inmo.isPresent()) {
                //if (usuario.getRole().equals(UserRole.ADMIN) || inmo.get().getGestores().forEach(x -> {
                 //  x.getId().equals(usuario.getId());
               // })) {
                    inmobiliariaService.delete(inmo.get());
                    return ResponseEntity.noContent().build();
                //}
        }
        if (inmobiliariaService.findById(id).isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Encontrar una inmobiliaria por la id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(description = "No se encuentra la Inmobiliaria",
                    responseCode = "404",
                    content = @Content)
    })
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/{id}")
    public ResponseEntity<InmobiliariaViviendasDto> findByIdInmoVivi(@PathVariable Long id){
        Optional<Inmobiliaria> i = inmobiliariaService.findById(id);

        if (i.isEmpty())
            return ResponseEntity.notFound().build();
        InmobiliariaViviendasDto inmoVivi = inmobiliariaViviendasDtoConverter.inmobiliriaToInmoViviDto(i.get());

        return ResponseEntity.ok().body(inmoVivi);
    }

    @GetMapping("/all")
    public ResponseEntity<List<InmobiliariaEscuetoDto>> allInmoShort (){
        if(inmobiliariaService.findAll().isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(inmobiliariaService.findAll().stream().map(inmobiliariaEscuetoDtoConverter::inmobiliariaToInmobiliariaEscuetoDto).collect(Collectors.toList()));
    }

    /******************************************CONTROLADORES GESTORES*****************************************************/

    @Operation(summary = "Devuelve la lista de gestores")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Te devuelve la lista de gestores",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "No se encuentra la lista de gestores",
                    content = @Content)
    })
    @GetMapping("/{id}/gestor/")
    public ResponseEntity<List<Usuario>> Allgestores(@PathVariable Long id, @AuthenticationPrincipal Usuario usuario){
        Optional<Inmobiliaria> inmo = inmobiliariaService.findById(id);
        Optional<Usuario> gestor = usuarioService.findById(usuario.getId());
        List<Usuario> gestores = null;

        if(gestor.isPresent()){
            if(inmo.isPresent()){

            if(usuario.getRole().equals(UserRole.ADMIN) || inmo.get().getId().equals(usuario.getInmobiliaria_prop().getId())){
                return ResponseEntity.ok().body(inmo.get().getGestores());
            }
        }else
            return ResponseEntity.notFound().build();
        }
       return ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Borra un gestor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404",
                    description = "Se borra y te devuelve no encontrado",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "No se encuentra el gestor",
                    content = @Content)
    })
    @DeleteMapping("/gestor/{id}")
    public ResponseEntity<?> DeleteGestor(@PathVariable UUID id, @AuthenticationPrincipal Usuario usuario) {
        Optional<Inmobiliaria> inmo = inmobiliariaService.findById(usuario.getInmobiliaria_prop().getId());
        List<Usuario> gestores = null;
        if(usuario.getRole().equals(UserRole.ADMIN) || inmo.get().getId().equals(usuario.getInmobiliaria_prop().getId())){
            usuarioService.deleteById(id);
            return ResponseEntity.notFound().build();

        }
        if(usuarioService.findById(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.badRequest().build();
    }
}