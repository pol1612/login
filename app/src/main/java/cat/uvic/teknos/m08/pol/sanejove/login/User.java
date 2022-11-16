package cat.uvic.teknos.m08.pol.sanejove.login;

import android.view.View;

public class User {
    private String email;
    private String password;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "\nUser{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
