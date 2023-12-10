package urbanjungletech.hardwareservice.exception.exception;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class StandardErrorException extends RuntimeException{
    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public Integer getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(Integer httpStatus) {
        this.httpStatus = httpStatus;
    }

    protected Integer httpStatus;
    protected String message;
    protected Map<String, String> metadata;

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
