package com.boot.meal.common.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class Header<T> {

    private LocalDateTime transactionTime;

    private String resultCode;

    private String description;

    private T data;

    private Exception exception;

    public static <T> Header<T> OK(){
        return (Header<T>) Header.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("OK")
                .description("OK")
                .build();
    }

    public static <T> Header<T> OK(T data, String resultCode, String description){
        return (Header<T>) Header.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode(resultCode)
                .description(description)
                .data(data)
                .build();
    }

    public static <T> Header<T> ERROR(String resultCode, String description, Exception exception){
        return (Header<T>) Header.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("ERROR")
                .description(description)
                .exception(exception)
                .build();
    }

}