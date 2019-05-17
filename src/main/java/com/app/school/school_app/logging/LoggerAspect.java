package com.app.school.school_app.logging;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Component
@Data
@NoArgsConstructor
public class LoggerAspect {

    @Autowired
    private LoggingRepository loggingRepository;

    private final Logger logger = LoggerFactory.getLogger(LoggerAspect.class);

    @AfterReturning("within(com.app.school.school_app..*)")
    public void log(JoinPoint joinPoint) {
        String method = joinPoint.getSignature().toShortString();
        String message = Arrays.toString(joinPoint.getArgs());
        LocalDateTime dateTime = LocalDateTime.now();

        logger.info("><Executed " + method + " ><");
        logger.info("With arguments:>< " + message + " ><");
        logger.info("When:>< " + dateTime.toString() + " ><");

        LoggingEntity log = new LoggingEntity();
        log.setMethod(method);
        log.setMessage(message);
        log.setDateTime(dateTime);

        loggingRepository.save(log);
    }

}
