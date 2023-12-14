package urbanjungletech.hardwareservice.exception.exception;

public class NameNotFoundException extends RuntimeException{
    public NameNotFoundException(Class clazz){
        super("Name not found for class: " + clazz.getName());
    }
}
