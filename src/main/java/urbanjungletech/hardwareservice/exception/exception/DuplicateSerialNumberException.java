package urbanjungletech.hardwareservice.exception.exception;

import org.springframework.http.HttpStatus;

public class DuplicateSerialNumberException extends StandardErrorException {
    public DuplicateSerialNumberException(){
        this.httpStatus = HttpStatus.CONFLICT.value();
        this.message = "Duplicate serial number";
    }
}
