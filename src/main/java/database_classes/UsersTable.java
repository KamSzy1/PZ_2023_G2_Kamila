package database_classes;

public class UsersTable {
    // Tutaj doda się kod do tabeli z pracownikami
    // Klasa została utworzona testowo, nazwę można podmienić, ale proszę o:
    // Zasada: jedna tabela == jedna klasa -> ale to jeszcze przedyskutuję z Patrykiem
    // Jeśli clasa odnosi się do tabeli w bazie danych to proszę o dodawanie "Table" na końcu
    // Piszemy: "WorkerTable", nie "Worker_Table", ani "worker_table" !

    //Ta tabela ogarnia: administatorów, managerów i workerów
    private static int id_user;
    private static String name;
    private static String surname;
    private static String address;
    private static String zip;
    private static String place;
    private static int phone_number;
    private static int position_id;
    private int login;
    private static int groups;

    public static int getId_user() {
        return id_user;
    }

    public static void setId_user(int id_user) {
        UsersTable.id_user = id_user;
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

    public static int getPhone_number() {
        return phone_number;
    }

    public static void setPhone_number(int phone_number) {
        UsersTable.phone_number = phone_number;
    }

    public static int getPosition_id() {
        return position_id;
    }

    public static void setPosition_id(int position_id) {
        UsersTable.position_id = position_id;
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


