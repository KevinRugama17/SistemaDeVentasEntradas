package com.mycompany.sistemadeventasentradas.Model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestorCliente {
    private static final String ARCHIVO_USUARIOS = "usuarios.txt";
    private List<Cliente> listaUsuarios;

    public GestorCliente() {
        listaUsuarios = new ArrayList<>();
        cargarUsuarios();
        // Agregar admin por defecto si no existe
        if (!existeUsuario("admin")) {
            listaUsuarios.add(new Cliente("admin", "2468"));
            guardarUsuarios();
        }
    }

    public void agregarUsuarios(Cliente cliente) {
        listaUsuarios.add(cliente);
    }

    public boolean validarLogin(String ID, String password) {
        for (Cliente c : listaUsuarios) {
            if (c.getID().equals(ID) && c.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public boolean existeUsuario(String id) {
        for (Cliente c : listaUsuarios) {
            if (c.getID().equals(id))
                return true;
        }
        return false;
    }

    public boolean registrarCliente(String id, String password) {
        for (Cliente c : listaUsuarios) {
            if (c.getID().equals(id)) {
                return false;
            }
        }
        listaUsuarios.add(new Cliente(id, password));
        guardarUsuarios(); // Persiste inmediatamente
        return true;
    }

    private void guardarUsuarios() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO_USUARIOS))) {
            for (Cliente c : listaUsuarios) {
                pw.println(c.getID() + "," + c.getPassword());
            }
        } catch (IOException ex) {
            System.err.println("[GestorCliente] Error guardando usuarios: " + ex.getMessage());
        }
    }

    private void cargarUsuarios() {
        File f = new File(ARCHIVO_USUARIOS);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linea;
            while ((linea = br.readLine()) != null && !linea.isBlank()) {
                String[] p = linea.split(",", 2);
                if (p.length == 2) {
                    listaUsuarios.add(new Cliente(p[0], p[1]));
                }
            }
        } catch (IOException ex) {
            System.err.println("[GestorCliente] Error cargando usuarios: " + ex.getMessage());
        }
    }
}