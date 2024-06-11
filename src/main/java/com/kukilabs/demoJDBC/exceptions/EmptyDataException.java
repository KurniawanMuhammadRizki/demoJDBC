package com.kukilabs.demoJDBC.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Setter
@Data
public class EmptyDataException extends RuntimeException  {
    private HttpStatus httpStatus;
    private List<String> errors;
    private Object data;

    public EmptyDataException(String message) {
        this(HttpStatus.BAD_REQUEST, message);
    }

    public EmptyDataException(HttpStatus httpStatus, String message) {
        this(httpStatus, message, Collections.singletonList(message), null);
    }

    public EmptyDataException(HttpStatus httpStatus, String message, List<String> errors, Object data) {
        super(message);
        this.httpStatus = httpStatus;
        this.errors = errors;
        this.data = data;
    }
}
