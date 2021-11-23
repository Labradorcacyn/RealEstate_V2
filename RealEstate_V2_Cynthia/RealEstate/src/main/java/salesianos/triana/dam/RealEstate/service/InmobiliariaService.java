package salesianos.triana.dam.RealEstate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import salesianos.triana.dam.RealEstate.model.Inmobiliaria;
import salesianos.triana.dam.RealEstate.repository.InmobiliariaRepository;
import salesianos.triana.dam.RealEstate.service.base.BaseService;


@Service
@RequiredArgsConstructor
public class InmobiliariaService extends BaseService<Inmobiliaria, Long, InmobiliariaRepository> {

    private final ViviendaService viviendaService;

    @Override
    public void delete(Inmobiliaria inmobiliaria) {

        if (!inmobiliaria.getViviendas().isEmpty()) {
            inmobiliaria.getViviendas().stream().map(v -> {
                v.setInmobiliaria(null);
                return v;
            }).forEach(viviendaService::save);

        }

        super.delete(inmobiliaria);

    }
}
