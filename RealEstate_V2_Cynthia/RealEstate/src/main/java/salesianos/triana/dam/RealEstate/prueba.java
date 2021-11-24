package salesianos.triana.dam.RealEstate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import salesianos.triana.dam.RealEstate.users.dto.CreateUserDto;
import salesianos.triana.dam.RealEstate.users.service.UsuarioService;

import javax.annotation.PostConstruct;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class prueba {

    private final UsuarioService usuarioService;

    @PostConstruct
    public void prueba(){
        CreateUserDto userDto = CreateUserDto.builder()
                .fullName("admin")
                .email("admin@gmail.com")
                .password("admin1234")
                .password2("admin1234")
                .telefono("654987689")
                .direccion("C. Condes de Bustillo")
                .avatar("avatar1.jpg")
                .build();

        usuarioService.registrarAdmin(userDto);


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