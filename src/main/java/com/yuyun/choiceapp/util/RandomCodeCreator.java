package com.yuyun.choiceapp.util;

import java.util.Random;

public class RandomCodeCreator {

    public String getRandomCode(int length) {
        String alphaNum = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int alphaNumLength = alphaNum.length();

        Random random = new Random();

        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++){
            code.append(alphaNum.charAt(random.nextInt(alphaNumLength)));
        }

        return code.toString();
    }
}
