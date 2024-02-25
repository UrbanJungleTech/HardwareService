package urbanjungletech.hardwareservice.exception.service;

/**
 * Service to get a human-readable name for an entity, intended for use in exceptions.
 */
public interface EntityNameService {
    /**
     * Get the name of the entity for the given class
     * @param clazz The class
     * @return The name of the entity as defined in the configuration
     */
    String getName(Class clazz);
}
