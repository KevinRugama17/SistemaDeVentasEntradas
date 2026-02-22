package com.mycompany.sistemadeventasentradas.Model;

import java.util.ArrayList;
import java.util.List;

public class GestorCliente {
    private List<Cliente> listaUsuarios;
    
    public GestorCliente() {
        listaUsuarios = new ArrayList<>();
        
        listaUsuarios.add(new Cliente("admin","2468"));
        listaUsuarios.add(new Cliente("luis", "abcd"));
        listaUsuarios.add(new Cliente("Kevin", "aeio"));
        listaUsuarios.add(new Cliente("Aaron", "1357"));
        listaUsuarios.add(new Cliente("Sofia", "1234"));
    }
    
    public void agregarUsuarios(Cliente cliente){
        listaUsuarios.add(cliente);
    }
    
    public boolean validarLogin(String ID, String password){
        for(Cliente c: listaUsuarios){
            if(c.getID().equals(ID) && c.getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }
}
