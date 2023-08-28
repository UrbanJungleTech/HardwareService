package urbanjungletech.hardwareservice.exception.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends StandardErrorException{

    public NotFoundException(){

    }

    public NotFoundException(String entityName, String id){
        this.status = HttpStatus.NOT_FOUND;
        this.message = entityName + " not found with id of " + id;
    }

    public NotFoundException(String entityName, long id){
        this(entityName, String.valueOf(id));
    }
}