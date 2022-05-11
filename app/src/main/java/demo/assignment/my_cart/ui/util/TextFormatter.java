package demo.assignment.my_cart.ui.util;

import android.widget.EditText;

public class TextFormatter {

    // check if the given edit text field value is null or empty
    public static boolean isNullOrEmpty(EditText et) {
        return et.getText() == null || et.getText().toString().trim().isEmpty();
    }

    // check if the given string value is null or empty
    public static boolean isNullOrEmpty(String text) {
        return text == null || text.trim().isEmpty();
    }

    // get the trimmed string value of a given edit text field
    public static String getTrimmedText(EditText et) {
        return et.getText().toString().trim();
    }

    public static String capitalizeFirstLetter(String text) {
        StringBuilder newText = new StringBuilder();
        String[] words = text.replaceAll(" +", " ").split(" ");
        for (String word : words) {
            newText.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1)).append(" ");
        }

        return newText.toString().trim();
    }
}
