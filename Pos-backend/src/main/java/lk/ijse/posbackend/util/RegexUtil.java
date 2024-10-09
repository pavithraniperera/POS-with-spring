package lk.ijse.posbackend.util;

import java.util.regex.Pattern;

public class RegexUtil {
    // Define the regex pattern for customer ID (e.g., C001)
    private static final String CUSTOMER_ID_REGEX = "^C\\d{3}$";
    private static final String ITEM_ID_REGEX = "^ITEM-[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$";


    // Utility method to validate customer ID
    public static boolean isValidCustomerId(String customerId) {
        return Pattern.matches(CUSTOMER_ID_REGEX, customerId);
    }
    public static boolean isValidItemId(String itemId) {
        return Pattern.matches(ITEM_ID_REGEX, itemId);
    }

}
