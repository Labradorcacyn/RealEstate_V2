package salesianos.triana.dam.RealEstate.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Setter @Getter
@RequiredArgsConstructor @NoArgsConstructor
@Builder
public class ApiError {

    @NonNull
    private HttpStatus estado;

    @JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fecha = LocalDateTime.now();

    @NonNull
    private String mensaje;
}

