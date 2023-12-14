package urbanjungletech.hardwareservice.exception;

import urbanjungletech.hardwareservice.exception.exception.SystemException;

public class DefaultSystemException extends SystemException {
    public DefaultSystemException(Exception cause) {
        super(cause.getMessage());
    }
}
