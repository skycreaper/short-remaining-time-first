package edu.co.srtf.models;

import java.awt.Color;

import java.awt.Color;

/**
 *
 * @author juancsr
 * @author davidssantoss
 */
public class Process {

    private String nombre;
    private boolean ejecutado;
    private boolean listo;
    private boolean duplicado;
    private int timeWasBlocked;
    private int tiempoLlegada;
    private int tiempoLlegadaFront;
    private int tiempoRafaga;
    private int tiempoTotalRafaga;
    private int tiempoComienzo;
    private int tiempoFinalizacion;
    private int tiempoRetorno;
    private int tiempoEspera;
    private int fila;
    private int filaTabla;
    private Color color;

    /*
    * Processes's constructor 
    * @param name Name of the process
    * @param arriveTime Time it takes to the process arrive into the critial section
    * @param frontArriveTime tiempo de llegada del proceso mostrado en el front
    * @param exectionTime: Time it takes for the process to be executed
     */
    public Process(String nombre, int tiempoLlegada, int tiempoLlegadaFront,
            int tiempoRafaga, int tiempoTotalRafaga) {

        this.nombre = nombre;
        this.tiempoLlegada = tiempoLlegada;
        this.tiempoLlegadaFront = tiempoLlegadaFront;
        this.tiempoRafaga = tiempoRafaga;
        this.tiempoTotalRafaga = tiempoTotalRafaga;
        this.duplicado = false;
        this.listo = false;
    }
    
    /**
     * Calcula los tiempos correspondiente de los procesos
     */
    public void calculateTimes() {
        tiempoFinalizacion = tiempoRafaga + tiempoComienzo;

        if (duplicado) {
            tiempoRetorno = Math.abs(tiempoFinalizacion - tiempoLlegadaFront);
            tiempoEspera = Math.abs(tiempoRetorno - tiempoTotalRafaga);
        } else {
            tiempoRetorno = Math.abs(tiempoFinalizacion - tiempoLlegada);
            tiempoEspera = Math.abs(tiempoRetorno - tiempoRafaga);
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isEjecutado() {
        return ejecutado;
    }

    public void setEjecutado(boolean ejecutado) {
        this.ejecutado = ejecutado;
    }

    public boolean isListo() {
        return listo;
    }

    public void setListo(boolean listo) {
        this.listo = listo;
    }

    public boolean isDuplicado() {
        return duplicado;
    }

    public void setDuplicado(boolean duplicado) {
        this.duplicado = duplicado;
    }

    public int getTimeWasBlocked() {
        return timeWasBlocked;
    }

    public void setTimeWasBlocked(int timeWasBlocked) {
        this.timeWasBlocked = timeWasBlocked;
    }

    public int getTiempoLlegada() {
        return tiempoLlegada;
    }

    public void setTiempoLlegada(int tiempoLlegada) {
        this.tiempoLlegada = tiempoLlegada;
    }

    public int getTiempoLlegadaFront() {
        return tiempoLlegadaFront;
    }

    public void setTiempoLlegadaFront(int tiempoLlegadaFront) {
        this.tiempoLlegadaFront = tiempoLlegadaFront;
    }

    public int getTiempoRafaga() {
        return tiempoRafaga;
    }

    public void setTiempoRafaga(int tiempoRafaga) {
        this.tiempoRafaga = tiempoRafaga;
    }

    public int getTiempoTotalRafaga() {
        return tiempoTotalRafaga;
    }

    public void setTiempoTotalRafaga(int tiempoTotalRafaga) {
        this.tiempoTotalRafaga = tiempoTotalRafaga;
    }

    public int getTiempoComienzo() {
        return tiempoComienzo;
    }

    public void setTiempoComienzo(int tiempoComienzo) {
        this.tiempoComienzo = tiempoComienzo;
    }

    public int getTiempoFinalizacion() {
        return tiempoFinalizacion;
    }

    public void setTiempoFinalizacion(int tiempoFinalizacion) {
        this.tiempoFinalizacion = tiempoFinalizacion;
    }

    public int getTiempoRetorno() {
        return tiempoRetorno;
    }

    public void setTiempoRetorno(int tiempoRetorno) {
        this.tiempoRetorno = tiempoRetorno;
    }

    public int getTiempoEspera() {
        return tiempoEspera;
    }

    public void setTiempoEspera(int tiempoEspera) {
        this.tiempoEspera = tiempoEspera;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getFilaTabla() {
        return filaTabla;
    }

    public void setFilaTabla(int filaTabla) {
        this.filaTabla = filaTabla;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

}
