/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemadeventasentradas.Controller;

import com.mycompany.sistemadeventasentradas.Model.*;
import java.util.ArrayList;
import java.util.List;


public class ControladorPrincipal {
 
    private final List<Evento> eventos;
    private Evento eventoActual;
    private int contadorReservas = 1;

    public ControladorPrincipal() {
        
        eventos = GestorPersistencia.cargarEventos();
        GestorPersistencia.cargarAsientos(eventos);
        GestorPersistencia.cargarVentas(eventos);

        if (!eventos.isEmpty()) eventoActual = eventos.get(0);
    }

   
    //  GESTIÓN DE EVENTOS


    public void crearEvento(String nombre, String fecha, double precioBase) {
        Evento nuevo = new Evento(nombre, fecha, precioBase);
        eventos.add(nuevo);
        if (eventoActual == null) eventoActual = nuevo;
    }

    public void editarEvento(int indice, String nombre, String fecha, double precioBase) {
        Evento e = eventos.get(indice);
        e.setNombre(nombre);
        e.setFecha(fecha);
        e.setPrecioBase(precioBase);
    }

    public void eliminarEvento(int indice) {
        Evento eliminado = eventos.remove(indice);
        if (eliminado == eventoActual) {
            eventoActual = eventos.isEmpty() ? null : eventos.get(0);
        }
    }

    public void seleccionarEvento(int indice) {
        if (indice >= 0 && indice < eventos.size()) {
            eventoActual = eventos.get(indice);
        }
    }

    public List<Evento> getEventos()    { return eventos; }
    public Evento getEventoActual()      { return eventoActual; }

   
    //  VENTA DE ENTRADAS

    public String comprarEntrada(String nombreCliente, String idCliente,
                             int fila, int col, String tipoEntrada) {
    if (eventoActual == null || !eventoActual.isAsientoLibre(fila, col)) return null;

    String idReserva  = "R" + String.format("%04d", contadorReservas++);
    double precioBase = eventoActual.getPrecioBase();
    String nomEvento  = eventoActual.getNombre();

    Entrada entrada;
    switch (tipoEntrada) {
        case "VIP":
            entrada = new EntradaVIP(nombreCliente, idCliente, idReserva, nomEvento, fila, col, precioBase);
            break;
        case "Estudiante":
            entrada = new EntradaEstudiante(nombreCliente, idCliente, idReserva, nomEvento, fila, col, precioBase);
            break;
        default:
            entrada = new EntradaGeneral(nombreCliente, idCliente, idReserva, nomEvento, fila, col, precioBase);
            break;
    }

    eventoActual.reservarAsiento(fila, col);
    eventoActual.agregarVenta(entrada);

    String ticket = entrada.generarTicket();
    GestorPersistencia.guardarTicket(ticket, idReserva);
    return ticket;
}


    //  BÚSQUEDA

    public Entrada buscarPorIdCliente(String id) {
        if (eventoActual == null) return null;
        return BusquedaLineal.buscarPorIdCliente(eventoActual.getEntradasVendidas(), id);
    }

    public Entrada buscarPorIdReserva(String id) {
        if (eventoActual == null) return null;
        return BusquedaLineal.buscarPorIdReserva(eventoActual.getEntradasVendidas(), id);
    }

    public List<Entrada> buscarTodasPorCliente(String id) {
        if (eventoActual == null) return new ArrayList<>();
        return BusquedaLineal.buscarTodasPorCliente(eventoActual.getEntradasVendidas(), id);
    }

  
    //  ADMINISTRACIÓN

    public void reiniciarSala() {
        if (eventoActual != null) eventoActual.reiniciarSala();
    }

    public double getTotalRecaudado() {
        return eventoActual != null ? eventoActual.getTotalRecaudado() : 0;
    }

   
    public String getReporteEvento() {
        if (eventoActual == null) return "No hay evento seleccionado.";
        StringBuilder sb = new StringBuilder();
        sb.append("═══════════════════════════════════════\n");
        sb.append("  REPORTE: ").append(eventoActual.getNombre()).append("\n");
        sb.append("═══════════════════════════════════════\n");
        sb.append("  Fecha      : ").append(eventoActual.getFecha()).append("\n");
        sb.append("  Precio Base: ₡").append(String.format("%.2f", eventoActual.getPrecioBase())).append("\n\n");

        List<Entrada> ventas = eventoActual.getEntradasVendidas();
        if (ventas.isEmpty()) {
            sb.append("  Sin ventas registradas.\n");
        } else {
            sb.append("  Entradas vendidas (").append(ventas.size()).append("):\n\n");
            for (Entrada e : ventas) {
                sb.append("  ▸ ").append(e).append("\n");
            }
        }
        sb.append("\n  TOTAL RECAUDADO: ₡").append(String.format("%.2f", getTotalRecaudado())).append("\n");
        sb.append("═══════════════════════════════════════\n");
        return sb.toString();
    }


    //  PERSISTENCIA
 
    public void guardarTodo() {
        GestorPersistencia.guardarTodo(eventos);
    }
}
