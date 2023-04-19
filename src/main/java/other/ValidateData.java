package other;

import org.apache.commons.validator.routines.EmailValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateData {

    //Nie pytajcie co to robi, uwierzcie w Jasną Stronę Javy
    final static String regex_zipCode = "^[0-9]{2}(?:-[0-9]{3})?$"; //98-145
    final static String regex_number = "^[0-9]{9}$"; //123 123 123
    final static String regex_address = "^\\S.*$"; //ul. Strażakca 9/10
    final static String regex_place = "^[\\p{L}\\p{M}]+([ -][\\p{L}\\p{M}]+)?$"; //Rzeszów // Nowa Sarzyna // Bielsko-Biała
    // Zapasowy regex, jakby coś się zepsuło
    // ^(?!\d)[\p{L}\p{M}]+(?:[-\s][\p{L}\p{M}]+)*$
    final static String regex_name = "^[\\p{L}]+$"; //
    //Zapasowy regex
    //[a-zA-ZąćęłńóśźżĄĆĘŁŃÓŚŹŻ]+


    //Sprawdzenie czy imię jest takie jak powinno -> tzn. żeby ktoś Kasia2000 nie wpisał
    public static void goodName(String name) throws Exception {
        if (name.length() > 128) {
            throw new Exception("Imię powinno mieć mniej niż 125 znaków");
        }

        Pattern pattern_name = Pattern.compile(regex_name);
        Matcher matcher_name = pattern_name.matcher(name);

        if (!matcher_name.matches()) {
            throw new Exception("Błędny format imienia");
        }
    }

    //Sprawdzenie czy nazwisko jest takie jak powinno -> tzn. żeby ktoś Kowalski2019 nie wpisał
    public static void goodSurname(String surname) throws Exception {
        if (surname.length() > 128) {
            throw new Exception("Nazwisko powinno mieć mniej niż 125 znaków");
        }

        Pattern pattern_surname = Pattern.compile(regex_name);
        Matcher matcher_surname = pattern_surname.matcher(surname);

        if (!matcher_surname.matches()) {
            throw new Exception("Błędny format nazwiska");
        }
    }

    //Sprawdzenie czy miejscowość jest taka jak powinna
    public static void goodPlace(String place) throws Exception {
        if (place.length() > 128) {
            throw new Exception("Nazwa miejscowości powinna mieć mniej niż 125 znaków");
        }

        Pattern pattern_place = Pattern.compile(regex_place);
        Matcher matcher_place = pattern_place.matcher(place);

        if (!matcher_place.matches()) {
            throw new Exception("Nie można używać spacji w polach PLACE");
        }
    }

    //Sprawdzenie czy adres nie ma spacji na początku linii w adresie
    public static void goodAddress(String address) throws Exception {
        if (address.length() > 128) {
            throw new Exception("Adres powinien mieć mniej niż 250 znaków");
        }

        Pattern pattern_address = Pattern.compile(regex_address);
        Matcher matcher_address = pattern_address.matcher(address);

        if (!matcher_address.matches()) {
            throw new Exception("Nie można używać spacji w polach ADDRESS");
        }
    }

    //Sprawdzenie czy email jest taki jak powinien
    public static void goodEmail(String email) throws Exception {
        if (email.length() > 128) {
            throw new Exception("Adres powinien mieć mniej niż 250 znaków");
        }

        if (!EmailValidator.getInstance().isValid(email)) {
            throw new Exception("Błędny e-mail");
        }
    }

    //Sprawdzenie czy 2 hasła są takie same
    public static void samePassword(String password, String repeat_password) throws Exception {
        if (password.length() > 50) {
            throw new Exception("Hasło powinno mieć mniej niż 50 znaków");
        }
        if (!password.equals(repeat_password)) {
            throw new Exception("Hasła nie są takie same!");
        }
    }

    //Sprawdzenie czy numer telefonu jest taki jak powinien
    public static void goodPhoneNumber(String phoneNumber) throws Exception {
        Pattern pattern_number = Pattern.compile(regex_number);
        Matcher matcher_number = pattern_number.matcher(phoneNumber);

        if (!matcher_number.matches()) {
            throw new Exception("Błędny numer telefonu");
        }
    }

    //Sprawdzenie czy kod pocztowy jest taki jak powinien
    public static void goodZipCode(String zipcode) throws Exception {
        Pattern pattern_zipCode = Pattern.compile(regex_zipCode);
        Matcher matcher_zipCode = pattern_zipCode.matcher(zipcode);

        if (!matcher_zipCode.matches()) {
            throw new Exception("Błędny kod pocztowy");
        }
    }


}
