package frentz.daniel.hardwareservice.service.implementation;

import frentz.daniel.hardwareservice.exception.NotFoundException;
import frentz.daniel.hardwareservice.service.EntityNameService;
import frentz.daniel.hardwareservice.service.ExceptionService;
import org.springframework.stereotype.Service;

@Service
public class ExceptionServiceImpl implements ExceptionService {

    private EntityNameService entityNameService;

    public ExceptionServiceImpl(EntityNameService entityNameService){
        this.entityNameService = entityNameService;
    }

    @Override
    public NotFoundException createNotFoundException(Class clazz, long id) {
        String name = this.entityNameService.getName(clazz);
        NotFoundException result = new NotFoundException(name, id);
        return result;
    }
}
