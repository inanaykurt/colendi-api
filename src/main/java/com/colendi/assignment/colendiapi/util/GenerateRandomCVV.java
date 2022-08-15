package com.colendi.assignment.colendiapi.util;

public class GenerateRandomCVV {

    public static int generateRandomNum() {
        int min = 100;
        int max = 999;

        return (int)Math.floor(Math.random()*(max-min+1)+min);
    }
}
