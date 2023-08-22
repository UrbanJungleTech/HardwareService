package urbanjungletech.hardwareservice.exception;

import org.springframework.http.HttpStatus;

public class DuplicateSerialNumberException extends StandardErrorException {
    public DuplicateSerialNumberException(){
        this.status = HttpStatus.CONFLICT;
        this.message = "Duplicate serial number";
    }
}
