package urbanjungletech.hardwareservice.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
//@Component
public class LoggingAspect {

    private Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    //Pointcut for all public methods under urbanjungletech.hardwareservice
    @Pointcut("execution(public * urbanjungletech.hardwareservice.service..*(..))")
    public void publicMethod() {
    }

    //Pointcut for all public methods under urbanjungletech.hardwareservice.addition
    @Pointcut("execution(public * urbanjungletech.hardwareservice.addition..*(..))")
    public void publicMethodAddition() {
    }


    /**
     * For each method, log the entry and exit points
     * As well as the time taken.
     * @param joinPoint
     */
    @Around("publicMethod() || publicMethodAddition()")
    public Object beforeMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {
        return joinPoint.proceed();
//        String className = joinPoint.getTarget().getClass().getInterfaces()[0].getSimpleName();
//        this.logger.debug("Entering method {} for class {}", joinPoint.getSignature().getName(), className);
//        long startTime = System.currentTimeMillis();
//        Object result = null;
//        try {
//            result = joinPoint.proceed();
//        } catch (Throwable throwable) {
//            this.logger.error("Exception thrown in method {} for class {}", joinPoint.getSignature().getName(), className);
//            this.logger.error(throwable.getMessage());
//        }
//        long endTime = System.currentTimeMillis();
//        this.logger.debug("Exiting method {} for class {} after {} ms", joinPoint.getSignature().getName(), className, endTime - startTime);
//        return result;
    }
}
