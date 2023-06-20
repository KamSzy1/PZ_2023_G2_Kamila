package database_classes;

import javafx.scene.control.Button;

/**
 * Klasa reprezentująca tabelę "Users" z bazy danych
 */
public class UsersTable {

    /**
     * Statyczne ID użytkownika
     */
    private static int idLoginUser;
    /**
     * Statyczny numer ID do edycji
     */
    private static int idEditUser;
    /**
     * Statyczny numer grupy
     */
    private static int groupNumber;
    /**
     * Statyczne imię
     */
    private static String loginName;
    /**
     * Statyczne nazwisko
     */
    private static String loginSurname;
    /**
     * ID
     */
    private int idUser;
    /**
     * Imię
     */
    private String name;
    /**
     * Nazwisko
     */
    private String surname;
    /**
     * Adres
     */
    private String address;
    /**
     * Kod pocztowy
     */
    private String zip;
    /**
     * Miejscowość
     */
    private String place;
    /**
     * Numer telefonu
     */
    private int phoneNumber;
    /**
     * Email
     */
    private String email;
    /**
     * Hasło
     */
    private String password;
    /**
     * ID Stanowiska
     */
    private int positionId;
    /**
     * Login
     */
    private int login;
    /**
     * Grupa
     */
    private int groups;
    /**
     * Nazwa stanowiska
     */
    private String namePosition;
    /**
     * Token
     */
    private String token;
    /**
     * Przycisk do edycji pracownika
     */
    private Button editEmployeeButton;

    /**
     * Konstruktor pusty
     */
    public UsersTable() {
    }

    /**
     * Konstruktor klasy
     *
     * @param idUser      ID użytkownika
     * @param name        imie
     * @param surname     nazwisko
     * @param address     adres
     * @param zip         kod pocztowy
     * @param place       miejsce zamieszkania
     * @param phoneNumber numer telefonu
     * @param positionId  ID stanowiska
     * @param token       token
     * @param groups      grupa
     */
    public UsersTable(int idUser, String name, String surname, String address, String zip, String place, int phoneNumber, int positionId, String token, int groups) {
        this.idUser = idUser;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.zip = zip;
        this.place = place;
        this.phoneNumber = phoneNumber;
        this.positionId = positionId;
        this.token = token;
        this.groups = groups;
        this.editEmployeeButton = new Button();
    }

    /**
     * Metoda statyczna zwracająca ID edytowanego użytkownika
     *
     * @return ID edytowanego użytkownika
     */
    public static int getIdEditUser() {
        return idEditUser;
    }

    /**
     * Metoda statyczna ustalająca ID edytowanego użytkownika
     *
     * @param idEditUser ustalone ID edytowanego użytkownika
     */
    public static void setIdEditUser(int idEditUser) {
        UsersTable.idEditUser = idEditUser;
    }

    /**
     * Metoda statyczna zwracająca ID zalogowanego użytkownika
     *
     * @return ID zalogowanego użytkownika
     */
    public static int getIdLoginUser() {
        return idLoginUser;
    }

    /**
     * Metoda zwracająca ID użytkownika
     *
     * @return ID użytkownika
     */
    public int getIdUser() {
        return idUser;
    }

    /**
     * Metoda zwracająca imie użytkownika
     *
     * @return imie
     */
    public String getName() {
        return name;
    }

    /**
     * Metoda zwracająca nazwisko użytkownika
     *
     * @return nazwisko
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Metoda zwracająca adres zamieszkania
     *
     * @return adres zamieszkania
     */
    public String getAddress() {
        return address;
    }

    /**
     * Metoda zwracająca kod pocztowy
     *
     * @return kod pocztowy
     */
    public String getZip() {
        return zip;
    }

    /**
     * Metoda zwracająca miejsce zamieszkania
     *
     * @return miejsce zamieszkania
     */
    public String getPlace() {
        return place;
    }

    /**
     * Metoda zwracająca numer telefonu
     *
     * @return numer telefonu
     */
    public int getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Metoda zwracająca adres mailowy
     *
     * @return adres mailowy
     */
    public String getEmail() {
        return email;
    }

    /**
     * Metoda zwracająca hasło
     *
     * @return hasło
     */
    public String getPassword() {
        return password;
    }

    /**
     * Metoda zwracająca ID stanowiska pracownika
     *
     * @return ID stanowiska pracownika
     */
    public int getPositionId() {
        return positionId;
    }

    /**
     * Metoda zwracająca login uzytkownika
     *
     * @return login
     */
    public int getLogin() {
        return login;
    }

    /**
     * Metoda zwracająca grupe użytkownika
     *
     * @return grupa użytkownika
     */
    public int getGroups() {
        return groups;
    }

    /**
     * Metoda statyczna zwracająca imie zalogowanego użytkownika
     *
     * @return imie zalogowanego użytkownika
     */
    public static String getLoginName() {
        return loginName;
    }

    /**
     * Metoda statyczna zwracająca nazwisko zalogowanego użytkownika
     *
     * @return nazwisko zalogowanego użytkownika
     */
    public static String getLoginSurname() {
        return loginSurname;
    }

    /**
     * Metoda zwracająca nazwę stanowiska użytkownika
     *
     * @return nazwa stanowiska
     */
    public String getNamePosition() {
        return namePosition;
    }

    /**
     * Metoda zwracająca token
     *
     * @return token
     */
    public String getToken() {
        return token;
    }

    /**
     * Metoda zwracjąca przycisk edycji pracownika
     *
     * @return przycisk edycji pracownika
     */
    public Button getEditEmployeeButton() {
        return editEmployeeButton;
    }

    /**
     * Metoda statyczna ustawiająca ID zalogowanego użytkownika
     *
     * @param idLoginUser ID zalogowanego użytkownika
     */
    public static void setIdLoginUser(int idLoginUser) {
        UsersTable.idLoginUser = idLoginUser;
    }

    /**
     * Metoda ustawiająca ID użytkownika
     *
     * @param idUser ID użytkownika
     */
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    /**
     * Metoda ustawiająca imie użytkownika
     *
     * @param name ustalone imie użytkownika
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Metoda ustawiająca nazwisko użytkownika
     *
     * @param surname ustalone nazwisko użytkownika
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Metoda ustawiająca adres zamieszkania
     *
     * @param address ustalony adres zamieszkania
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Metoda ustawiająca kod pocztowy
     *
     * @param zip ustalony kod pocztowy
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * Metoda ustawiająca miejsce zamieszkania
     *
     * @param place ustalone miejsce zamieszkania
     */
    public void setPlace(String place) {
        this.place = place;
    }

    /**
     * Metoda ustawiająca numer telefonu
     *
     * @param phoneNumber Numer telefonu
     */
    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Metoda ustawiająca adres mailowy
     *
     * @param email ustalony adres mailowy
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Metoda ustawiająca hasło
     *
     * @param password ustalone hasło
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Metoda ustawiająca ID stanowiska
     *
     * @param positionId ustalone ID stanowiska
     */
    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    /**
     * Metoda ustawiająca login użytkownika
     *
     * @param login ustalony login użytkownika
     */
    public void setLogin(int login) {
        this.login = login;
    }

    /**
     * Metoda ustawiająca grupę
     *
     * @param groups ustalona grupa
     */
    public void setGroups(int groups) {
        this.groups = groups;
    }

    /**
     * Metoda statyczna ustawiająca imię zalogowanego użytkownika
     *
     * @param loginName ustalone imię zalogowanego użytkownika
     */
    public static void setLoginName(String loginName) {
        UsersTable.loginName = loginName;
    }

    /**
     * Metoda statyczna ustawiająca nazwisko zalogowanego użytkownika
     *
     * @param loginSurname ustalone nazwisko zalogowanego użytkownika
     */
    public static void setLoginSurname(String loginSurname) {
        UsersTable.loginSurname = loginSurname;
    }

    /**
     * Metoda ustawiająca nazwę stanowiska użytkownika
     *
     * @param namePosition ustalona nazwa stanowiska
     */
    public void setNamePosition(String namePosition) {
        this.namePosition = namePosition;
    }

    /**
     * Metoda ustawiająca token
     *
     * @param token ustalony token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Metoda tworządza przycisk edycji pracownika
     *
     * @param editEmployeeButton przycisk edycji pracownika
     */
    public void setEditEmployeeButton(Button editEmployeeButton) {
        this.editEmployeeButton = editEmployeeButton;
    }

    /**
     * Metoda zwracająca numer grupy
     *
     * @return numer grupy
     */
    public static int getGroupNumber() {
        return groupNumber;
    }

    /**
     * Metoda ustawiająca numer grupy
     *
     * @param groupNumber ustalony numer grupy
     */
    public static void setGroupNumber(int groupNumber) {
        UsersTable.groupNumber = groupNumber;
    }

    /**
     * Metoda zwracająca obiekt jako ciąg znaków
     *
     * @return ciąg znaków
     */
    @Override
    public String toString() {
        return name + " " + surname;
    }
}

