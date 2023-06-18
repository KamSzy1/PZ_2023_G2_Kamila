package other;

import java.util.Random;

/**
 * Klasa do generowania tokenu
 */
public class GenerateToken {

    /**
     * Metoda do generowania tokenu
     *
     * @return Wygenerowany token
     */
    public String generateToken() {
        String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuwxyz1234567890!@#$%^&*";
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        while (stringBuilder.length() < 6) {
            int i = (int) (random.nextFloat() * CHARS.length());
            stringBuilder.append(CHARS.charAt(i));
        }
        return stringBuilder.toString();
    }
}
