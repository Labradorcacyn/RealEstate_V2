package salesianos.triana.dam.RealEstate;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class prueba {

    @PostConstruct
    public void prueba(){
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
