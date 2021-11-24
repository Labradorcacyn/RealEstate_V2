package salesianos.triana.dam.RealEstate.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import salesianos.triana.dam.RealEstate.dto.InmobiliariaDto.ViviendaListaDto;
import salesianos.triana.dam.RealEstate.dto.interesaDto.InteresaDto;
import salesianos.triana.dam.RealEstate.dto.interesaDto.InteresaDtoConverter;
import salesianos.triana.dam.RealEstate.dto.interesaDto.PostInteresaDto;
import salesianos.triana.dam.RealEstate.dto.interesaDto.SumaInteresadosDto;
import salesianos.triana.dam.RealEstate.dto.usuarioDto.GetInteresadosListaDto;
import salesianos.triana.dam.RealEstate.dto.viviendaDto.*;
import salesianos.triana.dam.RealEstate.model.*;
import salesianos.triana.dam.RealEstate.search.ViviendaSpecificationBuilder;
import salesianos.triana.dam.RealEstate.service.*;
import salesianos.triana.dam.RealEstate.users.model.Usuario;
import salesianos.triana.dam.RealEstate.users.service.UsuarioService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vivienda")
@Tag(name = "Controlador de los vivienda")
public class ViviendaController {

    private final ViviendaService viviendaService;
    private final UsuarioService usuarioService;
    private final ViviendaPropietarioConverterDto viviendaPropietarioConverterDto;
    private final ViviendaDetalleDtoConverter viviendaDetalleDtoConverter;
    private final InteresaService interesaService;
    private final ViviendaListaDtoConverter viviendaListaDtoConverter;
    private final InmobiliariaService inmobiliariaService;
    private final ViviendasTopDtoConverter viviendasTopDtoConverter;
    private final InteresaDtoConverter interesaDtoConverter;

    @Operation(summary = "Devuelve la lista de viviendas paginada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado las 9 primeras viviendas por defecto",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "No se encuentra la lista de viviendas",
                    content = @Content)
    })
    @GetMapping ("/")
    public ResponseEntity<?> getAllViviendas ( @PageableDefault(size = 9,page = 0) Pageable pageable){
        Page<Vivienda> lista=viviendaService.findAll(pageable);
        if(lista.isEmpty())
            return ResponseEntity.notFound().build();
        Page<ViviendaListaDto> dtoLista= lista.map(x->viviendaListaDtoConverter.viviendaToViviendaListaDto(x));
        return ResponseEntity.ok(dtoLista);
    }

    @Operation(summary = "Devuelve el detalle en forma de Dto de un propietario y su vivienda")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la vivienda y su propietario",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "No se encuentra la vivienda o el propietario",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ViviendaPropietarioDto> getDetallesViviendasPropietario(@PathVariable ("id") Long id){
        final Optional<Vivienda> v=viviendaService.findById(id);
        if(v.isEmpty() || v.get().getUsuario()==null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(viviendaPropietarioConverterDto.viviendaToViviendaPropietarioDto(viviendaService.findById(id).get()));
    }

    @Operation(summary = "Creas una vivienda y un propietario o lo rescatas de la base de datos (el propietario)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado correctamente",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",
                    description = "Datos incorrectos",
                    content = @Content)
    })

    @PostMapping ("/")
    public ResponseEntity<ViviendaDetalleDto> addVivienda(@RequestBody ViviendaPropietarioDto dto){
        Vivienda v=viviendaPropietarioConverterDto.getVivienda(dto);
        Usuario p= viviendaPropietarioConverterDto.getPropietario(dto);
        if(v.getProvincia()==null|| v.getCodPostal()==null || v.getDireccion()==null||
        v.getDescripcion()==null|| v.getPoblacion()==null || v.getTipo()==null || v.getTitulo()==null)
            return ResponseEntity.badRequest().build();
        if(p.getId()!=null)
            p=  usuarioService.findById(p.getId()).get();
        usuarioService.save(p);
        v.addToPropietario(p);
        viviendaService.save(v);
        return ResponseEntity.status(HttpStatus.CREATED).body(viviendaDetalleDtoConverter.viviendaToDetalleDto(v));
    }

    @Operation(summary = "Añade un nuevo interesa con un interesado existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404",
                    description = "No se encuentra la vivienda",
                    content = @Content),
            @ApiResponse(responseCode = "201",
                    description = "Interes creado correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Interesa.class))
                    }
            )
    })
    @PostMapping("/{id1}/meinteresa/{id2}")
    public ResponseEntity<InteresaDto> addInteresadoVivienda(@PathVariable ("id1")Long idVivienda, @PathVariable("id2") UUID idInteresado, @RequestBody InteresaDto iDto){
        if(viviendaService.findById(idVivienda).isEmpty() || usuarioService.findById(idInteresado).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        Interesa i = interesaDtoConverter.InteresaDtoToInteresa(iDto);
        Usuario interesado = usuarioService.findById(idInteresado).orElse(null);
        Vivienda vivivenda = viviendaService.findById(idVivienda).orElse(null);
        i.addInteresado(interesado,vivivenda);
        interesaService.save(i);
        usuarioService.save(interesado);
        viviendaService.save(vivivenda);

        return ResponseEntity.ok().body(interesaDtoConverter.InteresaToInteresaDto(i));

    }

    @Operation(summary = "Añade un nuevo interesa creando un nuevo interesado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404",
            description = "No se encuentra la vivienda",
            content = @Content),
            @ApiResponse(responseCode = "400",
            description = "Datos introducidos no validos",
            content = @Content),
            @ApiResponse(responseCode = "201",
            description = "Interes creado correctamente",
            content = {
                    @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Interesa.class))
                    }
            )
    })
    @PostMapping("/{id}/meinteresa")
    public ResponseEntity<?> addNuevoInteresadoAVivienda(
            @PathVariable("id") Long id,
            @RequestBody PostInteresaDto dto
    ) {
        if(viviendaService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        if(dto.getFullName() == null || dto.getDireccion() == null || dto.getEmail() == null || dto.getTelefono() == null || dto.getAvatar() == null) {
            ResponseEntity.badRequest().build();
        }
        Vivienda vivienda = viviendaService.findById(id).get();
        Usuario interesado = Usuario.builder()
                .fullName(dto.getFullName())
                .direccion(dto.getDireccion())
                .email(dto.getEmail())
                .avatar(dto.getAvatar())
                .telefono(dto.getTelefono())
                .build();
        usuarioService.save(interesado);
        Interesa interesa = new Interesa();

        if(dto.getMensaje() == null) {
            interesa.addInteresado(interesado, vivienda);
            interesaService.save(interesa);
        }
        else {
            interesa.setMensaje(dto.getMensaje());
            interesa.addInteresado(interesado, vivienda);
            interesaService.save(interesa);
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(interesa);
    }

    @Operation(summary = "Borra un interes de una vivienda")
    @ApiResponses(value = {
            @ApiResponse(description = "No se encuentra la vivienda/interesado",
            responseCode = "404",
            content = @Content),
            @ApiResponse(description = "Se ha borrado el interes por la vivienda exitosamente",
            responseCode = "200",
            content = @Content)
    })
    @DeleteMapping("/{id1}/meinteresa/{id2}")
    public ResponseEntity<?> deleteMeInteresa(
            @Parameter(description = "id de la vivienda") @PathVariable Long id1,
            @Parameter(description = "id del interesado") @PathVariable UUID id2
    ) {
        if(viviendaService.findById(id1).isEmpty() || usuarioService.findById(id2).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Vivienda vivienda = viviendaService.findById(id1).get();
        Usuario interesado = usuarioService.findById(id2).get();
        boolean enc=false;
        vivienda.getIntereses().stream().map(i -> {
            if(i.getUsuario().getId() == interesado.getId()) {
                interesaService.delete(i);
                i.removeInteresa(vivienda, interesado);
                return i;
            }
            return i;
        });
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Borra un interés de una vivienda y la vivienda pero no su interesado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha borrado correctamente",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "No se encuentra la vivienda",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteViviendaAndIntereses (@PathVariable ("id") Long id){
        if(viviendaService.findById(id).isEmpty())
            return ResponseEntity.notFound().build();
       List<Interesa> listaInteresa= interesaService.allInteresaDeUnaVivienda(id);
       interesaService.deleteAll(listaInteresa);
       viviendaService.deleteById(id);
        return ResponseEntity.ok().build();
    }



    @DeleteMapping("/{id}/inmobiliaria")
    public ResponseEntity<?> desasociarViviendaInmobiliaria (@PathVariable ("id") Long id){
        Optional<Vivienda> v = viviendaService.findById(id);
        if (v.isEmpty())
            return ResponseEntity.notFound().build();
        v.get().removeToInmobiliaria(v.get().getInmobiliaria());
        viviendaService.edit(v.get());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Actualiza los datos de una vivienda")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha actualizado correctamente",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "No se encuentra la vivienda",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<ViviendaDetalleDto> putVivienda(@PathVariable Long id, @RequestBody ViviendaDetalleDto dto) {
        if (viviendaService.findById(id).isEmpty())
            return ResponseEntity.noContent().build();

        return ResponseEntity.of(viviendaService.findById(id).map(v->{
            v.setTitulo(dto.getTitulo());
            v.setDescripcion(dto.getDescripcion());
            v.setAvatar(dto.getAvatar());
            v.setLatLng(dto.getLatLng());
            v.setDireccion(dto.getDireccion());
            v.setCodPostal(dto.getCodPostal());
            v.setProvincia(dto.getProvincia());
            v.setPoblacion(dto.getPoblacion());
            v.setTienePiscina(dto.getTienePiscina());
            v.setTieneGaraje(dto.getTieneGaraje());
            v.setTieneAscensor(dto.getTieneAscensor());
            v.setPrecio(dto.getPrecio());
            v.setMetrosCuadrados(dto.getMetrosCuadrados());
            v.setNumHabitaciones(dto.getNumHabitaciones());
            v.setNumBanos(dto.getNumBanos());
            v.setTipo(dto.getTipo());
            viviendaService.save(v);
            return viviendaDetalleDtoConverter.viviendaToDetalleDto(v);
        }));
    }

    @GetMapping("/{id}/interesados")
    public SumaInteresadosDto totalInteresados(@PathVariable Long id){
        Vivienda v = viviendaService.findById(id).get();
        SumaInteresadosDto suma = new SumaInteresadosDto();
        suma.setTotalInteresados(v.getIntereses().size());
        return suma;
    }

    @GetMapping(value = "/filtro/", params = {"search"})
    public ResponseEntity<List<?>> filtroViviendas (@RequestParam ("search") String search){
        ViviendaSpecificationBuilder builder=new ViviendaSpecificationBuilder();

        Pattern pattern=Pattern.compile("(\\w+?)(>|:|<)(\\w+?),");
        Matcher matcher= pattern.matcher(search+",");
        while(matcher.find()){
            builder.with(matcher.group(1),matcher.group(2),matcher.group(3));
        }
        Specification<Vivienda> spec=builder.build();
        return  buildResponseFromQuery(viviendaService.viviendaConSpecification(spec),viviendaListaDtoConverter::viviendaToViviendaListaDto);
    }

    // Muy bien pensado para ahorrar
    private ResponseEntity<List<?>> buildResponseFromQuery(List<Vivienda> data, Function<Vivienda, ViviendaListaDto> function) {
        if (data.isEmpty())
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(data.stream()
                    .map(function)
                    .collect(Collectors.toList())
            );
    }

    @Operation(summary = "Lista las viviendas n con mas interesados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404",
            description = "No se han encontrado viviendas",
            content = @Content),
            @ApiResponse(responseCode = "400",
            description = "Se han introducido mal los parametros",
            content = @Content),
            @ApiResponse(responseCode = "200",
            description = "Se han encontrado las viviendas deseadas",
            content = {
                    @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = GetInteresadosListaDto.class)))
            })
    })
    @GetMapping(value = "/top", params = {"n"})
    public ResponseEntity<?> topViviendas(
            @RequestParam(defaultValue = "5") long n
    ) {
        List<Vivienda> lista = viviendaService.findAll();
        if(lista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        if(n<1) {
            ResponseEntity.badRequest().build();
        }
        List<ViviendasTopDto> dtos = lista.stream().map(viviendasTopDtoConverter::viviendaToViviendasTopDto).sorted(
                (v1, v2) -> v2.getNumeroInteresados() - v1.getNumeroInteresados()).limit(n).toList();
        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Vinculas una vivienda con una inmobiliaria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha borrado correctamente",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "No se encuentra la vivienda",
                    content = @Content)
    })
    @PostMapping("/{id}/inmobiliaria/{id2}")
    public ResponseEntity<?> asociarViviendaInmobiliaria (@PathVariable ("id") Long id1, @PathVariable("id2") Long id2){
        Optional<Inmobiliaria> i=inmobiliariaService.findById(id2);
        Optional<Vivienda> v=viviendaService.findById(id1);
        if(i.isEmpty() || v.isEmpty())
            return ResponseEntity.notFound().build();
        v.get().addToInmobiliaria(i.get());
        viviendaService.edit(v.get());
        inmobiliariaService.edit(i.get());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
