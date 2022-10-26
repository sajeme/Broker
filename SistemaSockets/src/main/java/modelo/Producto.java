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
public class Producto {

    private String nombre;
    private int votos;

    public Producto(String nombre, int votos) {
        this.nombre = nombre;
        this.votos = votos;
    }
    
     public Producto() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getVotos() {
        return votos;
    }

    public void setVotos(int votos) {
        this.votos = votos;
    }

    @Override
    public String toString() {
        return "Producto{" + "nombre = " + nombre + ", votos = " + votos + '}';
    }

    

}
