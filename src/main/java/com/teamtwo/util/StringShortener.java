package com.teamtwo.util;

public class StringShortener {

    public static String shortenString (String s, int maxChar) {
        if (s.length() > maxChar)
            return s.substring(0, maxChar) + "...";
        return s;
    }
}
