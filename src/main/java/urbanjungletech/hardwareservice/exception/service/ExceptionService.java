package urbanjungletech.hardwareservice.exception.service;

import urbanjungletech.hardwareservice.exception.exception.NotFoundException;

public interface ExceptionService {
    NotFoundException createNotFoundException(Class clazz, long id);
    NotFoundException createNotFoundException(Class clazz, String id);
}
