package urbanjungletech.hardwareservice.exception;

public class DeviceNotFoundException extends RuntimeException {
    private String message;
    public DeviceNotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
