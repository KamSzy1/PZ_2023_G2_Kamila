package database_classes;

public class LoginTable {
    private int idLogin;
    private String token;
    private String email;
    private String password;

    public int getIdLogin() {
        return idLogin;
    }

    public String getToken() {
        return token;
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

    public void setToken(String token) {
        this.token = token;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
