package salesianos.triana.dam.RealEstate.model;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.lang.Nullable;
import salesianos.triana.dam.RealEstate.users.model.Usuario;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Vivienda implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo, descripcion, avatar, latLng, direccion, codPostal, poblacion, provincia;

    @Enumerated (EnumType.STRING)
    private Tipo tipo;

    private Boolean tienePiscina, tieneAscensor, tieneGaraje;

    private Integer numHabitaciones, numBanos;

    private Float precio;

    private Double metrosCuadrados;


    @ManyToOne
    @Nullable
    private Inmobiliaria inmobiliaria;

    @ManyToOne
    private Usuario usuario;

    @Builder.Default
    @OneToMany(mappedBy = "vivienda")
    private List<Interesa> intereses = new ArrayList<>();

    //@CreatedBy
    //private UUID createBy;


    // HELPERS INMOBILIARIA

    public void addToInmobiliaria(Inmobiliaria i){
        this.inmobiliaria=i;
        i.getViviendas().add(this);
    }

    public void removeToInmobiliaria(Inmobiliaria i){
        this.inmobiliaria=null;
        i.getViviendas().remove(this);
    }



    // HELPERS PROPIETARIO

    public void addToPropietario(Usuario p){
        this.usuario=p;
        p.getViviendas().add(this);
    }

    public void removeToPropietario(Usuario p){
        this.usuario=null;
        p.getViviendas().remove(this);
    }

    //HELPER INMOBILIARIA/PROPIETARIO

    public void editPropietarioInmobiliaria(Inmobiliaria i, Usuario p){

        this.usuario = p;
        this.inmobiliaria = i;
        p.getViviendas().add(this);
        i.getViviendas().add(this);
    }

}
