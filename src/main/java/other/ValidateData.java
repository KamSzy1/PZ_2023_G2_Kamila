package other;

import org.apache.commons.validator.routines.EmailValidator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateData {
    final static String regex_zipCode = "^[0-9]{2}(?:-[0-9]{3})?$";
    final static String regex_number = "^[0-9]{9}$";
    final static String regex_noSpace = "^[^\\s]+(\\s+[^\\s]+)*$";
    final static String regex_name = "[a-zA-ZąćęłńóśźżĄĆĘŁŃÓŚŹŻ]+";


    //Sprawdzenie czy imię jest takie jak powinno -> tzn. żeby ktoś Kasia2000 nie wpisał
    public static void goodName(String name) throws Exception {
        Pattern pattern_name = Pattern.compile(regex_name);
        Matcher matcher_name = pattern_name.matcher(name);

        if(!matcher_name.matches()){
            throw new Exception("Błędny format imienia");
        }
    }

    //Sprawdzenie czy nazwisko jest takie jak powinno -> tzn. żeby ktoś Kowalski2019 nie wpisał
    public static void goodSurname(String surname) throws Exception {
        Pattern pattern_surname = Pattern.compile(regex_name);
        Matcher matcher_surname = pattern_surname.matcher(surname);

        if(!matcher_surname.matches()){
            throw new Exception("Błędny format nazwiska");
        }
    }

    //Sprawdzenie czy nazwisko jest takie jak powinno -> tzn. żeby ktoś Kowalski2019 nie wpisał
    public static void goodPlace(String place) throws Exception {
        Pattern pattern_place = Pattern.compile(regex_name);
        Matcher matcher_place = pattern_place.matcher(place);

        if(!matcher_place.matches()){
            throw new Exception("Nie można używać spacji w polach");
        }
    }

    //Sprawdzenie czy adres nie ma spacji na początku lini w adresie
    public static void goodAddress(String address) throws Exception {
        Pattern pattern_address = Pattern.compile(regex_noSpace);
        Matcher matcher_address = pattern_address.matcher(address);

        if(!matcher_address.matches()){
            throw new Exception("Nie można używać spacji w polach");
        }
    }

    //Sprawdzenie czy email jest taki jak powinien
    public static void goodEmail(String email) throws Exception {
        if(!EmailValidator.getInstance().isValid(email)){
            throw new Exception("Błędny e-mail");
        }
    }

    //Sprawdzenie czy 2 hasła są takie same
    public static void samePassword(String password, String repeat_password) throws Exception {
        if(!password.equals(repeat_password)){
            throw new Exception("Hasła nie są takie same!");
        }
    }

    //Sprawdzenie czy numer telefonu jest taki jak powinien
    public static void goodPhoneNumber(String phoneNumber) throws Exception {
        Pattern pattern_number = Pattern.compile(regex_number);
        Matcher matcher_number = pattern_number.matcher(phoneNumber);

        if(!matcher_number.matches()){
            throw new Exception("Błędny numer telefonu");
        }
    }

    //Sprawdzenie czy kod pocztowy jest taki jak powinien
    public static void goodZipCode(String zipcode) throws Exception {
        Pattern pattern_zipCode = Pattern.compile(regex_zipCode);
        Matcher matcher_zipCode = pattern_zipCode.matcher(zipcode);

        if(!matcher_zipCode.matches()){
            throw new Exception("Błędny kod pocztowy");
        }
    }


}
