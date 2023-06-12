package frentz.daniel.hardwareservice.exception;

import org.springframework.http.HttpStatus;

public class StandardNotFoundException extends StandardErrorException{
    public StandardNotFoundException(String entityName, String id){
        this.status = HttpStatus.NOT_FOUND;
        this.message = entityName + " not found with id of " + id;
    }

    public StandardNotFoundException(String entityName, long id){
        this(entityName, String.valueOf(id));
    }
}
