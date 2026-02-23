/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemadeventasentradas.Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


public class GestorPersistencia {
 
    private static final String ARCHIVO_EVENTOS  = "eventos.txt";
    private static final String ARCHIVO_ASIENTOS = "asientos.txt";
    private static final String ARCHIVO_VENTAS   = "ventas.txt";

   
    //  GUARDAR

    public static void guardarEventos(List<Evento> eventos) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO_EVENTOS))) {
            for (Evento e : eventos) {
                pw.println(e.getNombre() + "," + e.getFecha() + "," + e.getPrecioBase());
            }
        } catch (IOException ex) {
            System.err.println("[Persistencia] Error guardando eventos: " + ex.getMessage());
        }
    }

    public static void guardarAsientos(List<Evento> eventos) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO_ASIENTOS))) {
            for (Evento ev : eventos) {
                boolean[][] mat = ev.getAsientos();
                for (int i = 0; i < Evento.FILAS; i++) {
                    for (int j = 0; j < Evento.COLUMNAS; j++) {
                        pw.println(ev.getNombre() + "," + i + "," + j + "," + mat[i][j]);
                    }
                }
            }
        } catch (IOException ex) {
            System.err.println("[Persistencia] Error guardando asientos: " + ex.getMessage());
        }
    }

    public static void guardarVentas(List<Evento> eventos) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO_VENTAS))) {
            for (Evento ev : eventos) {
                for (Entrada en : ev.getEntradasVendidas()) {
                    pw.println(
                        ev.getNombre()       + "," +
                        en.getIdReserva()    + "," +
                        en.getNombreCliente() + "," +
                        en.getIdCliente()    + "," +
                        en.getFila()         + "," +
                        en.getColumna()      + "," +
                        en.getPrecioBase()   + "," +
                        en.getTipoEntrada()
                    );
                }
            }
        } catch (IOException ex) {
            System.err.println("[Persistencia] Error guardando ventas: " + ex.getMessage());
        }
    }

    /** Guarda el ticket de compra como archivo .txt individual */
    public static void guardarTicket(String contenido, String idReserva) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("ticket_" + idReserva + ".txt"))) {
            pw.println(contenido);
        } catch (IOException ex) {
            System.err.println("[Persistencia] Error guardando ticket: " + ex.getMessage());
        }
    }

    /** Llama a los tres métodos de guardado en orden */
    public static void guardarTodo(List<Evento> eventos) {
        guardarEventos(eventos);
        guardarAsientos(eventos);
        guardarVentas(eventos);
    }
 
    
    //  CARGAR

    public static List<Evento> cargarEventos() {
        List<Evento> lista = new ArrayList<>();
        File f = new File(ARCHIVO_EVENTOS);
        if (!f.exists()) return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linea;
            while ((linea = br.readLine()) != null && !linea.isBlank()) {
                String[] p = linea.split(",", 3);
                if (p.length == 3) {
                    lista.add(new Evento(p[0], p[1], Double.parseDouble(p[2])));
                }
            }
        } catch (IOException | NumberFormatException ex) {
            System.err.println("[Persistencia] Error cargando eventos: " + ex.getMessage());
        }
        return lista;
    }

    public static void cargarAsientos(List<Evento> eventos) {
        File f = new File(ARCHIVO_ASIENTOS);
        if (!f.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linea;
            while ((linea = br.readLine()) != null && !linea.isBlank()) {
                String[] p = linea.split(",", 4);
                if (p.length == 4 && Boolean.parseBoolean(p[3])) {
                    String nombre = p[0];
                    int fila = Integer.parseInt(p[1]);
                    int col  = Integer.parseInt(p[2]);
                    for (Evento e : eventos) {
                        if (e.getNombre().equals(nombre)) { e.reservarAsiento(fila, col); break; }
                    }
                }
            }
        } catch (IOException | NumberFormatException ex) {
            System.err.println("[Persistencia] Error cargando asientos: " + ex.getMessage());
        }
    }

    public static void cargarVentas(List<Evento> eventos) {
        File f = new File(ARCHIVO_VENTAS);
        if (!f.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linea;
            while ((linea = br.readLine()) != null && !linea.isBlank()) {
                String[] p = linea.split(",", 8);
                if (p.length == 8) {
                    Entrada en = fabricar(p[7], p[1], p[2], p[3],
                            Integer.parseInt(p[4]), Integer.parseInt(p[5]),
                            Double.parseDouble(p[6]), p[0]);
                    for (Evento e : eventos) {
                        if (e.getNombre().equals(p[0])) { e.agregarVenta(en); break; }
                    }
                }
            }
        } catch (IOException | NumberFormatException ex) {
            System.err.println("[Persistencia] Error cargando ventas: " + ex.getMessage());
        }
    }

private static Entrada fabricar(String tipo, String idR, String nom, String idC,
                                 int fila, int col, double precio, String evento) {
    Entrada entrada;
    
    switch (tipo) {
        case "VIP":
           
            entrada = new EntradaVIP(nom, idC, idR, evento, fila, col, precio);
            break;
            
        case "Estudiante":
            entrada = new EntradaEstudiante(nom, idC, idR, evento, fila, col, precio);
            break;
            
        default:
            entrada = new EntradaGeneral(nom, idC, idR, evento, fila, col, precio);
            break;
    }
    
    return entrada;
 }
}