package com.epam.itemcatalog.web.controller;

import com.epam.itemcatalog.model.dto.ErrorDto;
import com.epam.itemcatalog.service.exception.AlreadyExistsException;
import com.epam.itemcatalog.service.exception.NotFoundException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ExceptionHandlingController {

    private static final String INVALID_URL = "Invalid url: ";
    private static final String INVALID_DATA = "Invalid data";
    private static final String INVALID_PARAMETER = "Invalid parameter: '%s' for %s";
    private static final String INVALID_FORMAT = "Invalid data format";

    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleAlreadyExistsException(AlreadyExistsException ex) {
        return new ErrorDto(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), ex.getLocalizedMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto handleNotFoundException(NotFoundException ex) {
        return new ErrorDto(HttpStatus.NOT_FOUND.value(), ex.getMessage(), ex.getLocalizedMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto handleNoHandlerFoundException(NoHandlerFoundException ex) {
        return new ErrorDto(HttpStatus.NOT_FOUND.value(), INVALID_URL + ex.getRequestURL(), ex.getLocalizedMessage());
    }

    @ExceptionHandler(InvalidFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleInvalidFormatException() {
        return new ErrorDto(HttpStatus.BAD_REQUEST.value(), INVALID_DATA, INVALID_FORMAT);
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleUnsupportedOperationException(UnsupportedOperationException ex) {
        return new ErrorDto(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), ex.getLocalizedMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        return new ErrorDto(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), ex.getLocalizedMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        return new ErrorDto(HttpStatus.BAD_REQUEST.value(), INVALID_DATA,
                String.format(INVALID_PARAMETER, ex.getValue(), ex.getName()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<ErrorDto> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<ErrorDto> errorsDto = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(x -> errorsDto.add(new ErrorDto(HttpStatus.BAD_REQUEST.value(),
                INVALID_DATA, x.getDefaultMessage())));
        return errorsDto;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleHttpMessageNotReadableException() {
        return new ErrorDto(HttpStatus.BAD_REQUEST.value(), INVALID_DATA, INVALID_FORMAT);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public ErrorDto handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex) {
        return new ErrorDto(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), INVALID_DATA, ex.getLocalizedMessage());
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleHttpMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException ex) {
        return new ErrorDto(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), ex.getLocalizedMessage());
    }
}
