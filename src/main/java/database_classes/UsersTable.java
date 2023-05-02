package database_classes;

public class UsersTable {
    // Tutaj doda się kod do tabeli z pracownikami
    // Klasa została utworzona testowo, nazwę można podmienić, ale proszę o:
    // Zasada: jedna tabela == jedna klasa -> ale to jeszcze przedyskutuję z Patrykiem
    // Jeśli clasa odnosi się do tabeli w bazie danych to proszę o dodawanie "Table" na końcu
    // Piszemy: "WorkerTable", nie "Worker_Table", ani "worker_table" !

    //Ta tabela ogarnia: administatorów, managerów i workerów
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

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPositionId() {
        return positionId;

    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public int getLogin() {
        return login;
    }

    public void setLogin(int login) {
        this.login = login;
    }

    public int getGroups() {
        return groups;
    }

    public void setGroups(int groups) {
        this.groups = groups;
    }

}
