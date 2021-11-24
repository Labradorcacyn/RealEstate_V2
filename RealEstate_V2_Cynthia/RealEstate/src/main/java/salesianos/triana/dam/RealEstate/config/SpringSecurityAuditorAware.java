package salesianos.triana.dam.RealEstate.config;

import lombok.extern.java.Log;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import salesianos.triana.dam.RealEstate.users.model.Usuario;

import java.util.Optional;
import java.util.UUID;

@Log
public class SpringSecurityAuditorAware implements AuditorAware<UUID> {

    @Override
    public Optional<UUID> getCurrentAuditor() {
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            log.info("Principal: " + authentication.getPrincipal());
            Usuario usuario = (Usuario) authentication.getPrincipal();
            return Optional.ofNullable(usuario.getId());
        }catch(Exception ex){
            log.info("Error de auditoría: " + ex.getMessage());
        }
        return Optional.empty();
    }
}
