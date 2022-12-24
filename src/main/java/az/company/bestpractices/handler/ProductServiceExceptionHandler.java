package az.company.bestpractices.handler;

import az.company.bestpractices.dto.Error;
import az.company.bestpractices.dto.response.ApiResponse;
import az.company.bestpractices.exception.ProductNotFoundException;
import az.company.bestpractices.exception.ProductServiceBusinessException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class ProductServiceExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ApiResponse<?> handleProductNotFoundException(ProductNotFoundException exception) {
        ApiResponse<?> serviceResponse = new ApiResponse<>();
        serviceResponse.setStatus("FAILED");
        serviceResponse.setErrors(Collections.singletonList(new Error("", exception.getMessage())));

        return serviceResponse;
    }

    @ExceptionHandler(ProductServiceBusinessException.class)
    public ApiResponse<?> handleServiceException(ProductServiceBusinessException exception) {
        ApiResponse<?> serviceResponse = new ApiResponse<>();
        serviceResponse.setStatus("FAILED");
        serviceResponse.setErrors(Collections.singletonList(new Error("", exception.getMessage())));

        return serviceResponse;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<?> handleValidationException(MethodArgumentNotValidException exception) {
        ApiResponse<?> serviceResponse = new ApiResponse<>();
        List<Error> errors = new ArrayList<>();

        exception.getBindingResult().getFieldErrors()
                .forEach(fieldError -> {
                    Error error = new Error(fieldError.getField(), fieldError.getDefaultMessage());
                    errors.add(error);
                });
        serviceResponse.setStatus("FAILED");
        serviceResponse.setErrors(errors);

        return serviceResponse;
    }

}
