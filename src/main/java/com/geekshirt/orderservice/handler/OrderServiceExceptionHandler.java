package com.geekshirt.orderservice.handler;

import com.geekshirt.orderservice.exception.AccountNotFoundException;
import com.geekshirt.orderservice.exception.IncorrectOrderRequestException;
import com.geekshirt.orderservice.exception.OrderServiceExceptionResponse;
import com.geekshirt.orderservice.exception.PaymentNotAcceptedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice //Puedo especificar que controlador quiero que escuche
@RestController
public class OrderServiceExceptionHandler extends ResponseEntityExceptionHandler {

    //Controlador encargado de captar una excepcion en los controladores y devolverla al cliente
    //Esto lo utilizamos para personalizar el formato de las excepciones que devolvemos al usuario

    @ExceptionHandler(Exception.class) //Delimita que excepciones quiero captar
    public ResponseEntity<Object> handleAllExceptions(Exception exception, WebRequest request){
        //WebRequest = Interceptor de las excepciones
        OrderServiceExceptionResponse response = new OrderServiceExceptionResponse(exception.getMessage(),request.getDescription(false), HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now());
        return new ResponseEntity<>(response,response.getStatus());
    }

    @ExceptionHandler(IncorrectOrderRequestException.class)
    public ResponseEntity<Object> handleIncorrectRequest(Exception exception, WebRequest request){
        OrderServiceExceptionResponse response = new OrderServiceExceptionResponse(exception.getMessage(),request.getDescription(false), HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return new ResponseEntity<>(response,response.getStatus());
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFound(Exception exception, WebRequest request){
        OrderServiceExceptionResponse response = new OrderServiceExceptionResponse(exception.getMessage(),request.getDescription(false), HttpStatus.NOT_FOUND, LocalDateTime.now());
        return new ResponseEntity<>(response,response.getStatus());
    }

    @ExceptionHandler(PaymentNotAcceptedException.class)
    public ResponseEntity<Object> handlePaymentNotAcceptedResourceNotFound(PaymentNotAcceptedException exception, WebRequest request) {
        OrderServiceExceptionResponse response = new OrderServiceExceptionResponse(exception.getMessage(), request.getDescription(false), HttpStatus.NOT_FOUND, LocalDateTime.now());
        return new ResponseEntity<>(response, response.getStatus());
    }


}
