package salesianos.triana.dam.RealEstate.users.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import salesianos.triana.dam.RealEstate.users.model.UserRole;
import salesianos.triana.dam.RealEstate.users.model.Usuario;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    Optional<Usuario> findFirstByEmail(String email);

    //@Query("select u from Usuario u where role = $1")
    //public Optional<List<Usuario>> findAllByRole(UserRole userRole);

    // @Query("select u from Usuario u where role = 'PROPIETARIO'")
    //public Optional<List<Usuario>> findAllByRole();

    List<Usuario> findByRole(UserRole role);

    //public Page<Usuario> findAllByRole(UserRole userRole, Pageable pageable);

}