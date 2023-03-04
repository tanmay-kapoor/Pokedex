package edu.northeastern.pokedex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    static final Pattern validEmailRegex = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static String getDefaultPassword() {
        return "password";
    }

    public static boolean isValidEmail(String email) {
        Matcher matcher = validEmailRegex.matcher(email);
        return matcher.matches();
    }
}
