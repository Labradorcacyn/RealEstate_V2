package salesianos.triana.dam.RealEstate.users.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import salesianos.triana.dam.RealEstate.users.model.Usuario;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    @Query("select i from Usuario i")
    public List<Usuario> allInteresados();

    Optional<Usuario> findFirstByEmail(String email);

}