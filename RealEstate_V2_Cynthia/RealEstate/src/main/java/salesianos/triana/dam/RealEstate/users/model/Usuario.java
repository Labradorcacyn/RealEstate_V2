package salesianos.triana.dam.RealEstate.users.model;
import lombok.*;
import org.hibernate.annotations.NaturalId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import salesianos.triana.dam.RealEstate.model.Inmobiliaria;
import salesianos.triana.dam.RealEstate.model.Interesa;
import salesianos.triana.dam.RealEstate.model.Vivienda;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Builder
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Usuario implements UserDetails {

    private static final long serialVersionUID = 3172893250076562407L;

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private UUID id;

    private String nombre;
    private String apellidos;
    private String direccion;

    @NaturalId
    @Column(unique = true, updatable = false)
    private String email;

    private String telefono;
    private String avatar;
    private String password;

    @CreatedDate
    private LocalDateTime createdAt;

    @Builder.Default
    private LocalDateTime lastPasswordChangeAt = LocalDateTime.now();

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<UserRole> role;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Interesa> intereses=new ArrayList<>();

    @OneToMany (mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Vivienda> viviendas=new ArrayList<>();

    @ManyToOne
    private Inmobiliaria inmobiliaria_prop;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.stream().map(r -> new SimpleGrantedAuthority("ROLE" + r.name())).collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}