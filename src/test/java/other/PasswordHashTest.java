package other;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static other.PasswordHash.hashedPassword;

public class PasswordHashTest {

    //Test hashowania hasła
    @Test
    void testHash(){
        final String password = "abc123";
        final String exp_hash = "e99a18c428cb38d5f260853678922e03";
        final String hash_password = hashedPassword(password);
        assertEquals(hash_password, exp_hash);
    }
}
