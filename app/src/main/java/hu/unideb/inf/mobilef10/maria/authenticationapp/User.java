package hu.unideb.inf.mobilef10.maria.authenticationapp;

public class User {
    public String fullName;
    public String email;

    //empty constructor
    public User() {

    }

    public User(String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
    }
}
