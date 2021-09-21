package com.kadia.kblogserber.common;

import java.util.Random;

public class RandomString {
    public static String getCharAndNum(int length) {
        Random random = new Random();
        StringBuilder valSb = new StringBuilder();
        String charStr = "0123456789QWERTYUIOPASDFGHJKLZXCVBNM";
        int charLength = charStr.length();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(charLength);
            valSb.append(charStr.charAt(index));
        }
        return valSb.toString();
    }
}
