package com.blog.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut("execution(* com.blog.web.*.*(..))")
    public void logPointcut() {
    }

    @Before("logPointcut()")
    public void beforePointcut(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String requestURI = request.getRequestURI();
        String s = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        ReqeustLog reqeustLog = new ReqeustLog(request.getRequestURI(), request.getRemoteAddr(), s, joinPoint.getArgs());
        logger.info("Rquest ----- {}",reqeustLog);
    }

    @After("logPointcut()")
    public void afterPointcut() {
        logger.info("-------afterPointcut ------ {}");
    }

    @AfterReturning(returning = "result", pointcut = "logPointcut()")
    public void doAtfertRturning(Object result) {
        logger.info("Return ------ {}", result);
    }

    private class ReqeustLog {
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public ReqeustLog(String url, String ip, String classMethod,
                          Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "ReqeustLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}

