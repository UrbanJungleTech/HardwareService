package urbanjungletech.hardwareservice.exception.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.exception.DefaultSystemException;
import urbanjungletech.hardwareservice.exception.exception.DatasourceNotRegisteredException;
import urbanjungletech.hardwareservice.exception.exception.NotFoundException;
import urbanjungletech.hardwareservice.exception.exception.SystemException;

import java.lang.reflect.InvocationTargetException;

@Service
public class ExceptionServiceImpl implements ExceptionService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final EntityNameService entityNameService;

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

    @Override
    public void throwSystemException(Class<? extends SystemException> exceptionType, Throwable exceptionCause, Object context) {
        try {
            this.logger.info("Creating system exception of type {}", exceptionType);
            SystemException result = exceptionType.getConstructor().newInstance();
            result.setCause(exceptionCause);
            result.setContext(context);
            throw result;
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new DefaultSystemException(e);
        }
    }

    @Override
    public void throwSystemException(Class<? extends SystemException> exceptionType) {
        this.throwSystemException(exceptionType, null, null);
    }
}
