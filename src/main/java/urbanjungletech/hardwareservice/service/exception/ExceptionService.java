package urbanjungletech.hardwareservice.service.exception;

import urbanjungletech.hardwareservice.exception.NotFoundException;

public interface ExceptionService {
    NotFoundException createNotFoundException(Class clazz, long id);
    NotFoundException createNotFoundException(Class clazz, String id);
}
