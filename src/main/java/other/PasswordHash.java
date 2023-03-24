package other;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHash {

    public String hashedPassword(String password) {
        String hashPassword = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(password.getBytes());
            byte[] bytes = messageDigest.digest();
            StringBuilder stringBuilder = new StringBuilder();
            for (byte aByte : bytes) {
                stringBuilder.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            hashPassword = stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Coś poszło nie tak z hashowaniem hasła");
        }
        return hashPassword;
    }
}
