package utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import model.User;
import org.bouncycastle.util.encoders.Hex;

public final class Hashing {

    // TODO: You should add a salt and make this secure FIXED

    public String md5WithSalt(Long createdTime, String string) {

//     Nedenfor sætter jeg mit salt og password sammen. Salten består at et unix timestamp og en streng jeg selv har lavet
        string = createdTime + string + "Hej123";

//      Nedenfor hashes password samt mit salt
        return md5(string);
    }

    public static String md5(String rawString) {
        try {

            // We load the hashing algoritm we wish to use.
            MessageDigest md = MessageDigest.getInstance("MD5");

            // We convert to byte array
            byte[] byteArray = md.digest(rawString.getBytes());

            // Initialize a string buffer
            StringBuffer sb = new StringBuffer();

            // Run through byteArray one element at a time and append the value to our stringBuffer
            for (int i = 0; i < byteArray.length; ++i) {
                sb.append(Integer.toHexString((byteArray[i] & 0xFF) | 0x100).substring(1, 3));
            }

            //Convert back to a single string and return
            return sb.toString();

        } catch (java.security.NoSuchAlgorithmException e) {

            //If somethings breaks
            System.out.println("Could not hash string");
        }

        return null;
    }

    // TODO: You should add a salt and make this secure FIXED

    public String sha5WithSalt(int id,String email,String password) {

    // Nedenfor sætter jeg mit salt og password sammen. Salten består at et unix timestamp og en streng jeg selv har lavet
        String string = id + email +password + "Hej123";

    // Nedenfor hashes password samt mit salt
        return sha(string);
    }


    public static String sha(String rawString) {
        try {
            // We load the hashing algoritm we wish to use.
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // We convert to byte array
            byte[] hash = digest.digest(rawString.getBytes(StandardCharsets.UTF_8));

            // We create the hashed string
            String sha256hex = new String(Hex.encode(hash));

            // And return the string
            return sha256hex;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return rawString;
    }
}