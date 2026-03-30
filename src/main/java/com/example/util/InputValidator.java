package com.example.util;

import java.util.regex.Pattern;

/**
 * Lớp tiện ích thực hiện chuẩn hóa và kiểm tra dữ liệu đầu vào tập trung.
 * Hỗ trợ các kiểu dữ liệu phổ biến và đảm bảo tính nhất quán của format (Tên, SĐT, Tiền).
 */
public final class InputValidator {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    // Số điện thoại VN: 10 chữ số, có thê bắt đầu bằng +84 hoặc 0
    private static final Pattern PHONE_PATTERN = Pattern.compile("^(0|\\+84)[3|5|7|8|9][0-9]{8}$");
    // Điện thoại bàn hoặc đầu số 11 số cũ
    private static final Pattern OLD_PHONE_PATTERN = Pattern.compile("^(0|\\+84)[0-9]{9,10}$");

    private InputValidator() {
        // Private constructor for utility class
    }

    /**
     * Làm sạch ký hiệu đơn vị vật lý, hỗ trợ format số kiểu Việt (ví dụ: 1.5, 1,5)
     */
    public static String cleanNumeric(String input) {
        if (input == null || input.trim().isEmpty()) return "0";
        String cleaned = input.trim().toLowerCase();
        
        // Loại bỏ các đơn vị phổ biến
        cleaned = cleaned.replaceAll("(gb|tb|kg|inch|ghz|mhz|mm|cm|g)$", "").trim();
        
        long commaCount = cleaned.chars().filter(c -> c == ',').count();
        long dotCount = cleaned.chars().filter(c -> c == '.').count();

        // 1.500.000,00 -> 1500000.00
        // 1,500,000.00 -> 1500000.00
        
        if (commaCount > 0 && dotCount > 0) {
            int lastComma = cleaned.lastIndexOf(',');
            int lastDot = cleaned.lastIndexOf('.');
            if (lastComma > lastDot) {
                // ....,..  -> format VN (VD: 1.500.000,50)
                cleaned = cleaned.replace(".", "").replace(",", ".");
            } else {
                // ....,..  -> format QTe (VD: 1,500,000.50)
                cleaned = cleaned.replace(",", "");
            }
        } else if (commaCount > 1) {
            // VD: 20,000,000
            cleaned = cleaned.replace(",", "");
        } else if (commaCount == 1) {
            int commaIndex = cleaned.indexOf(',');
            String afterComma = cleaned.substring(commaIndex + 1);
            if (afterComma.length() <= 2) {
                // Thập phân: 1,5 / 1,50
                cleaned = cleaned.replace(",", ".");
            } else {
                // Phân cách nghìn: 20,000
                cleaned = cleaned.replace(",", "");
            }
        } else if (dotCount > 1) {
             // VD: 20.000.000
             cleaned = cleaned.replace(".", "");
        }
        
        cleaned = cleaned.replaceAll("[^0-9.]", "");
        return cleaned.isEmpty() ? "0" : cleaned;
    }

    public static ValidationResult<Integer> parseIntSafe(String input, String fieldName) {
        try {
            if (input == null || input.trim().isEmpty()) {
                return ValidationResult.fail("Vui lòng nhập " + fieldName + "!");
            }
            String cleaned = cleanNumeric(input);
            double val = Double.parseDouble(cleaned);
            return ValidationResult.success((int) val);
        } catch (NumberFormatException e) {
            return ValidationResult.fail(fieldName + " không hợp lệ! Vui lòng chỉ nhập số.");
        }
    }

    public static ValidationResult<Double> parseDoubleSafe(String input, String fieldName) {
         try {
            if (input == null || input.trim().isEmpty()) {
                return ValidationResult.fail("Vui lòng nhập " + fieldName + "!");
            }
            String cleaned = cleanNumeric(input);
            return ValidationResult.success(Double.parseDouble(cleaned));
        } catch (NumberFormatException e) {
            return ValidationResult.fail(fieldName + " không hợp lệ! Vui lòng chỉ nhập số thực (ví dụ 1.5).");
        }
    }

    public static ValidationResult<Float> parseFloatSafe(String input, String fieldName) {
         try {
            if (input == null || input.trim().isEmpty()) {
                return ValidationResult.fail("Vui lòng nhập " + fieldName + "!");
            }
            String cleaned = cleanNumeric(input);
            return ValidationResult.success(Float.parseFloat(cleaned));
        } catch (NumberFormatException e) {
            return ValidationResult.fail(fieldName + " không hợp lệ! Vui lòng chỉ nhập số thực (ví dụ 1.5).");
        }
    }

    public static ValidationResult<Double> parseCurrency(String input, String fieldName) {
        try {
            if (input == null || input.trim().isEmpty()) {
                return ValidationResult.fail("Vui lòng nhập " + fieldName + "!");
            }
            
            String cleaned = input.toLowerCase().replace("đ", "").replace("vnd", "").trim();
            // Nếu có cả . và , thì dựa vào vị trí cuối
            // Ví dụ: 1.500.000,00 
            int lastComma = cleaned.lastIndexOf(',');
            int lastDot = cleaned.lastIndexOf('.');
            if (lastComma != -1 && lastDot != -1) {
                 if (lastComma > lastDot) {
                      cleaned = cleaned.replace(".", "").replace(",", ".");
                 } else {
                      cleaned = cleaned.replace(",", "");
                 }
            } else if (lastComma != -1 && lastComma != cleaned.indexOf(',')) {
                 // Nhiều dấu phẩy: 1,500,000
                 cleaned = cleaned.replace(",", "");
            } else if (lastDot != -1 && lastDot != cleaned.indexOf('.')) {
                 // Nhiều dấu chấm: 1.500.000
                 cleaned = cleaned.replace(".", "");
            } else if (lastComma != -1 && cleaned.length() - lastComma >= 4) {
                 // 1,500
                 cleaned = cleaned.replace(",", "");
            } else if (lastDot != -1 && cleaned.length() - lastDot >= 4) {
                 // 1.500
                 cleaned = cleaned.replace(".", "");
            } else if (lastComma != -1) {
                // 1500,5 -> 1500.5
                cleaned = cleaned.replace(",", ".");
            }

            cleaned = cleaned.replaceAll("[^0-9.]", "");
            if (cleaned.isEmpty()) return ValidationResult.fail(fieldName + " không chứa ký tự số!");
            double amount = Double.parseDouble(cleaned);
            if (amount < 0) return ValidationResult.fail(fieldName + " không được âm!");
            
            return ValidationResult.success(amount);
        } catch (NumberFormatException e) {
            return ValidationResult.fail(fieldName + " không đúng định dạng!");
        }
    }

    public static ValidationResult<String> normalizeName(String name, String fieldName) {
        if (name == null || name.trim().isEmpty()) {
            return ValidationResult.fail("Vui lòng nhập " + fieldName + "!");
        }
        name = name.trim().replaceAll("\\s+", " "); // Loại bỏ khoảng trắng kép
        
        // Title Case
        String[] words = name.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String w : words) {
            if (w.length() > 0) {
                sb.append(Character.toUpperCase(w.charAt(0)));
                if (w.length() > 1) {
                    sb.append(w.substring(1).toLowerCase());
                }
                sb.append(" ");
            }
        }
        String result = sb.toString().trim();
        return checkLength(result, 255, fieldName);
    }
    
    public static ValidationResult<String> normalizeAddress(String address, String fieldName) {
        if (address == null || address.trim().isEmpty()) {
            return ValidationResult.success(""); // Địa chỉ có thể ko bắt buộc rỗng
        }
        return checkLength(address.trim().replaceAll("\\s+", " "), 255, fieldName);
    }

    public static ValidationResult<String> validatePhone(String phone, boolean required) {
        if (phone == null || phone.trim().isEmpty()) {
            return required ? ValidationResult.fail("Vui lòng nhập số điện thoại!") : ValidationResult.success("");
        }
        phone = phone.trim().replace("-", "").replace(" ", "").replace(".", "");
        if (phone.startsWith("+84")) {
            phone = "0" + phone.substring(3);
        }
        
        if (!phone.matches("^[0-9]+$")) {
            return ValidationResult.fail("Số điện thoại chỉ được chứa các chữ số!");
        }

        if (PHONE_PATTERN.matcher(phone).matches()) {
            return ValidationResult.success(phone);
        } else if (OLD_PHONE_PATTERN.matcher(phone).matches()) {
             return ValidationResult.fail("Vui lòng nhập định dạng SĐT cập nhật (10 số). Đầu số cũ đã được chuyển đổi.");
        }
        return ValidationResult.fail("Số điện thoại không hợp lệ! SĐT phải chứa 10 chữ số bắt đầu bằng 0.");
    }

    public static ValidationResult<String> validateEmail(String email, boolean required) {
        if (email == null || email.trim().isEmpty()) {
            return required ? ValidationResult.fail("Vui lòng nhập email!") : ValidationResult.success("");
        }
        email = email.trim().toLowerCase();
        if (EMAIL_PATTERN.matcher(email).matches()) {
            return checkLength(email, 100, "Email"); // Email max len ở DB là 100
        }
        return ValidationResult.fail("Email không đúng định dạng! Ví dụ: abc@gmail.com");
    }

    public static ValidationResult<String> checkLength(String input, int maxLength, String fieldName) {
        if (input == null) return ValidationResult.success("");
        if (input.length() > maxLength) {
            return ValidationResult.fail(fieldName + " không được vượt quá " + maxLength + " ký tự!");
        }
        return ValidationResult.success(input);
    }
    
    public static ValidationResult<String> validateRequiredString(String input, String fieldName, int maxLength) {
         if (input == null || input.trim().isEmpty()) return ValidationResult.fail("Vui lòng nhập " + fieldName + "!");
         return checkLength(input.trim(), maxLength, fieldName);
    }
}
