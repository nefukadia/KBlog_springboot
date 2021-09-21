package com.kadia.kblogserber.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Checker {
    public static boolean existNull(Object[] list){
        for(Object obj : list){
            if(obj == null)
                return true;
        }
        return false;
    }

    public static boolean existNullOrEmpty(Object[] list){
        for(Object obj : list){
            if(obj == null || obj.equals(""))
                return true;
        }
        return false;
    }

    public static boolean isEmail(String email){
        if (null==email || "".equals(email)){
            return false;
        }
        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p = Pattern.compile(regEx1);
        Matcher m = p.matcher(email);
        return m.matches();
    }
}
