package com.metanit.CLASS;

public class UserLOGIN {
    private String login;
    private String password;
    private String profession;
    private String Firstname;


    public UserLOGIN(String login, String Firstname, String password, String profession) {
        this.login = login;
        this.password = password;
        this.profession = profession;
        this.Firstname=Firstname;
    }
    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasword() {
        return password;
    }

    public void setPasword(String pasword) {
        this.password = pasword;
    }

    public String getProfession() {return profession; }

    public void setProfession(String profession) { this.profession = profession; }
}
