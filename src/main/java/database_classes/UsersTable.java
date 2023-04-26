package database_classes;

public class UsersTable {
    // Tutaj doda się kod do tabeli z pracownikami
    // Klasa została utworzona testowo, nazwę można podmienić, ale proszę o:
    // Zasada: jedna tabela == jedna klasa -> ale to jeszcze przedyskutuję z Patrykiem
    // Jeśli clasa odnosi się do tabeli w bazie danych to proszę o dodawanie "Table" na końcu
    // Piszemy: "WorkerTable", nie "Worker_Table", ani "worker_table" !

    //Ta tabela ogarnia: administatorów, managerów i workerów
    private static int idUser;
    private static String name;
    private static String surname;
    private static String address;
    private static String zip;
    private static String place;
    private static int phoneNumber;
    private static String email;
    private static String password;
    private static int positionId;
    private int login;
    private static int groups;

    public static int getIdUser() {
        return idUser;
    }

    public static void setIdUser(int idUser) {
        UsersTable.idUser = idUser;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        UsersTable.name = name;
    }

    public static String getSurname() {
        return surname;
    }

    public static void setSurname(String surname) {
        UsersTable.surname = surname;
    }

    public static String getAddress() {
        return address;
    }

    public static void setAddress(String address) {
        UsersTable.address = address;
    }

    public static String getZip() {
        return zip;
    }

    public static void setZip(String zip) {
        UsersTable.zip = zip;
    }

    public static String getPlace() {
        return place;
    }

    public static void setPlace(String place) {
        UsersTable.place = place;
    }

    public static int getPhoneNumber() {
        return phoneNumber;
    }

    public static void setPhoneNumber(int phoneNumber) {
        UsersTable.phoneNumber = phoneNumber;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        UsersTable.email = email;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        UsersTable.password = password;
    }

    public static int getPositionId() {
        return positionId;

    }

    public static void setPositionId(int positionId) {
        UsersTable.positionId = positionId;
    }

    public int getLogin() {
        return login;
    }

    public void setLogin(int login) {
        this.login = login;
    }

    public static int getGroups() {
        return groups;
    }

    public static void setGroups(int groups) {
        UsersTable.groups = groups;
    }

}
