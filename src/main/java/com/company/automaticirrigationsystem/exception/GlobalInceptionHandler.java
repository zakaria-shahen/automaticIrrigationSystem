package com.company.automaticirrigationsystem.exception;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalInceptionHandler extends ResponseEntityExceptionHandler {

    private final Environment env;

    @ExceptionHandler({
            NotFound.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Object notFound(Exception ex, WebRequest webRequest) {
        return new Object();
    }

    @ExceptionHandler({
            PlotDontHaveSlots.class
    })
    @ResponseStatus(HttpStatus.CONFLICT)
    public Object conflict(Exception ex, WebRequest webRequest) {
        return new Object();
    }


    @ExceptionHandler({
             RabbitMQConnectionError.class
    })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Object internalServerError(Exception ex, WebRequest webRequest) {
        return new Object();
    }

    @ExceptionHandler({
            IdNotEntered.class,
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object badRequest(Exception ex, WebRequest webRequest) {
        return new Object();
    }


    @ExceptionHandler({
            BusinessException.class,
            RuntimeException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void notFoundHandler(Exception ex, WebRequest webRequest) {
        boolean dev =  env.acceptsProfiles(Profiles.of("dev"));
        log.error("Not Found Handler for {}",
                dev ? ex.getMessage()
                : "'must enable dev profile to display exception message'"
        );
    }

}
