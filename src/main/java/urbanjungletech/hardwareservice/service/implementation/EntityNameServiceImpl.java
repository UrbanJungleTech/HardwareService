package urbanjungletech.hardwareservice.service.implementation;

import urbanjungletech.hardwareservice.config.EntityNameConfiguration;
import urbanjungletech.hardwareservice.exception.NameNotFoundException;
import urbanjungletech.hardwareservice.service.EntityNameService;
import org.springframework.stereotype.Service;



@Service
public class EntityNameServiceImpl implements EntityNameService {

    private EntityNameConfiguration entityNameConfiguration;

    public EntityNameServiceImpl(EntityNameConfiguration entityNameConfiguration){
        this.entityNameConfiguration = entityNameConfiguration;
    }

    @Override
    public String getName(Class clazz) {
        String name = this.entityNameConfiguration.getNames().get(clazz.getName());
        if(name == null){
            throw new NameNotFoundException();
        }
        return name;
    }
}
