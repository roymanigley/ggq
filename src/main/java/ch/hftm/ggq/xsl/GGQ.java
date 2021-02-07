package ch.hftm.ggq.xsl;

import java.util.Optional;

public class GGQ {

    private static final String EMPTY_STRING = "";

    public static String firstToUpperCase(String str) {
        return Optional.ofNullable(str)
                .filter(s -> s.length() > 0)
                .map(s -> s.substring(0,1).toUpperCase() + s.substring(1))
                .orElse(EMPTY_STRING);
    }

    public static String firstToLowerCase(String str) {
        return Optional.ofNullable(str)
                .filter(s -> s.length() > 0)
                .map(s -> s.substring(0,1).toLowerCase() + s.substring(1))
                .orElse(EMPTY_STRING);
    }

    public static String toUpperCase(String str) {
        return Optional.ofNullable(str)
                .map(String::toUpperCase)
                .orElse(EMPTY_STRING);
    }

    public static String toLowerCase(String str) {
        return Optional.ofNullable(str)
                .map(String::toLowerCase)
                .orElse(EMPTY_STRING);
    }
}
