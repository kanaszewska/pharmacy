package com.debska.pharmacy.exceptions;

import jakarta.validation.ConstraintViolation;
import lombok.Data;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@ResponseBody
public class ControllerAdviceException {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleNotFoundException(RuntimeException exception) {
        return String.format("%s", exception.getMessage());
    }

    @ExceptionHandler(ElementExistsExceptions.class)
    public String handleElementExistsException(RuntimeException exception) {
        return String.format("%s", exception.getMessage());
    }


    //todo add test for it (use `mockmvc`)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleException(MethodArgumentNotValidException ex) {

        ErrorDto dto = new ErrorDto(HttpStatus.BAD_REQUEST, "Validation error");

        dto.setDetailedMessages(ex.getBindingResult().getAllErrors().stream().map(err -> err.unwrap(ConstraintViolation.class)).map(err -> String.format("'%s' %s", err.getPropertyPath(), err.getMessage())).collect(Collectors.toList()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dto);

    }

    @Data
    private class ErrorDto {

        private final int status;
        private final String error;
        private final String message;
        private List<String> detailedMessages;

        public ErrorDto(HttpStatus httpStatus, String message) {
            status = httpStatus.value();
            error = httpStatus.getReasonPhrase();
            this.message = message;
        }

    }

}


