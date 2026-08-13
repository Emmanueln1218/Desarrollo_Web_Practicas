package com.motocitas.dto;

public class ApiResponse<T> {
    public boolean error;
    public String message;
    public T data;

    public ApiResponse(boolean error, String message, T data) {
        this.error = error;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(false, "Success", data);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(true, message, null);
    }
}
