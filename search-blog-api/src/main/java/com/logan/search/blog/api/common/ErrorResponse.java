package com.logan.search.blog.api.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public final class ErrorResponse {
    private String code;
    private String message;
    public static ErrorResponse of(String code, String message) {
        return new ErrorResponse(code, message);
    }
}
