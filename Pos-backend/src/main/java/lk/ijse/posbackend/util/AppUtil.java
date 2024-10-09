package lk.ijse.posbackend.util;

import java.util.UUID;

public class AppUtil {
    public static String generateItemId(){
        return "ITEM-"+ UUID.randomUUID();
    }
}
