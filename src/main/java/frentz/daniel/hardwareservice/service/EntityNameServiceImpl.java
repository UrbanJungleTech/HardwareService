package frentz.daniel.hardwareservice.service;

import frentz.daniel.hardwareservice.config.EntityNameConfiguration;
import frentz.daniel.hardwareservice.exception.NameNotFoundException;
import org.springframework.stereotype.Service;



@Service
public class EntityNameServiceImpl implements EntityNameService{

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
