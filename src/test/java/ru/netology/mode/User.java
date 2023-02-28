package ru.netology.mode;

public class User {
    private String id;
    private String login;
    private String password;
    private String active;

    public User() {
    }

    public User(String id, String login, String password, String active) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.active = active;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String isActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}