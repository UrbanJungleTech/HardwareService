package frentz.daniel.hardwareservice.service;

/**
 * Used for logging objects in a serialized format.
 */
public interface ObjectLoggerService {
    /**
     * Logs an object to a serialized format at info log level.
     * @param message The message to be logged before the serialized object.
     * @param entity The entity to be serialized.
     */
    void logInfo(String message, Object entity);
}
