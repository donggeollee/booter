package com.boot.meal.common.util;

import lombok.*;
import lombok.experimental.Accessors;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class Header<T> {

    private LocalDateTime transactionTime;

    private int resultCode;

    private String description;

    private T data;

    private Exception exception;

    public static <T> Header<T> OK(){
        return (Header<T>) Header.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode(200)
                .description("OK")
                .build();
    }

    public static <T> Header<T> OK(T data, int resultCode, String description){
        return (Header<T>) Header.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode(resultCode)
                .description(description)
                .data(data)
                .build();
    }

    public static Header ERROR(int resultCode, String description){
        return (Header) Header.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode(resultCode)
                .description(description)
                .build();
    }

    public static <T> Header ERROR(int resultCode, String description, Exception exception){
        return (Header) Header.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode(resultCode)
                .description(description)
                .exception(exception)
                .build();
    }

    public String toString(){
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
