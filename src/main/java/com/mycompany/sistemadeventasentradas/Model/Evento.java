/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemadeventasentradas.Model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kevin
 */
public class Evento {
    
    public static final int FILAS    = 10;
    public static final int COLUMNAS = 10;

    private String nombre;
    private String fecha;
    private double precioBase;
    private boolean[][] asientos;
    private List<Entrada> entradasVendidas;

    public Evento(String nombre, String fecha, double precioBase) {
        this.nombre           = nombre;
        this.fecha            = fecha;
        this.precioBase       = precioBase;
        this.asientos         = new boolean[FILAS][COLUMNAS];
        this.entradasVendidas = new ArrayList<>();
    }

    
    public boolean isAsientoLibre(int fila, int col) 
    { return !asientos[fila][col]; }
    
    
    public void reservarAsiento(int fila, int col)   
    { asientos[fila][col] = true;  }
    
    
    public void liberarAsiento(int fila, int col)
    { asientos[fila][col] = false; }
    
    
    public void reiniciarSala() {
        asientos         = new boolean[FILAS][COLUMNAS];
        entradasVendidas = new ArrayList<>();
    }

    
    public void agregarVenta(Entrada entrada) 
    { entradasVendidas.add(entrada); }

   
    public double getTotalRecaudado() {
        double total = 0;
        for (Entrada entrada : entradasVendidas) total += entrada.calcularPrecio();
        return total;
    }
    

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(double precioBase) {
        this.precioBase = precioBase;
    }

    public boolean[][] getAsientos() {
        return asientos;
    }

    public void setAsientos(boolean[][] asientos) {
        this.asientos = asientos;
    }

    public List<Entrada> getEntradasVendidas() {
        return entradasVendidas;
    }
    
    
        @Override
    public String toString() {
        return nombre + "  |  " + fecha + "  |  ₡" + String.format("%.2f", precioBase);
    }
    
    

    
}
