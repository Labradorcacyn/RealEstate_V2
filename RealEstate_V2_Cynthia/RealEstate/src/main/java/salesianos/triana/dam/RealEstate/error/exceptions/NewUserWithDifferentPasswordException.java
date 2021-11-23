package salesianos.triana.dam.RealEstate.error.exceptions;

public class NewUserWithDifferentPasswordException  extends RuntimeException{

    private static final long serialVersionUID = -7978601526802035152L;

    public NewUserWithDifferentPasswordException() {
        super("Las contraseñas no coinciden");
    }
}
