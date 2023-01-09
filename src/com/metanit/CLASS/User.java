package com.metanit.CLASS;

public class User {
    private Integer ID;
    private String login;
    private String password;
    private String profession;
    private String Firstname;

    public User(int ID, String login, String Firstname, String profession) {
        this.ID =ID;
        this.login = login;
        this.password = password;
        this.profession = profession;
        this.Firstname=Firstname;
    }
    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
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

//    public String getPasword() {
//        return password;
//    }
//
//    public void setPasword(String pasword) {
//        this.password = pasword;
//    }

    public String getProfession() {return profession; }

    public void setProfession(String profession) { this.profession = profession; }
}