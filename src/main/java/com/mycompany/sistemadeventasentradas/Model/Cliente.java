package com.mycompany.sistemadeventasentradas.Model;

public class Cliente {
    private String ID;
    private String password;

    public Cliente(String ID, String password) {
        this.ID = ID;
        this.password = password;
    }

    public String getID() {
        return ID;
    }

    public String getPassword() {
        return password;
    }   
}
