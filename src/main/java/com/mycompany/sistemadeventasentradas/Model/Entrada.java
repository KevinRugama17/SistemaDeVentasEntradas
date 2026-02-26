package com.mycompany.sistemadeventasentradas.Model;

public abstract class Entrada implements IVendible{
  
    protected String nombreCliente;
    protected String idCliente;
    protected String idReserva;
    protected String nombreEvento;
    protected int fila;
    protected int  columna;
    protected double precioBase;

    public Entrada(String nombreCliente, String idCliente, String idReserva, String nombreEvento, int fila, int columna, double precioBase) {
        this.nombreCliente = nombreCliente;
        this.idCliente = idCliente;
        this.idReserva = idReserva;
        this.nombreEvento = nombreEvento;
        this.fila = fila;
        this.columna = columna;
        this.precioBase = precioBase;
    }

    
    public abstract String getTipoEntrada ();
    

    public String getNombreCliente() {
        return nombreCliente;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public String getIdReserva() {
        return idReserva;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public double getPrecioBase() {
        return precioBase;
    }
    
     @Override
    public String toString() {
        return String.format("[%s] %s | Asiento (%c%d) | ₡%.2f",
                getTipoEntrada(), nombreCliente,
                (char)('A' + fila), columna + 1,
                calcularPrecio());
    }
    
    
    
}
