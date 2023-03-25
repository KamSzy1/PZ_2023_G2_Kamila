package other;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidateDataTest {

    final String surnname = "Mateusz";
    final String surname2 = "Mateusz";

    //Sprawdza czy nie wyrzuci wyjątku
    @Test
    void testName() {
        final String name = "Łukasz";
        Assertions.assertDoesNotThrow(() -> ValidateData.goodName(name));
    }

    //Sprawdza czy wyrzuci wyjątek -> liczby nie są akcpetowane w imieniu w walidacji danych
    @Test
    void testName2() throws Exception {
        final String name = "Łukasz10";
        Assertions.assertThrows(Exception.class, () -> ValidateData.goodName(name));
    }

    //Test dla nazwiska
    @Test
    void testSurname() {
        final String surname = "Nowak";
        Assertions.assertDoesNotThrow(() -> ValidateData.goodSurname(surname));
    }

    //Sprawdza czy wyrzuci wyjątek -> spacja nie jest akceptowana w walidacji danych
    @Test
    void testSurname2() throws Exception {
        final String surname = " Nowak";
        Assertions.assertThrows(Exception.class, () -> ValidateData.goodSurname(surname));
    }

    //Test dla miejscowości -> Polskie znaki powinny być możliwe do wpisania
    @Test
    void testPlace() {
        final String place = "Rzeszów";
        Assertions.assertDoesNotThrow(() -> ValidateData.goodPlace(place));
    }

    //Test dla miejscowości -> wyrzuci wyjątek, ponieważ jest spacja
    @Test
    void testPlace2() throws Exception {
        final String place = " Rzeszów";
        Assertions.assertThrows(Exception.class, () -> ValidateData.goodPlace(place));
    }

    //Test dla adresu
    @Test
    void testAddress() {
        final String address = "ul. Strażacka 12";
        Assertions.assertDoesNotThrow(() -> ValidateData.goodAddress(address));
    }

    //Test dla adresu -> wyrzuci wyjątek, ponieważ jest spacja przez " ul"
    @Test
    void testAddress2() throws Exception {
        final String address = " ul. Strażacka 12";
        Assertions.assertThrows(Exception.class, () -> ValidateData.goodAddress(address));
    }

    //Test dla adresu
    @Test
    void testEmail() {
        final String email = "misiaczek3000@gmail.com";
        Assertions.assertDoesNotThrow(() -> ValidateData.goodAddress(email));
    }

    //Test dla adresu -> wyrzuci wyjątek, ponieważ jest spacja przez " ul"
    @Test
    void testAEmail2() throws Exception {
        final String address = " zlymisiaczek0003@gmail.com";
        Assertions.assertThrows(Exception.class, () -> ValidateData.goodAddress(address));
    }

    //Test dla hasła
    @Test
    void testPassword() {
        final String password = "prosteHaslo";
        final String password2 = "prosteHaslo";
        Assertions.assertDoesNotThrow(() -> ValidateData.samePassword(password, password2));
    }

    //Test dla hasła -> wyjątek
    @Test
    void testPassword2() {
        final String password = "prosteHaslo";
        final String password2 = "nieProsteHaslo";
        Assertions.assertThrows(Exception.class, () -> ValidateData.samePassword(password, password2));
    }


    //Test dla numeru telefonu
    @Test
    void testPhone() {
        final String phone = "123123123";
        Assertions.assertDoesNotThrow(() -> ValidateData.goodPhoneNumber(phone));
    }

    //Test dla numeru telefonu -> wyjątek
    @Test
    void testPhone2() {
        final String phone = "abc123abc";
        Assertions.assertThrows(Exception.class, () -> ValidateData.goodPhoneNumber(phone));
    }

    //Test dla kodu pocztowgo
    @Test
    void testZipCode() {
        final String zipCode = "12-123";
        Assertions.assertDoesNotThrow(() -> ValidateData.goodZipCode(zipCode));
    }

    //Test dla kodu pocztowgo -> wyjątek
    @Test
    void testZipCode2() {
        final String zipCode = "ab-123";
        Assertions.assertThrows(Exception.class, () -> ValidateData.goodZipCode(zipCode));
    }

}
