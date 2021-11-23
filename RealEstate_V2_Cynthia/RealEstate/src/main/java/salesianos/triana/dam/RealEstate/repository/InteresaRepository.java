package salesianos.triana.dam.RealEstate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import salesianos.triana.dam.RealEstate.model.Interesa;
import salesianos.triana.dam.RealEstate.model.InteresaPK;

import java.util.List;

public interface InteresaRepository extends JpaRepository<Interesa, InteresaPK> {

    @Query("select i from Usuario i where vivienda_id=:iden")
    public List<Interesa> allInteresaDeUnaVivienda (@Param("iden")Long iden);
}