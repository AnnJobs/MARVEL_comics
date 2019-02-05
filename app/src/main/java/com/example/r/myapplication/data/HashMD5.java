package com.example.r.myapplication.data;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class HashMD5 {

    static String hash(String string) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(string.getBytes());
            byte[] bytes = messageDigest.digest();
            BigInteger bigInteger =  new  BigInteger(1,bytes);
            String md5hex = bigInteger.toString(16);

            while (md5hex.length()<32){
                md5hex = "0".concat(md5hex);
            }

            return md5hex;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
