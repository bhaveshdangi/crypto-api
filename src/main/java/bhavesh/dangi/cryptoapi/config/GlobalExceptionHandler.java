package bhavesh.dangi.cryptoapi.config;

import bhavesh.dangi.cryptoapi.exception.DuplicateDataException;
import bhavesh.dangi.cryptoapi.exception.InvalidRequestException;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    static class Error {

        private int error;

        private String errorMessage;

        private final Long timestamp = System.currentTimeMillis();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Error handleException(Exception ex) {

        log.error("Internal server error : {}", ex.getMessage(), ex);
        return Error.builder()
                .error(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .errorMessage(ex.getMessage())
                .build();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<Error> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        log.debug("Bad request : {}", ex.getMessage());
        return ex.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> Error.builder()
                        .error(HttpStatus.BAD_REQUEST.value())
                        .errorMessage(fieldError.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateDataException.class)
    public Error handleDuplicateDataException(DuplicateDataException ex) {

        log.debug("DuplicateDataException : {}", ex.getMessage());
        return Error.builder()
                .error(HttpStatus.CONFLICT.value())
                .errorMessage(ex.getMessage())
                .build();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidRequestException.class)
    public Error handleInvalidRequestException(InvalidRequestException ex) {

        log.debug("InvalidRequestException : {}", ex.getMessage());
        return Error.builder()
                .error(HttpStatus.BAD_REQUEST.value())
                .errorMessage(ex.getMessage())
                .build();
    }
}
