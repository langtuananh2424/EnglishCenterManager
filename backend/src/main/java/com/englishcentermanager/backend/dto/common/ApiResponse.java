package com.englishcentermanager.backend.dto.common;

public class ApiResponse<T> {
    private String status;  // "SUCCESS" hoặc "ERROR"
    private String message; // Thông báo cho người dùng
    private T data;         // Dữ liệu chính (User, List<Student>, Token...)

    public ApiResponse() {
    }

    public ApiResponse(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    // Constructor tiện lợi cho trường hợp thành công
    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>("SUCCESS", message, data);
    }

    // Constructor tiện lợi cho trường hợp thất bại
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>("ERROR", message, null);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}