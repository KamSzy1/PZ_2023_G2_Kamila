package database_classes;

public class UsersTable {

    private static int idLoginUser;
    private int idUser;
    private String name;
    private String surname;
    private String address;
    private String zip;
    private String place;
    private int phoneNumber;
    private String email;
    private String password;
    private int positionId;
    private int login;
    private int groups;
    private static String loginName;
    private static String loginSurname;
    private String namePosition;
    private String token;

    public UsersTable() {}

    public UsersTable(int idUser, String name, String surname, String address, String zip, String place, int phoneNumber, int positionId, String token, int groups){
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
    }

    public static int getIdLoginUser() {
        return idLoginUser;
    }

    public int getIdUser() {
        return idUser;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getAddress() {
        return address;
    }

    public String getZip() {
        return zip;
    }

    public String getPlace() {
        return place;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getPositionId() {
        return positionId;
    }

    public int getLogin() {
        return login;
    }

    public int getGroups() {
        return groups;
    }

    public static String getLoginName() {
        return loginName;
    }

    public static String getLoginSurname() {
        return loginSurname;
    }

    public String getNamePosition() {
        return namePosition;
    }

    public String getToken() {
        return token;
    }

    public static void setIdLoginUser(int idLoginUser) {
        UsersTable.idLoginUser = idLoginUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public void setLogin(int login) {
        this.login = login;
    }

    public void setGroups(int groups) {
        this.groups = groups;
    }

    public static void setLoginName(String loginName) {
        UsersTable.loginName = loginName;
    }

    public static void setLoginSurname(String loginSurname) {
        UsersTable.loginSurname = loginSurname;
    }

    public void setNamePosition(String namePosition) {
        this.namePosition = namePosition;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

