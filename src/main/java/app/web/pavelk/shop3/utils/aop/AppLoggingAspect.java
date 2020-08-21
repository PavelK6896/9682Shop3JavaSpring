package app.web.pavelk.shop3.utils.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Date;

@Aspect
@Component
public class AppLoggingAspect {
// "execution(modifier-pattern? return-type-pattern declaring-type-pattern? method-name-pattern(args-pattern)
// throws-pattern?)"
// execution([модификатор_метода(public, *)?] [тип_возврата] [класс?] [имя_метода]([аргументы]) [исключения?]


    String[] name = {"log1.txt", "log2.txt"};
    StringBuilder stringBuilder;
    StringBuilder stringBuilder2;

    @PostConstruct
    private void newF() {
        Arrays.stream(name).forEach(f -> {
            try {
                Files.write(
                        Paths.get(f),
                        "new \n".getBytes(),
                        StandardOpenOption.CREATE,
                        StandardOpenOption.TRUNCATE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    @Around("execution(* app.web.pavelk.shop3.controller.rest.*.*(*))")
    public Object methodProfiling(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        stringBuilder2 = new StringBuilder();
        System.out.println("start profiling");
        long begin = System.currentTimeMillis();
        Object out = proceedingJoinPoint.proceed();
        long end = System.currentTimeMillis();
        long duration = end - begin;
        Date date = new Date();
        stringBuilder2.append(date.toString() + " \n");
        stringBuilder2.append("METHOD: " + (MethodSignature) proceedingJoinPoint.getSignature() + " duration: " + duration + " ms");
        System.out.println((MethodSignature) proceedingJoinPoint.getSignature() + " duration: " + duration + " ms");
        stringBuilder2.append("\n\n");
        try {
            Files.write(Paths.get(name[1]), stringBuilder2.toString().getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("end profiling");
        return out;

    }


    @Before("execution( * app.web.pavelk.shop3.controller.*.*.*(*))") // pointcut expression
    public void beforeUserModifyInUserDAOClass(JoinPoint joinPoint) {
        stringBuilder = new StringBuilder();
        Date date = new Date();
        stringBuilder.append(date.toString() + " \n");
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        stringBuilder.append("METHOD: ");
        stringBuilder.append(methodSignature);
        Object[] args = joinPoint.getArgs();
        if (args.length > 0) {
            stringBuilder.append("\nARG: ");
            for (Object o : args) {
                stringBuilder.append(o);
            }
        }
        stringBuilder.append("\nUSER: ");
        stringBuilder.append(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        stringBuilder.append("\n\n");
        try {
            Files.write(Paths.get(name[0]), stringBuilder.toString().getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
