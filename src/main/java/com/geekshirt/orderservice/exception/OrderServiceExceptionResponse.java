package com.geekshirt.orderservice.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor //Constructor con todos los atributos
public class OrderServiceExceptionResponse {
    //Esto lo utilizamos para personalizar el formato de las excepciones que devolvemos al usuario

    private String message;
    private String details;
    private HttpStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
}
