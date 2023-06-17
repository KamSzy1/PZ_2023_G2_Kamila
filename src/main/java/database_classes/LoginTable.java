package database_classes;

/**
 * Klasa reprezentująca tabelę "Login" z bazy danych
 */
public class LoginTable {

    /**
     * @param idLogin ID
     */
    private int idLogin;
    /**
     * @param email E-mail
     */
    private String email;
    /**
     * @param password Hasło
     */
    private String password;

    public int getIdLogin() {
        return idLogin;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setIdLogin(int idLogin) {
        this.idLogin = idLogin;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
