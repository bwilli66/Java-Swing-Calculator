package Logic;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by BradWilliams on 11/10/16.
 */
public abstract class Validation {

    public static boolean isValidPassword(String s){


        //^ start of expression
        //(?=.*[0-9])     a digit must occur at least once
        //(?=.*[A-Za-z])  a lower case or upper case letter must occur at least once
        //.{8,}           must be at least 8 characters
        //$ end of expression
        Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[A-Za-z]).{8,}$");

        Matcher matcher = pattern.matcher(s);

        if(matcher.find())
            return true;
        return false;

        // ----------- OLD ALGORITHM
        /*
        isValidPassword Algorithm:
        Condition to be met:
        String has to contain a combination of at least one letter and one digit

        1. If < 2 (one character or less) return false
        2. Convert to char array
        3. Loop through char array
        4. Check for digit
        5. Check for non-digit
        6. If a letter AND a digit is not found return false
         */

//        if(s.length() < 2)
//            return false;
//
//        boolean hasDigit = false; //if a digit is found
//        boolean hasNotDigit = false; //if a character that's not a digit is found
//
//        for(char c : s.toCharArray()){
//            if(Character.isDigit(c))
//                hasDigit = true;
//            else
//                hasNotDigit = true;
//        }
//
//        if (hasDigit && hasNotDigit)
//            return true;
//
//        return false;
    }

    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    public static boolean isValidUsername(String username){
        if(JavaDatabaseAPI.getAccountInfo(username)[0] == null)
            return false;
        else
            return true;
    }

    public static boolean doesEmailExist(String username, String email){

        //Bug fixed the easier way, just avoid passing null into .equals function
        if(JavaDatabaseAPI.getAccountInfo(username)[1] == null)
            return false;

        // If the email entered does correspond to the username tried to log in with
        if((JavaDatabaseAPI.getAccountInfo(username)[1]).equals(email))
            return true;

        return false;
    }
}
