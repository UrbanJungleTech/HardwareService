package frentz.daniel.hardwareservice.service;

import frentz.daniel.hardwareservice.exception.NotFoundException;

public interface ExceptionService {
    NotFoundException createNotFoundException(Class clazz, long id);
}
