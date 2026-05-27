package fa.training.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Validator {
    public static boolean isBlank(String str) {
        return str.trim().isEmpty();
    }

    public static boolean isValidPhoneNumber(String phone) {
        String normalizePhone = phone.replaceAll("\\s+", "");
        return normalizePhone.trim().matches(Constants.PHONE_NUMBER_REGEX);
    }

    public static boolean isValidOrderNumber(String number) {
        String orderNumber = number.replaceAll("\\s+", "");
        if(orderNumber.length() != 10) return false;
        for(int i = 0; i < orderNumber.length(); i++) {
            if(!Character.isDigit(orderNumber.charAt(i))) return false;
        }
        return true;
    }

    public static boolean isValidDate(String date, DateTimeFormatter formatter) {
        try{
            LocalDate.parse(date, formatter);
        }catch (DateTimeParseException e){
            return false;
        }
        return true;
    }
}
