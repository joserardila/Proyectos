package com.concesionario;

import java.util.concurrent.Semaphore;

/**
 * Clase Cliente que representa a un cliente del concesionario.
 *
 * Autor: JOSE MANUEL
 */
public class Cliente implements Runnable {

    // Nombre del cliente
    private String nombre;

    // Semáforo compartido que controla acceso a los vehículos
    private Semaphore semaforo;

    // Array compartido de vehículos (false = libre, true = ocupado)
    private static boolean[] vehiculos = {false, false, false, false};

    // Constructor
    public Cliente(String nombre, Semaphore semaforo) {
        this.nombre = nombre;
        this.semaforo = semaforo;
    }

    @Override
    public void run() {
        // Inicializa el vehículo asignado a -1 (ninguno)
        int numeroVehiculo = -1;

        try {
            // Cerrar la sección crítica (máximo 4 clientes simultáneamente)
            semaforo.acquire();

            // Buscar y ocupar un vehículo libre de manera sincronizada
            synchronized (vehiculos) {
                for (int i = 0; i < vehiculos.length; i++) {
                    // Si el vehículo está libre y aún no se ha asignado ninguno
                    if (!vehiculos[i] && numeroVehiculo == -1) {
                        vehiculos[i] = true;       // Marcar el vehículo como ocupado
                        numeroVehiculo = i + 1;    // Guardar el número del vehículo
                    }
                }
            }

            // Mensaje indicando que el cliente comenzó a probar el vehículo
            System.out.println(nombre + " ... probando vehículo ... " + numeroVehiculo);

            // Simular tiempo de prueba del vehículo (1 a 4 segundos)
            Thread.sleep((long) (Math.random() * 3000 + 1000));

            // Mensaje indicando que el cliente terminó de probar el vehículo
            System.out.println(nombre + " ... terminó de probar el vehículo ... " + numeroVehiculo);

            // Liberar el vehículo para que otros clientes puedan usarlo
            synchronized (vehiculos) {
                vehiculos[numeroVehiculo - 1] = false;
            }

            // Abrir la sección crítica
            semaforo.release();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
