/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketsmulticliente.ServidorVotos;

import ManejadoresJson.ManipuladorSolicitudes;
import ManejadoresJson.PlantillasJSON;
//import com.mycompany.sistemasockets.socketsmulticliente.EchoMultiServer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Bitacora;
import modelo.Modelo;
import modelo.Producto;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Alexis
 */
public class ServerVotos {

    Modelo modelo = Modelo.getInstancia();

    Scanner entradaStr = new Scanner(System.in);
    private ServerSocket serverSocket;

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("The server is listening at port: " + serverSocket.getLocalPort());
        while (true) {
            new ManejadorClienteVotos(serverSocket.accept()).start();
        }
    }

    public void stop() {
        try {
            serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerVotos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public class ManejadorClienteVotos extends Thread {

        private Socket clientSocket;
        private PrintWriter writer;
        private BufferedReader reader;

        public ManejadorClienteVotos(Socket clientoSocket) {
            this.clientSocket = clientoSocket;
        }

        @Override
        public void run() {
            try {
                reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                writer = new PrintWriter(clientSocket.getOutputStream(), true);
                System.out.println("");
                System.out.println("");
                System.out.println("ClienteBroker y ServidorVotos emparejados: " + clientSocket.getLocalPort());
                Scanner escaner = new Scanner(System.in);
                String inputLine = "";

                String text;
                //aca llega el JSON
                text = reader.readLine();
                if (text != null) {
                    /*
                    
                     */
                    String stringJson = text;
                    JSONParser parser = new JSONParser();
                    JSONObject json = (JSONObject) parser.parse(stringJson);
                    String tipoServicio = (String) json.get("nombreServicio");

                    //validar que el servicio este dispoble y luego mandarlo al servidor de votos
                    if (tipoServicio.equals("votar")) {
                        System.out.println("--servicio votar--");
                        //parse parametros
                        String arreglo = json.get("parametros").toString();
                        String[] items = arreglo.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");
                        String nombreProductoVotado = items[0];
                        //creeando producto a base de parametros 
                        Producto producto = modelo.votar(nombreProductoVotado);
                        System.out.println("producto" + producto.getNombre() + ", votos" + producto.getVotos());
                        ManipuladorSolicitudes manipuladorSolicitudes = new ManipuladorSolicitudes();
                        Object[] parametros = {producto.getNombre(), "" + producto.getVotos()};
                        String strJson = manipuladorSolicitudes.generarRespuesta(PlantillasJSON.TipoRespuesta.EJECUTAR_SERVICIO_VOTAR, parametros);
                        System.out.println("Respuesta votar: " + strJson);
                        writer.println(strJson);

                    } else if (tipoServicio.equals("contar")) {
                        System.out.println("--servicio contar--");
                        ManipuladorSolicitudes manipuladorSolicitudes = new ManipuladorSolicitudes();
                        Object[] parametros = (Object[]) Modelo.getInstancia().contarProductosArray();
                        String strJson = manipuladorSolicitudes.generarRespuesta(PlantillasJSON.TipoRespuesta.EJECUTAR_SERVICIO_CONTAR, parametros);
                        System.out.println("Respuesta votar: " + strJson);
                        writer.println(strJson);
                    } else if (tipoServicio.equals("listar")) {
                        System.out.println("entro listar");
                        System.out.println("--servicio listar--");
                        ManipuladorSolicitudes manipuladorSolicitudes = new ManipuladorSolicitudes();
                        Object[] parametros = (Object[]) Modelo.getInstancia().listarBitacorasArray();
                        String strJson = manipuladorSolicitudes.generarRespuesta(PlantillasJSON.TipoRespuesta.EJECUTAR_SERVICIO_LISTAR, parametros);
                        System.out.println("Respuesta votar: " + strJson);
                        writer.println(strJson);
                    } else if (tipoServicio.equals("registrar")) {
                        System.out.println("--servicio registrar--");
                        String arreglo = json.get("parametros").toString();
                        String[] items = arreglo.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");
                        String evento = items[0];
                        String fecha = items[1];
                        //creeando producto a base de parametros 
                        Bitacora bitacora = Modelo.getInstancia().registrarEnBitacora(evento, fecha);
                        ManipuladorSolicitudes manipuladorSolicitudes = new ManipuladorSolicitudes();
                        Object[] parametros = {bitacora.getEvento(), bitacora.getFecha()};
                        String strJson = manipuladorSolicitudes.generarRespuesta(PlantillasJSON.TipoRespuesta.EJECUTAR_SERVICIO_REGISTRAR, parametros);
                        System.out.println("Respuesta votar: " + strJson);
                        writer.println(strJson);

                    } else {
                        System.out.println("El servicio no existe");
                    }

                }
                clientSocket.close();
            } catch (Exception e) {
            }

        }
    }

}
