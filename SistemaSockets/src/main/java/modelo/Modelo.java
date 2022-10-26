/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Alexis
 */
public class Modelo {

    private static Modelo modelo;
    ArchivoIO archivoIO = ArchivoIO.getInstancia();

    public static Modelo getInstancia() {
        if (modelo == null) {
            modelo = new Modelo();
        }
        return modelo;
    }

    ArrayList<Producto> listaProductos = new ArrayList<>();
    ArrayList<Bitacora> listaBitacoras = new ArrayList<>();

    private String direccionNombresProductos = "src\\main\\java\\modelo\\archivosProductos";
    private String archivoProductos = "src\\main\\java\\modelo\\Productos.txt";
    private String archivoBitacora = "src\\main\\java\\modelo\\Bitacora.txt";

    public Modelo() {
        inicializarListaProductos();
        //modelo.votar("Pepsi");
    }

    public void inicializarListaProductos() {
        archivoIO.limpiarDirectorio(direccionNombresProductos);
        //ArrayList<String> listaNombres = archivoIO.leerArchivo(direccionNombresProductos);
        ArrayList<String> listaStrProductos = new ArrayList<>();

        ArchivoIO archivoProducto = ArchivoIO.getInstancia();
        listaStrProductos = archivoProducto.leerArchivo(archivoProductos);

        for (int i = 0; i < listaStrProductos.size(); i++) {
            Producto unProducto = new Producto(listaStrProductos.get(i), 0);
            //System.out.println(listaPro.get(i));
            listaProductos.add(unProducto);
            File archivoVotaciones = archivoIO.crearArchivo(direccionNombresProductos
                    + "/" + listaProductos.get(i).getNombre()
                    + "Votaciones.txt");
        }
    }

    public void inicializarBitacora() {

    }

    public Producto votar(String nombre) {
        for (int i = 0; i < listaProductos.size(); i++) {
            if (nombre.equals(listaProductos.get(i).getNombre())) {
                listaProductos.get(i).setVotos(listaProductos.get(i).getVotos() + 1);
                return listaProductos.get(i);
            }
        }
        return null;
    }

    public Bitacora registrarEnBitacora(String evento, String fecha) {
        Bitacora nuevaBitacora = new Bitacora(evento, fecha);
        listaBitacoras.add(nuevaBitacora);

        //Producto producto = encontrarProducto(evento);
        //producto.setVotos(producto.getVotos() + 1);
        //Date fecha = new Date();
        archivoIO.escribirArchivo(direccionNombresProductos
                + "/" + nuevaBitacora.getEvento()
                + "Votaciones.txt", nuevaBitacora.getFecha());

        archivoIO.escribirArchivo(archivoBitacora, nuevaBitacora.getEvento() + "," + nuevaBitacora.getFecha());

        return nuevaBitacora;
    }

    private Producto encontrarProducto(String nombreProducto) {
        for (int i = 0; i < listaProductos.size(); i++) {
            Producto producto = listaProductos.get(i);
            if (producto.getNombre() == nombreProducto) {
                return producto;
            }
        }
        return null;
    }

    public ArrayList<Producto> contarProductos() {
        ArrayList<Producto> listaProductosActual = new ArrayList<>();
        listaProductosActual = listaProductos;
        return listaProductosActual;
    }

    public Object contarProductosArray() {
        int tamanioArray = listaProductos.size() * 2;
        Object[] parametros = new Object[tamanioArray];
        int contador1 = 0;
        int contador2 = 0;

        while (contador1 < listaProductos.size()) {
            parametros[contador2] = listaProductos.get(contador1).getNombre() + "";
            parametros[contador2 + 1] = listaProductos.get(contador1).getVotos() + "";
            contador1++;
            contador2 = contador2 + 2;
        }
        return parametros;
    }

    public Object listarBitacorasArray() {
        if (listaBitacoras.isEmpty()) {
            Object []object = {"Bitacora vacia"};
            return object;
        } else {
            int tamanioArray = listaBitacoras.size() * 2;
            Object[] parametros = new Object[tamanioArray];
            int contador1 = 0;
            int contador2 = 0;

            while (contador1 < listaBitacoras.size()) {
                parametros[contador2] = listaBitacoras.get(contador1).getEvento() + "";
                parametros[contador2 + 1] = listaBitacoras.get(contador1).getFecha() + "";
                contador1++;
                contador2 = contador2 + 2;
            }
            return parametros;
        }
    }

    public ArrayList<Bitacora> listarBitacora() {
        ArrayList<Bitacora> listaBitacoraActual = new ArrayList<>();
        listaBitacoraActual = listaBitacoras;
        return listaBitacoraActual;
    }

    public void imprimirListaProductos() {
        for (Producto listaProducto : listaProductos) {
            System.out.println(listaProducto.getNombre() + ":" + listaProducto.getVotos());
        }
    }

    public void imprimirListaBitacora() {
        for (Bitacora listaBitacora : listaBitacoras) {
            System.out.println(listaBitacora.getEvento() + ":" + listaBitacora.getFecha());
        }
    }

}
