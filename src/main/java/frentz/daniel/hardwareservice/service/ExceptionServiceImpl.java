package frentz.daniel.hardwareservice.service;

import frentz.daniel.hardwareservice.exception.StandardNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ExceptionServiceImpl implements ExceptionService {

    private EntityNameService entityNameService;

    public ExceptionServiceImpl(EntityNameService entityNameService){
        this.entityNameService = entityNameService;
    }

    @Override
    public StandardNotFoundException createNotFoundException(Class clazz, long id) {
        String name = this.entityNameService.getName(clazz);
        StandardNotFoundException result = new StandardNotFoundException(name, id);
        return result;
    }
}
