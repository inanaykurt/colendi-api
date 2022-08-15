package com.colendi.assignment.colendiapi.util;

public class GenerateMerchantId {

    public static long generateRandomNum() {
        int min = 10000;
        int max = 99999;

        return (long)Math.floor(Math.random()*(max-min+1)+min);

    }
}
