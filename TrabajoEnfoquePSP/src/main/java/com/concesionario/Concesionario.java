package com.concesionario;

import java.util.concurrent.Semaphore;

/**
 * Clase principal que simula el concesionario de vehículos.
 *
 * Autor: JOSE MANUEL
 */
public class Concesionario {

    public static void main(String[] args) {
        
        // Semáforo con 4 permisos, representando los 4 vehículos disponibles
        Semaphore semaforo = new Semaphore(4);

        // Array con los nombres de los 9 clientes
        String[] clientes = {
            "Cliente1", "Cliente2", "Cliente3",
            "Cliente4", "Cliente5", "Cliente6",
            "Cliente7", "Cliente8", "Cliente9"
        };

        // Crear y arrancar un hilo por cada cliente
        for (String nombre : clientes) {
            new Thread(new Cliente(nombre, semaforo)).start();
        }
    }
}
