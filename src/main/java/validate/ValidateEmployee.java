package validate;

import org.apache.commons.validator.routines.EmailValidator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Walidacja danych o pracowniku
 */
public class ValidateEmployee {

    /**
     * @param REGEX_ZIP_CODE Regex do kodu pocztowego
     * @param REGEX_NUMBER Regex do numeru telefonu
     * @param REGEX_ADDRESS Regex do adresu
     * @param REGEX_PLACE Regex do miejscowości
     * @param REGEX_NAME Regex do imienia
     * @param NUMBER Regex do numeru
     */
    private final static String REGEX_ZIP_CODE = "^[0-9]{2}(?:-[0-9]{3})?$"; //98-145
    private final static String REGEX_NUMBER = "^[0-9]{9}$"; //123 123 123
    private final static String REGEX_ADDRESS = "^\\S.*$"; //ul. Strażacka 9/10
    private final static String REGEX_PLACE = "^[\\p{L}\\p{M}]+([ -][\\p{L}\\p{M}]+)?$"; //Rzeszów // Nowa Sarzyna // Bielsko-Biała
    // Zapasowy regex, jakby coś się zepsuło
    // ^(?!\d)[\p{L}\p{M}]+(?:[-\s][\p{L}\p{M}]+)*$
    private final static String REGEX_NAME = "^[\\p{L}]+$"; //Nie pozwala na spację przed imieniem i nazwiskiem
    //Zapasowy regex
    //[a-zA-ZąćęłńóśźżĄĆĘŁŃÓŚŹŻ]+
    private final static String NUMBER = "-?\\d+(\\.\\d+)?"; //0-9

    /**
     * Sprawdzenie czy imię jest takie jak powinno -> tzn. żeby ktoś Kasia2000 nie wpisał
     *
     * @param name Imię
     * @throws Exception
     */
    public static void goodName(String name) throws Exception {
        if (name.isEmpty()) {
            throw new Exception("Puste pole imienia");
        }

        if (name.length() > 128) {
            throw new Exception("Imię powinno mieć mniej niż 125 znaków");
        }

        Pattern pattern_name = Pattern.compile(REGEX_NAME);
        Matcher matcher_name = pattern_name.matcher(name);

        if (!matcher_name.matches()) {
            throw new Exception("Błędny format imienia");
        }
    }

    /**
     * Sprawdzenie czy nazwisko jest takie jak powinno -> tzn. żeby ktoś Kowalski2019 nie wpisał
     * @param surname Nazwisko
     * @throws Exception
     */
    public static void goodSurname(String surname) throws Exception {
        if (surname.isEmpty()) {
            throw new Exception("Puste pole nazwiska");
        }

        if (surname.length() > 128) {
            throw new Exception("Nazwisko powinno mieć mniej niż 125 znaków");
        }

        Pattern pattern_surname = Pattern.compile(REGEX_NAME);
        Matcher matcher_surname = pattern_surname.matcher(surname);

        if (!matcher_surname.matches()) {
            throw new Exception("Błędny format nazwiska");
        }
    }

    /**
     * Sprawdzenie czy miejscowość jest taka jak powinna
     *
     * @param place Miejscowość
     * @throws Exception
     */
    public static void goodPlace(String place) throws Exception {
        if (place.isEmpty()) {
            throw new Exception("Puste pole miejscowości");
        }

        if (place.length() > 125) {
            throw new Exception("Nazwa miejscowości powinna mieć mniej niż 125 znaków");
        }

        Pattern pattern_place = Pattern.compile(REGEX_PLACE);
        Matcher matcher_place = pattern_place.matcher(place);

        if (!matcher_place.matches()) {
            throw new Exception("Nie można używać spacji w polach");
        }
    }

    /**
     * Sprawdzenie czy adres nie ma spacji na początku linii w adresie
     *
     * @param address Adres
     * @throws Exception
     */
    public static void goodAddress(String address) throws Exception {
        if (address.isEmpty()) {
            throw new Exception("Puste pole adresu");
        }

        if (address.length() > 250) {
            throw new Exception("Adres powinien mieć mniej niż 250 znaków");
        }

        Pattern pattern_address = Pattern.compile(REGEX_ADDRESS);
        Matcher matcher_address = pattern_address.matcher(address);

        if (!matcher_address.matches()) {
            throw new Exception("Nie można używać spacji w polach ADDRESS");
        }
    }

    /**
     * Sprawdzenie czy email jest taki jak powinien
     *
     * @param email Email
     * @throws Exception
     */
    public static void goodEmail(String email) throws Exception {
        if (email.isEmpty()) {
            throw new Exception("Pusty e-mail");
        }

        if (email.length() > 200) {
            throw new Exception("E-mail powinien mieć mniej niż 200 znaków");
        }

        if (!EmailValidator.getInstance().isValid(email)) {
            throw new Exception("Błędny e-mail");
        }
    }

    /**
     * Sprawdzenie czy emaile są takie same
     *
     * @param email Email
     * @param repeatEmail Powtórzony email
     * @throws Exception
     */
    public static void sameEmail(String email, String repeatEmail) throws Exception {
        if (!email.equals(repeatEmail)) {
            throw new Exception("Emaile nie są takie same!");
        }
    }

    /**
     * Sprawdzenie czy 2 hasła są takie same
     *
     * @param password Hasło
     * @param repeatPassword Powtórzone hasło
     * @throws Exception
     */
    public static void samePassword(String password, String repeatPassword) throws Exception {
        if (password.length() > 50) {
            throw new Exception("Hasło powinno mieć mniej niż 50 znaków");
        }
        if (!password.equals(repeatPassword)) {
            throw new Exception("Hasła nie są takie same!");
        }
    }

    /**
     * Sprawdzenie czy numer telefonu jest taki jak powinien
     *
     * @param phoneNumber Numer telefonu
     * @throws Exception
     */
    public static void goodPhoneNumber(String phoneNumber) throws Exception {
        if (phoneNumber.isEmpty()) {
            throw new Exception("Puste pole numeru telefonu");
        }

        Pattern pattern_number = Pattern.compile(REGEX_NUMBER);
        Matcher matcher_number = pattern_number.matcher(phoneNumber);

        if (!matcher_number.matches()) {
            throw new Exception("Błędny numer telefonu");
        }
    }

    /**
     * Sprawdzenie czy kod pocztowy jest taki jak powinien
     *
     * @param zipcode Kod pocztowy
     * @throws Exception
     */
    public static void goodZipCode(String zipcode) throws Exception {
        if (zipcode.isEmpty()) {
            throw new Exception("Puste pole kodu pocztowego");
        }

        Pattern pattern_zipCode = Pattern.compile(REGEX_ZIP_CODE);
        Matcher matcher_zipCode = pattern_zipCode.matcher(zipcode);

        if (!matcher_zipCode.matches()) {
            throw new Exception("Błędny kod pocztowy");
        }
    }

    /**
     * Sprawdzenie czy token jest wygenerowany
     *
     * @param token Token
     * @throws Exception
     */
    public static void goodToken(String token) throws Exception {
        if (token.equals("") || token.isBlank() || token.equals(" ")) {
            throw new Exception("Wygeneruj token");
        }
    }

    /**
     * Sprawdzenie czy grupa jest numeryczna
     *
     * @param strNum Numer grupy
     * @throws Exception
     */
    public static void goodGroup(String strNum) throws Exception {
        if (strNum == null) {
            throw new Exception("Wpisz numer grupy");
        }

        Pattern pattern_number = Pattern.compile(NUMBER);
        Matcher number = pattern_number.matcher(strNum);
        if (!number.matches()) {
            throw new Exception("Numer grupy musi być liczbą");
        }
    }

    /**
     * Sprawdzenie poprawności stanowiska
     *
     * @param position Stanowisko
     * @throws Exception
     */
    public static void goodPosition(int position) throws Exception {
        if (position <= 0 || position > 3) {
            throw new Exception("Wybierz poprawne stanowisko z listy");
        }
    }
}