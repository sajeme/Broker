/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketsmulticliente.Cliente;

/**
 *
 * @author Alexis
 */
import ManejadoresJson.ManipuladorSolicitudes;
import ManejadoresJson.PlantillasJSON;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Bitacora;
import modelo.Producto;

/**
 *
 * @author Alexis
 */
public class ClienteVotos {

    private Socket clientSocket;
    private PrintWriter writer;
    private BufferedReader reader;
    private String evento;
    private String fecha;

    public void startConnection(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            writer = new PrintWriter(clientSocket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException ex) {
            Logger.getLogger(ClienteVotos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String sendMessage(String msg) {
        try {
            writer.println(msg);
            //System.out.println("mensaje enviado desde: [Cliente de Votos]");

            String resp = reader.readLine();
            //System.out.println("Respuesta: " + resp);
            return resp;
        } catch (IOException ex) {
            //Logger.getLogger(ClienteVotos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void stopConnection() {
        try {
            //System.out.println("Cliente desconectado");
            //writer.print("bye");
            reader.close();
            writer.close();
            clientSocket.close();
            //System.out.println("conexion cerrada");
        } catch (IOException ex) {
            Logger.getLogger(ClienteVotos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        //ClienteVotos clienteVotos = new ClienteVotos();
        //ManipuladorSolicitudes manipuladorSolicitudes = new ManipuladorSolicitudes();
        Scanner intEntrada = new Scanner(System.in);
        Scanner entradaStr = new Scanner(System.in);
        Bitacora bitacora = new Bitacora();
        Producto producto = new Producto();

        System.out.println("");
        System.out.println("---------------------------------");
        System.out.println("-------Cliente de votos----------");
        System.out.println("---------------------------------");
        System.out.println("");

        int port = 0;
        System.out.println("Ingrese el puerto");
        port = intEntrada.nextInt();

        String msj = "";
        String opcNum = "";

        do {

            ClienteVotos clienteVotos = new ClienteVotos();
            clienteVotos.startConnection("localhost", port);
            System.out.println("");
            System.out.println("");
            System.out.println("--------------------------------------------");
            System.out.println("Ingrese un numero para ejecutar un servicio");
            System.out.println("1. Solicitud ejecutar servicios");
            System.out.println("2. Solicitud listar servicios");
            System.out.println("0. Terminar");
            opcNum = entradaStr.nextLine();

            if (opcNum.equals("1")) {
                //Parámetros:Nombre del servicio a ejecutar y parámetros que son necesarios
                System.out.println("-------------------------------");
                System.out.println("-Solicitando ejecutar servicio-");
                System.out.println("-------------------------------");
                System.out.println("Ingresee el nombre del servicio a ejecutar");
                System.out.println("1. votar");
                System.out.println("2. contar");
                System.out.println("3. listar");
                System.out.println("4. registrar");
                String opcServicio = entradaStr.nextLine();

                if (opcServicio.equals("1")) {
                    System.out.println("Ingrese el nombre del producto");
                    String nombreProducto = entradaStr.nextLine();
                    Object[] parametros = {nombreProducto, "1"};
                    ManipuladorSolicitudes manipuladorSolicitudes = new ManipuladorSolicitudes();
                    String json = manipuladorSolicitudes.generarSolicitud(PlantillasJSON.TipoSolicitud.EJECUTAR_SERVICIO_VOTAR, parametros);
                    System.out.println("Solicitud: " + json);
                    String strRespuesta = clienteVotos.sendMessage(json);
                    System.out.println("Respuesta: " + strRespuesta);
                    clienteVotos.stopConnection();
                    producto.setNombre(nombreProducto);
                } else if (opcServicio.equals("2")) {
                    //Object[] parametros = {"Pepsi","1"};
                    ManipuladorSolicitudes manipuladorSolicitudes = new ManipuladorSolicitudes();
                    String json = manipuladorSolicitudes.generarSolicitud(PlantillasJSON.TipoSolicitud.EJECUTAR_SERVICIO_CONTAR, null);
                    System.out.println("Solicitud: " + json);
                    String strRespuesta = clienteVotos.sendMessage(json);
                    System.out.println("Respuesta: " + strRespuesta);
                    clienteVotos.stopConnection();
                } else if (opcServicio.equals("3")) {
                    ManipuladorSolicitudes manipuladorSolicitudes = new ManipuladorSolicitudes();
                    String json = manipuladorSolicitudes.generarSolicitud(PlantillasJSON.TipoSolicitud.EJECUTAR_SERVICIO_LISTAR, null);
                    System.out.println("Solicitud: " + json);
                    String strRespuesta = clienteVotos.sendMessage(json);
                    System.out.println("Respuesta: " + strRespuesta);
                    clienteVotos.stopConnection();
                } else if (opcServicio.equals("4")) {
                    //System.out.println("Ingrese el nombre del producto");
                    //String nombreProducto = entradaStr.nextLine();
                    Date fecha = new Date();
                    Object[] parametros = {"Pepsi", fecha.toLocaleString()};
                    ManipuladorSolicitudes manipuladorSolicitudes = new ManipuladorSolicitudes();
                    String json = manipuladorSolicitudes.generarSolicitud(PlantillasJSON.TipoSolicitud.EJECUTAR_SERVICIO_REGISTRAR, parametros);
                    System.out.println("Solicitud: " + json);
                    String strRespuesta = clienteVotos.sendMessage(json);
                    System.out.println("Respuesta: " + strRespuesta);
                    clienteVotos.stopConnection();
                }

                if (opcServicio.equals("1")) {
                    bitacora.setEvento("votar para producto: " + producto.getNombre());
                    bitacora.setFecha("" + new Date().toLocaleString());
                } else if (opcServicio.equals("2")) {
                    bitacora.setEvento("contar");
                    bitacora.setFecha("" + new Date().toLocaleString());
                } else if (opcServicio.equals("3")) {
                    bitacora.setEvento("listar");
                    bitacora.setFecha("" + new Date().toLocaleString());
                } else if (opcServicio.equals("4")) {
                    bitacora.setEvento("registrar");
                    bitacora.setFecha("" + new Date().toLocaleString());
                }

            } else if (opcNum.equals("2")) {
                ManipuladorSolicitudes manipuladorSolicitudes = new ManipuladorSolicitudes();
                System.out.println("-------------------------------");
                System.out.println("--Solicitando servicio listar--");
                System.out.println("-------------------------------");
                System.out.println("");
                System.out.println("Ingrese el nombre del servicio");
                String nombreServicio = "";
                nombreServicio = entradaStr.nextLine();
                Object[] parametros = {nombreServicio};

                String strJson = manipuladorSolicitudes.generarSolicitud(PlantillasJSON.TipoSolicitud.LISTAR_SERVICIOS_BROKER, parametros);
                System.out.println("Solicitud: " + strJson);
                String strRespuesta = clienteVotos.sendMessage(strJson);
                System.out.println("Respuesta: " + strRespuesta);
                clienteVotos.stopConnection();
            } else if (opcNum.equals("0")) {
                System.out.println("Cliente cerrado");
                clienteVotos.stopConnection();
                opcNum = "0";
                break;
            }
            /*
             else if (opcNum.equals("0")) {
                break;
            }
             */

        } while (!(opcNum.equals("0")));
    }

}
