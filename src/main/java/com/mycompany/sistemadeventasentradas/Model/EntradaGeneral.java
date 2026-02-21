/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemadeventasentradas.Model;

/**
 *
 * @author kevin
 */
public  class EntradaGeneral extends Entrada{
    
    public EntradaGeneral(String nombreCliente, String idCliente, String idReserva, String nombreEvento, int fila, int columna, double precioBase) {
        super(nombreCliente, idCliente, idReserva, nombreEvento, fila, columna, precioBase);
    }
    
    @Override
    public String getTipoEntrada(){
       return "General";
    }
    
    @Override
    public double calcularPrecio(){
       return precioBase;
    }
    
    @Override
    public String generarTicket(){
        return "╔══════════════════════════════╗\n"
             + "║     ENTRADA GENERAL          ║\n"
             + "╠══════════════════════════════╣\n"
             + "║ ID Reserva : " + rellenarTicket(idReserva, 16) + " ║\n"
             + "║ Cliente    : " + rellenarTicket(nombreCliente, 16) + " ║\n"
             + "║ ID Cliente : " + rellenarTicket(idCliente, 16) + " ║\n"
             + "║ Evento     : " + rellenarTicket(nombreEvento, 16) + " ║\n"
             + "║ Asiento    : " + rellenarTicket((char)('A'+fila)+""+(columna+1), 16) + " ║\n"
             + "║ Tipo       : " + rellenarTicket("General", 16) + " ║\n"
             + "║ Precio     : " + rellenarTicket("₡"+String.format("%.2f",calcularPrecio()), 16) + " ║\n"
             + "╚══════════════════════════════╝";
   
    }
        private String rellenarTicket(String s, int n) {
        if (s.length() > n) s = s.substring(0, n);
        return String.format("%-" + n + "s", s);
    }
}
