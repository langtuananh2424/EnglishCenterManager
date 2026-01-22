package com.englishcentermanager.backend.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private String status;  // "SUCCESS" hoặc "ERROR"
    private String message; // Thông báo cho người dùng
    private T data;         // Dữ liệu chính (User, List<Student>, Token...)

    // Constructor tiện lợi cho trường hợp thành công
    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>("SUCCESS", message, data);
    }

    // Constructor tiện lợi cho trường hợp thất bại
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>("ERROR", message, null);
    }
}