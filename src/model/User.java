package model;

import helper.DBConnetion;

public class User {

    private int id;
    private String tckn;
    private String name;
    private String password;
    private String tipi;
    private int dogumtarih;

    DBConnetion conn = new DBConnetion();

    public User(int id, String tcno, String name, String password, String type) {
        this.id = id;
        this.tckn = tcno;
        this.name = name;
        this.password = password;
        this.tipi = type;
        this.dogumtarih = dogumtarih;

    }

    public User() {
    }

    public int getDogumtarih() {
        return dogumtarih;
    }

    public void setDogumtarih(int dogumtarih) {
        this.dogumtarih = dogumtarih;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTckn() {
        return tckn;
    }

    public void setTckn(String tckn) {
        this.tckn = tckn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTipi() {
        return tipi;
    }

    public void setTipi(String tipi) {
        this.tipi = tipi;
    }
}
