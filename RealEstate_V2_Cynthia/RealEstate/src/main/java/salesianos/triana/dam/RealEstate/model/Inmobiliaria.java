package salesianos.triana.dam.RealEstate.model;

import lombok.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import salesianos.triana.dam.RealEstate.users.model.Usuario;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@CrossOrigin
public class Inmobiliaria implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre, email, telefono;

    @OneToMany(mappedBy = "inmobiliaria")
    @Builder.Default
    private List<Vivienda> viviendas = new ArrayList();

    @OneToMany(mappedBy = "inmobiliaria_prop")
    @Builder.Default
    private List<Usuario> gestores = new ArrayList<>();
}
