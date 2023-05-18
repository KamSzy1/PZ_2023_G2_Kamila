package other;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotSame;

public class GenerateTokenTest {

    @Test
    @DisplayName("2 różne tokeny")
    public void tokenNotTheSameTest(){
        GenerateToken generateToken = new GenerateToken();
        String token = generateToken.generateToken();
        String otherToken = generateToken.generateToken();
        System.out.println(token + ", " + otherToken);
        assertNotSame(token, otherToken);
    }
}
