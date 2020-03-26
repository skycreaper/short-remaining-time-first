package edu.co.srtf.logic;

import edu.co.srtf.UI.GUI;
import edu.co.srtf.models.Process;
import edu.co.srtf.util.SortByExecutionTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author juancamilo
 */
public class Queue {

    private final String ALPHABETH[] = {"A", "B", "C", "D", "E", "F", "G", "H",
        "I", "J", "K", "L", "M", "N", "Ñ", "O", "P", "Q", "R", "S", "T", "U", "V",
        "W", "X", "Y", "Z"};

    private final GUI gui;
    private int numeroDeProcesos; // Número total de procesos
    private int tiempoTotal; // Tiempo total de ejecución

    private List<Process> procesos;

    public Queue() {
        this.gui = new GUI();
        gui.btnStart.addActionListener(new java.awt.event.ActionListener() {      //Metodo implementado provisional para el actionperformed 
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartActionPerformed(evt);
            }
        });
    }

    public void initProcesses() {
        Process newProcess;
        int tiempoDeEjecucion;
        tiempoTotal = 0;
        Random ran = new Random();
        numeroDeProcesos = ran.nextInt(3) + 3;
        procesos = new ArrayList<>();

        for (int i = 0; i < numeroDeProcesos; i++) {
            tiempoDeEjecucion = ran.nextInt(6) + 1;
            newProcess = new Process(ALPHABETH[i], i, i, tiempoDeEjecucion, tiempoDeEjecucion);
            tiempoTotal += tiempoDeEjecucion;
            newProcess.setColor(gui.getRandomColor());
            newProcess.setFilaTabla(i);
            newProcess.setFila(i);
            procesos.add(newProcess);
        }
    }

    /**
     * Establece cual será el siguiente proceso a ser ejecutado
     *
     * @return
     */
    Process despacharProceso(Process procesoEjecucion) {
        Process proceso;
        for (int i = 0; i < procesos.size(); i++) {
            proceso = procesos.get(i);
            if (i + 1 < procesos.size()) {
                procesos.get(i + 1).setListo(true);
            }
            if (proceso.isListo() && !proceso.equals(procesoEjecucion)) {
                if (proceso.getTiempoRafaga() < procesoEjecucion.getTiempoRafaga()) {
                    procesoEjecucion = proceso;
                }
                if (proceso.getTiempoRafaga() == procesoEjecucion.getTiempoRafaga()) {
                    if (proceso.getTiempoLlegada() < procesoEjecucion.getTiempoLlegada()) {
                        procesoEjecucion = proceso;
                    }
                }
            }
        }

        return procesoEjecucion;
    }

    void btnStartActionPerformed(java.awt.event.ActionEvent evt) {
        initProcesses();
        gui.dibujarTabla(procesos);
        gui.dibujarDiagrama(procesos, tiempoTotal);

        Thread hiloPrincipal = new Thread() {
            int tiempoDeComienzo = 0;
            int tiempoRestante = 0;
            boolean primerProceso = true;
            Process procesoEjecucion = null;

            @Override
            public void run() {
                try {
                    gui.btnStart.setEnabled(false);

                    while (procesos.size() > 0) {

                        procesoEjecucion = procesos.get(0);
                        procesoEjecucion.setListo(true);

                        if (primerProceso) {
                            if (procesoEjecucion.getTiempoRafaga() > procesos.get(1).getTiempoRafaga()) {
                                tiempoDeComienzo = 1;
                                agregarProcesoPendiente(procesoEjecucion);
                                pintarDiagrama(procesoEjecucion, this);

                                procesoEjecucion = procesos.get(0);
                                procesos.get(1).setListo(true);
                            }
                            primerProceso = false;
                        } else {
                            procesoEjecucion = despacharProceso(procesoEjecucion);
                        }

                        procesoEjecucion.setTiempoComienzo(tiempoDeComienzo);
                        procesoEjecucion.calculateTimes();
                        agregarInfoTabla(procesoEjecucion);

                        pintarDiagrama(procesoEjecucion, this);
                        tiempoDeComienzo = procesoEjecucion.getTiempoFinalizacion();

                        procesos.remove(procesoEjecucion); // Aquí finalizó el proceso
                    }
                } catch (InterruptedException e) {
                    System.out.println("Error en hiloPrincipal: " + e.getMessage());
                } finally {
                    gui.btnStart.setEnabled(true);
                }
            }

        };

        hiloPrincipal.start();
    }

    /**
     * pintarDiagrama Pinta la celdas correspondientes a un proceso en el
     * diagram de gant
     *
     * @param procesoEjecucion el proceso que se está ejecuando
     * @param hilo el hilo por el cual se ejecuta el proceso
     * @throws InterruptedException
     */
    private void pintarDiagrama(Process procesoEjecucion, Thread hilo) throws InterruptedException {
        gui.editDiagramCell(procesoEjecucion.getNombre(), procesoEjecucion.getFila(), 0);
        for (int j = procesoEjecucion.getTiempoComienzo(); j < procesoEjecucion.getTiempoFinalizacion(); j++) {
            gui.pintarCelda(j + 1, procesoEjecucion.getFila(), procesoEjecucion.getColor());
            hilo.sleep(1000);
        }
    }

    /**
     * Agrega la información de una fila
     *
     * @param procesoEjecucion proceso que tiene la información a agregar
     */
    private void agregarInfoTabla(Process procesoEjecucion) {
        gui.addTableInfo(procesoEjecucion.getTiempoComienzo(),
                procesoEjecucion.getTiempoFinalizacion(),
                procesoEjecucion.getTiempoRetorno(),
                procesoEjecucion.getTiempoEspera(),
                procesoEjecucion.getFilaTabla()
        );
    }

    /**
     * Agrega una nueva fila a la tabla
     *
     * @param proceso
     */
    private void agregarFilaTabla(Process proceso) {
        gui.addTableRow(proceso.getNombre(),
                proceso.getTiempoLlegadaFront(),
                proceso.getTiempoRafaga()
        );
    }

    /**
     * Agrega un nuevo proceso al final de la lista de procesos y elimina el
     * actual
     *
     * @param procesoOriginal
     */
    private void agregarProcesoPendiente(Process procesoOriginal) {
        Process pendiente = new Process(
                procesoOriginal.getNombre() + "*",
                procesos.size(),
                procesoOriginal.getTiempoLlegada(),
                procesoOriginal.getTiempoRafaga() - 1,
                procesoOriginal.getTiempoRafaga()
        );
        pendiente.setFila(procesoOriginal.getFila());
        pendiente.setFilaTabla(procesos.size());
        pendiente.setDuplicado(true);
        pendiente.setColor(procesoOriginal.getColor());
        pendiente.setListo(true);
        procesos.add(pendiente);
        procesos.remove(procesoOriginal);

        procesoOriginal.setTiempoRafaga(1);
        procesoOriginal.calculateTimes();
        agregarInfoTabla(procesoOriginal);
        agregarFilaTabla(pendiente);
    }
}
