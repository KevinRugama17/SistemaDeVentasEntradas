/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemadeventasentradas.Model;

import java.util.ArrayList;
import java.util.List;


public class BusquedaLineal {
 

    
    public static Entrada buscarPorIdCliente(List<Entrada> lista, String idCliente) {
        for (Entrada e : lista) {
            if (e.getIdCliente().equalsIgnoreCase(idCliente.trim())) return e;
        }
        return null;
    }

    public static Entrada buscarPorIdReserva(List<Entrada> lista, String idReserva) {
        for (Entrada e : lista) {
            if (e.getIdReserva().equalsIgnoreCase(idReserva.trim())) return e;
        }
        return null;
    }

  
    public static List<Entrada> buscarTodasPorCliente(List<Entrada> lista, String idCliente) {
        List<Entrada> resultado = new ArrayList<>();
        for (Entrada e : lista) {
            if (e.getIdCliente().equalsIgnoreCase(idCliente.trim())) resultado.add(e);
        }
        return resultado;
    }
}

