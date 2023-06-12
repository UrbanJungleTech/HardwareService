package frentz.daniel.hardwareservice.service;

import frentz.daniel.hardwareservice.exception.StandardNotFoundException;

public interface ExceptionService {
    StandardNotFoundException createNotFoundException(Class clazz, long id);
}
