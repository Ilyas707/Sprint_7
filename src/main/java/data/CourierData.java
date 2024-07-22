package data;

import lombok.Data;

@Data
public class CourierData {
    private String login;
    private String password;
    private String firstName;

    public CourierData(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }
}
