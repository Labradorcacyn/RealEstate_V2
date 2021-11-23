package salesianos.triana.dam.RealEstate.dto.interesaDto;

import org.springframework.stereotype.Component;
import salesianos.triana.dam.RealEstate.model.Interesa;

import java.time.LocalDateTime;

@Component
public class InteresaDtoConverter {
    public Interesa InteresaDtoToInteresa (InteresaDto i){
        return Interesa.builder()
                .mensaje(i.getMensaje())
                .createdDate(LocalDateTime.now())
                .build();
    }

    public InteresaDto InteresaToInteresaDto(Interesa i){
        return InteresaDto.builder()
                .mensaje(i.getMensaje())
                .createDate(LocalDateTime.now())
                .build();
    }
}