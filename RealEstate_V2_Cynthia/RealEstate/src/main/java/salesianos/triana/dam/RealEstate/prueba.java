package salesianos.triana.dam.RealEstate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import salesianos.triana.dam.RealEstate.model.Inmobiliaria;
import salesianos.triana.dam.RealEstate.model.Tipo;
import salesianos.triana.dam.RealEstate.model.Vivienda;
import salesianos.triana.dam.RealEstate.service.InmobiliariaService;
import salesianos.triana.dam.RealEstate.service.ViviendaService;
import salesianos.triana.dam.RealEstate.users.dto.CreateGestorDto;
import salesianos.triana.dam.RealEstate.users.dto.CreateUserDto;
import salesianos.triana.dam.RealEstate.users.model.Usuario;
import salesianos.triana.dam.RealEstate.users.service.UsuarioService;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class prueba {

    private final UsuarioService usuarioService;
    private final ViviendaService viviendaService;
    private final InmobiliariaService inmobiliariaService;

    @PostConstruct
    public void prueba(){
        CreateUserDto admin = CreateUserDto.builder()
                .fullName("admin")
                .email("admin@gmail.com")
                .password("admin1234")
                .password2("admin1234")
                .telefono("654987689")
                .direccion("C. Condes de Bustillo")
                .avatar("avatar1.jpg")
                .build();

        usuarioService.registrarAdmin(admin);

        CreateUserDto propietario = CreateUserDto.builder()
                .fullName("Carlos Pino")
                .email("carlospino@gmail.com")
                .password("propietario")
                .password2("propietario")
                .telefono("654892034")
                .direccion("C. Virgen de la Guía")
                .avatar("avatar4.jpg")
                .build();

        Usuario prop = usuarioService.registrarUsuario(propietario);

        CreateGestorDto gestor = CreateGestorDto.builder()
                .fullName("Marta López")
                .email("martalopez@gmail.com")
                .password("gestor")
                .password2("gestor")
                .telefono("654389087")
                .direccion("C. Canopus")
                .avatar("avatar3.jpg")
                .inmobiliaria(1L)
                .build();

         Usuario g = usuarioService.registrarGestor(gestor);

        Vivienda v = Vivienda.builder()
                .id(1L)
                .titulo("Casa historica")
                .direccion("C/ El alfanje N 24 5B")
                .avatar("avatar1.jpg")
                .codPostal("41016")
                .descripcion("Casa de estilo colonial en la rampa de gines con toque mozarabes")
                .latLng("231.21 0241.13")
                .metrosCuadrados(120.50)
                .provincia("Sevilla")
                .poblacion("Sevilla")
                .numBanos(2)
                .numHabitaciones(3)
                .precio(150000F)
                .tieneGaraje(true)
                .tieneAscensor(false)
                .tienePiscina(true)
                .tipo(Tipo.Obra_Nueva)
                //.createBy(UUID.fromString("c0a838017d581afc817d581b013a0000"))
                .build();

        Optional<Inmobiliaria> inmo = inmobiliariaService.findById(2L);
        v.addToPropietario(prop);
        viviendaService.save(v);
        /*if(inmo.isPresent()) {
            g.addInmobiliariaToPropietario(inmo.get());
            usuarioService.save(g);
            inmobiliariaService.save(inmo.get());
        }*/



        //String search="fecha<umple=1997&nombre=Jose&mayor=18";
        //fdsajklñfdsajñ/?search=fechaCumple<1997,nombre:Jose,loquesea>18
        String search = "fechaCumple<1997,nombre:Jose,loquesea>18";
        Pattern pattern=Pattern.compile("(\\w+?)(<|>|:)(\\w+?)");
        Matcher matcher= pattern.matcher(search);
        while(matcher.find()){
            System.out.println(matcher.group(1) + matcher.group(2)+matcher.group(3));
        }
    }
}