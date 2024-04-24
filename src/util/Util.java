package util;

public class Util {
    public static int lastID(String input) {
        return Integer.parseInt(String.valueOf(input.charAt(input.length() - 1)));
    }
}
