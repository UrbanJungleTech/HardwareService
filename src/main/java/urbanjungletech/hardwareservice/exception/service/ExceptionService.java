package urbanjungletech.hardwareservice.exception.service;

import urbanjungletech.hardwareservice.exception.exception.NotFoundException;
import urbanjungletech.hardwareservice.exception.exception.SystemException;

public interface ExceptionService {

    NotFoundException createNotFoundException(Class clazz, long id);
    NotFoundException createNotFoundException(Class clazz, String id);

    void throwSystemException(Class<? extends SystemException> exceptionType, Throwable exceptionCause, Object context);
    void throwSystemException(Class<? extends SystemException> exceptionType);
}
