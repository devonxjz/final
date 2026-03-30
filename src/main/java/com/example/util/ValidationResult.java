package com.example.util;

/**
 * Lớp Wrapper chứa kết quả kiểm tra tính hợp lệ của dữ liệu đầu vào.
 * @param <T> Kiểu dữ liệu trả về nếu hợp lệ (vd: String, Integer, Double)
 */
public class ValidationResult<T> {
    private final boolean valid;
    private final T value;
    private final String errorMessage;

    private ValidationResult(boolean valid, T value, String errorMessage) {
        this.valid = valid;
        this.value = value;
        this.errorMessage = errorMessage;
    }

    public static <T> ValidationResult<T> success(T value) {
        return new ValidationResult<>(true, value, null);
    }

    public static <T> ValidationResult<T> fail(String message) {
        return new ValidationResult<>(false, null, message);
    }

    public boolean isValid() {
        return valid;
    }

    public T getValue() {
        if (!valid) {
            throw new IllegalStateException("Cannot get value from failed validation. Error: " + errorMessage);
        }
        return value;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
