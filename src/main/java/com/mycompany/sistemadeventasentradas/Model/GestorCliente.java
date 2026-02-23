package com.mycompany.sistemadeventasentradas.Model;

import java.util.ArrayList;
import java.util.List;

public class GestorCliente {
    private List<Cliente> listaUsuarios;
    
    public GestorCliente() {
        listaUsuarios = new ArrayList<>();
        listaUsuarios.add(new Cliente("admin","2468"));
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
    
    public boolean existeUsuario(String id){
        for(Cliente c: listaUsuarios){
            if(c.getID().equals(id))
                return true;
        }
        return false;
    }
    
    public boolean registrarCliente(String id, String password){
        for(Cliente c: listaUsuarios){
            if(c.getID().equals(id)){
                return false;
            }
        }
        listaUsuarios.add(new Cliente(id, password));
        return true;
    }
}
