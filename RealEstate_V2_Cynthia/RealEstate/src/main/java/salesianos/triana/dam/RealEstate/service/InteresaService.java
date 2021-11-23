package salesianos.triana.dam.RealEstate.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import salesianos.triana.dam.RealEstate.model.Interesa;
import salesianos.triana.dam.RealEstate.model.InteresaPK;
import salesianos.triana.dam.RealEstate.repository.InteresaRepository;
import salesianos.triana.dam.RealEstate.service.base.BaseService;

import java.util.List;

@Service
public class InteresaService extends BaseService<Interesa, InteresaPK, InteresaRepository> {

    @Autowired
    InteresaRepository interesaRepository;
    public List<Interesa> allInteresaDeUnaVivienda(Long id){
        return interesaRepository.allInteresaDeUnaVivienda(id);
    }

}
