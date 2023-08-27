package urbanjungletech.hardwareservice.exception.exception;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class StandardErrorException extends RuntimeException{
    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    protected HttpStatus status;
    protected String message;
    protected Map<String, String> metadata;

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
