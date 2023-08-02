package com.prophiuslimited.ProphiusLimitedAssessment.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

@Component

public class AppUtils {

    private final Random RANDOM = new SecureRandom();
    private final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    public String generateUserId(int length){
        return generateRandomString(length);
    }

    public String generateCommentId(int length){
        return generateRandomString(length);
    }

    public String generatePostId(int length){
        return generateRandomString(length);
    }

    public String getFormattedNumber(final String number){
        String trimmedNumber = number.trim();
        String formattedNumber = null;
        if(trimmedNumber.startsWith("0"))
            formattedNumber = "+234" + trimmedNumber.substring(1);
        else if(trimmedNumber.startsWith("234"))
            formattedNumber = "+" + number;
        else if (!number.startsWith("+")
                && Integer.parseInt(String.valueOf(number.charAt(0))) > 0) {
            formattedNumber = "+234" + number;
        }
        return formattedNumber;
    }

    public String getString( Object o){
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(o);
        } catch (Exception ex){
            ex.printStackTrace();
            return null;

        }
    }
    private String generateRandomString(int length){
        StringBuilder returnValue = new StringBuilder(length);
        for (int i=0; i<length; i++){
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return new String(returnValue);
    }

    public Long generateOTP(){
        Random rand = new Random();
        Long number = (long) rand.nextInt(999999);

        return number;
    }

}
