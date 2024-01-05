package urbanjungletech.hardwareservice.exception.exception;

public class SystemException extends RuntimeException {
    protected String message;
    protected Throwable cause;
    protected Object context;
    public SystemException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }

    public void setCause(Throwable cause) {
        this.cause = cause;
    }

    public void setContext(Object context) {
        this.context = context;
    }

    public Object getContext() {
        return context;
    }
}
