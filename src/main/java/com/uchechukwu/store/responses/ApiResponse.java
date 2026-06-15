package com.uchechukwu.store.responses;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;



@Getter
@RequiredArgsConstructor
public class ApiResponse<T> {

    private final boolean success;
    private final  String message;
    private final T data;
    private final String timestamp = LocalDateTime.now()
        .format(DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm:ss a"));

    
}
