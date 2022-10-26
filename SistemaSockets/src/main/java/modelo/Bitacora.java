/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author Alexis
 */
public class Bitacora {
    
    private String evento;
    private String fecha;

    public Bitacora(String evento, String fecha) {
        this.evento = evento;
        this.fecha = fecha;
    }
    
    public Bitacora() {
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }
    
    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    } 
}
