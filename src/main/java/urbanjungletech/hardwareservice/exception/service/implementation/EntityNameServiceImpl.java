package urbanjungletech.hardwareservice.exception.service.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.config.EntityNameConfiguration;
import urbanjungletech.hardwareservice.exception.exception.EntityNameConfigurationNotSetException;
import urbanjungletech.hardwareservice.exception.exception.NameNotFoundException;
import urbanjungletech.hardwareservice.exception.service.EntityNameService;

import java.util.Optional;


@Service
public class EntityNameServiceImpl implements EntityNameService {

    /**
     * The configuration object that contains the mapping of class names to entity names.
     * This should be set in application.yml
     */
    private final EntityNameConfiguration entityNameConfiguration;

    /**
     * Constructor
     * @param entityNameConfiguration The configuration object that contains the mapping of class names to entity names.
     */
    public EntityNameServiceImpl(EntityNameConfiguration entityNameConfiguration){
        this.entityNameConfiguration = entityNameConfiguration;
    }

    /**
     * Get the name of the entity for the given class
     * @param clazz The class
     * @return The name of the entity as defined in the configuration
     * @throws EntityNameConfigurationNotSetException If the configuration is not set
     * @throws NameNotFoundException If the name is not found in the configuration
     */
    @Override
    public String getName(Class clazz) {
        String name = Optional.ofNullable(this.entityNameConfiguration).orElseThrow(() -> new EntityNameConfigurationNotSetException())
        .getNames().get(clazz.getName());
        if(name == null){
            throw new NameNotFoundException(clazz);
        }
        return name;
    }
}
