package urbanjungletech.hardwareservice.exception;

public class RpcMethodNotFoundException extends RuntimeException {
    public RpcMethodNotFoundException(String method) {
        super("Method " + method + " not found");
    }
}
