package data;

public class CredentialsForCourier {
    private String login;
    private String password;

    public CredentialsForCourier(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public static CredentialsForCourier from(CourierData courier) {
        return new CredentialsForCourier(courier.getLogin(), courier.getPassword());
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
