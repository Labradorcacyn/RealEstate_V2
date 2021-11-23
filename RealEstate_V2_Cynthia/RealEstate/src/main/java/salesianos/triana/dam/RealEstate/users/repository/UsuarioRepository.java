package salesianos.triana.dam.RealEstate.users.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import salesianos.triana.dam.RealEstate.users.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("select i from Usuario i")
    public List<Usuario> allInteresados();

    @EntityGraph(value = "grafo-interesado", type = EntityGraph.EntityGraphType.FETCH)
    List<Usuario> findByIdNotNull();

    Optional<Usuario> findFirstByEmail(String email);

}