package salesianos.triana.dam.RealEstate.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import salesianos.triana.dam.RealEstate.error.ApiError;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
@RequiredArgsConstructor
public class CustomBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    //Nos permite sobreescribir algunos métodos
    private final ObjectMapper mapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setHeader("WWWW-Authenticate","Basic realm=" + getRealmName() + "\"");
        response.setContentType("application/json");
        response.setStatus(HttpStatus.UNAUTHORIZED.value()); //Para usar siempre HttpStatus

        //Trabajamos en nuestro ApiError
        ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, authException.getMessage());
        String strApiError = mapper.writeValueAsString(apiError); //Lo queremos transformar en una cadena de caracteres

        PrintWriter writer = response.getWriter();
        writer.println(strApiError);
    }

    @PostConstruct // Sin el nombre del reino se produciría un error
    public void initRealName(){
        setRealmName("realestate.net");
    }
}
