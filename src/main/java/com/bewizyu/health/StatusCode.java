package com.bewizyu.health;

public class StatusCode {

    public static boolean convertFromHTTP(int httpCode) {
        if (httpCode == 200)
            return true;
        return false;
    }
}
