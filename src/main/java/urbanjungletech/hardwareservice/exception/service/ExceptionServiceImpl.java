package urbanjungletech.hardwareservice.exception.service;

import urbanjungletech.hardwareservice.exception.exception.NotFoundException;
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

    @Override
    public NotFoundException createNotFoundException(Class clazz, String id) {
        String name = this.entityNameService.getName(clazz);
        NotFoundException result = new NotFoundException(name, id);
        return result;
    }
}
