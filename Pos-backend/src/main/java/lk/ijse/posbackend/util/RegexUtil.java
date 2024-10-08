package lk.ijse.posbackend.util;

import java.util.regex.Pattern;

public class RegexUtil {
    // Define the regex pattern for customer ID (e.g., C001)
    private static final String CUSTOMER_ID_REGEX = "^C\\d{3}$";

    // Utility method to validate customer ID
    public static boolean isValidCustomerId(String customerId) {
        return Pattern.matches(CUSTOMER_ID_REGEX, customerId);
    }
}
