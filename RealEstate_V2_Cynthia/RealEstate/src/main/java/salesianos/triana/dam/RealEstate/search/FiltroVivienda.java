package salesianos.triana.dam.RealEstate.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import salesianos.triana.dam.RealEstate.model.Tipo;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor

public class FiltroVivienda {

    private String key;
    private String operation;
    private Object value;
}