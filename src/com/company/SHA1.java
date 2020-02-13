package com.company;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class SHA1 {

    public static String getHash (String input)
    {
        String hashMessage = "";
        try
        {
            //Called md to be with sha-1 algorithm
            MessageDigest md = MessageDigest.getInstance("SHA-1");

            //Calculate message digest of the input string and return as byte
            byte[] hash = md.digest(input.getBytes());

            //Convert byte array into signum representation
            BigInteger number = new BigInteger(1, hash);

            //Convert message digest into hex value
            hashMessage = number.toString(16);

            //Add preceding 0s to make it 32 bit
            while(hashMessage.length() < 32)
                hashMessage = "0".concat(hashMessage);

            //Return HashMessage
            return hashMessage;
        }
        catch(NoSuchAlgorithmException ex)
        {
            throw new RuntimeException(ex);
        }
    }
}
