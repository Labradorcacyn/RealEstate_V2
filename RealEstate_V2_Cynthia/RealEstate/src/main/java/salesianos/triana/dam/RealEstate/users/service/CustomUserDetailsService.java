package salesianos.triana.dam.RealEstate.users.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return usuarioService.findByEmail(s)
                .orElseThrow(()-> new UsernameNotFoundException(s + " no encontrado"));
    }


}
