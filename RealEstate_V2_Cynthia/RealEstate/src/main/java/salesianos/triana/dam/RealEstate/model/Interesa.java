package salesianos.triana.dam.RealEstate.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import salesianos.triana.dam.RealEstate.users.model.Usuario;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor @AllArgsConstructor @Builder
@Getter @Setter
@EntityListeners(AuditingEntityListener.class)
public class Interesa implements Serializable {

    @Builder.Default
    @EmbeddedId
    private InteresaPK id = new InteresaPK();

    @ManyToOne
    @MapsId("vivienda_id")
    @JoinColumn(name = "vivienda_id", foreignKey = @ForeignKey(name = "FK_INTERESA_VIVIENDA"))
    private Vivienda vivienda;

    @ManyToOne
    @MapsId("usuario_id")
    @JoinColumn(name = "usuario_id",  foreignKey = @ForeignKey(name = "FK_INTERESA_USUARIO"))
    private Usuario usuario;

    @CreatedDate
    private LocalDateTime createdDate;
    @Lob
    private String mensaje;

    //HELPERS

    public void removeInteresa(Vivienda v, Usuario i) {
        v.getIntereses().remove(this);
        this.vivienda = null;
        i.getIntereses().remove(this);
        this.usuario = null;
    }

    public void addInteresado(Usuario i, Vivienda v) {
        this.usuario = i;
        this.vivienda = v;
        i.getIntereses().add(this);
        v.getIntereses().add(this);
    }
}