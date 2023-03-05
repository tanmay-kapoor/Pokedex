package edu.northeastern.pokedex;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Utils {
    private static final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private static final DatabaseReference messageRef = mDatabase.child("room1").child("messages");

    private static final Pattern validEmailRegex = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static String getDefaultPassword() {
        return "password";
    }

    public static boolean isValidEmail(String email) {
        Matcher matcher = validEmailRegex.matcher(email);
        return matcher.matches();
    }
}
