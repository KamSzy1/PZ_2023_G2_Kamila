package validate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValidateEmployeeTest {

    //Sprawdza czy nie wyrzuci wyjątku
    @Test
    public void testName() {
        final String name = "Łukasz";
        Assertions.assertDoesNotThrow(() -> ValidateEmployee.goodName(name));
    }

    //Sprawdza czy wyrzuci wyjątek -> liczby nie są akcpetowane w imieniu w walidacji danych
    @Test
    public void testName2() throws Exception {
        final String name = "Łukasz10";
        Assertions.assertThrows(Exception.class, () -> ValidateEmployee.goodName(name));
    }

    //Test dla nazwiska
    @Test
    public void testSurname() {
        final String surname = "Nowak";
        Assertions.assertDoesNotThrow(() -> ValidateEmployee.goodSurname(surname));
    }

    //Sprawdza czy wyrzuci wyjątek -> spacja nie jest akceptowana w walidacji danych
    @Test
    public void testSurname2() throws Exception {
        final String surname = " Nowak";
        Assertions.assertThrows(Exception.class, () -> ValidateEmployee.goodSurname(surname));
    }

    //Test dla miejscowości -> Polskie znaki powinny być możliwe do wpisania
    @Test
    public void testPlace() {
        final String place = "Rzeszów";
        Assertions.assertDoesNotThrow(() -> ValidateEmployee.goodPlace(place));
    }

    //Test dla miejscowości -> wyrzuci wyjątek, ponieważ jest spacja
    @Test
    public void testPlace2() throws Exception {
        final String place = " Rzeszów";
        Assertions.assertThrows(Exception.class, () -> ValidateEmployee.goodPlace(place));
    }

    //Test dla adresu
    @Test
    public void testAddress() {
        final String address = "ul. Strażacka 12";
        Assertions.assertDoesNotThrow(() -> ValidateEmployee.goodAddress(address));
    }

    //Test dla adresu -> wyrzuci wyjątek, ponieważ jest spacja przed " ul"
    @Test
    public void testAddress2() throws Exception {
        final String address = " ul. Strażacka 12";
        Assertions.assertThrows(Exception.class, () -> ValidateEmployee.goodAddress(address));
    }

    //Test dla e-maila
    @Test
    public void testEmail() {
        final String email = "misiaczek3000@gmail.com";
        Assertions.assertDoesNotThrow(() -> ValidateEmployee.goodEmail(email));
    }

    //Test dla e-maila -> wyrzuci wyjątek, ponieważ jest spacja przed mailem
    @Test
    public void testAEmail2() throws Exception {
        final String email = " zlymisiaczek0003@gmail.com";
        Assertions.assertThrows(Exception.class, () -> ValidateEmployee.goodEmail(email));
    }

    @Test
    public void testSameEmail() throws Exception {
        final String email = "takisammisiaczek3000@gmail.com";
        final String sameEmail = "takisammisiaczek3000@gmail.com";
        Assertions.assertDoesNotThrow(() -> ValidateEmployee.sameEmail(email, sameEmail));
    }


    //Test dla hasła
    @Test
    public void testSamePassword() {
        final String password = "prosteHaslo";
        final String password2 = "prosteHaslo";
        Assertions.assertDoesNotThrow(() -> ValidateEmployee.samePassword(password, password2));
    }

    //Test dla hasła -> wyjątek
    @Test
    public void testNotSamePassword2() {
        final String password = "prosteHaslo";
        final String password2 = "nieProsteHaslo";
        Assertions.assertThrows(Exception.class, () -> ValidateEmployee.samePassword(password, password2));
    }


    //Test dla numeru telefonu
    @Test
    public void testPhone() {
        final String phone = "123123123";
        Assertions.assertDoesNotThrow(() -> ValidateEmployee.goodPhoneNumber(phone));
    }

    //Test dla numeru telefonu -> wyjątek
    @Test
    public void testPhone2() {
        final String phone = "abc123abc";
        Assertions.assertThrows(Exception.class, () -> ValidateEmployee.goodPhoneNumber(phone));
    }

    //Test dla kodu pocztowego
    @Test
    public void testZipCode() {
        final String zipCode = "12-123";
        Assertions.assertDoesNotThrow(() -> ValidateEmployee.goodZipCode(zipCode));
    }

    //Test dla kodu pocztowego -> wyjątek
    @Test
    public void testZipCode2() {
        final String zipCode = "ab-123";
        Assertions.assertThrows(Exception.class, () -> ValidateEmployee.goodZipCode(zipCode));
    }

    @Test
    public void testToken(){
        final String token = "";
        Assertions.assertThrows(Exception.class, () -> ValidateEmployee.goodToken(token));
    }

    @Test
    public void testGoodGroup(){
        String number = "2";
        assertDoesNotThrow(() -> ValidateEmployee.goodGroup(number));
    }

    // NAPRAWIC
//    @Test
////    public void testGoodPosition(){
////        int number = 2;
////        assertDoesNotThrow(() -> ValidateEmployee.goodPosition(number));
////    }
}
