/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketsmulticliente.ServidorVotos;

import ManejadoresJson.ManipuladorSolicitudes;
import ManejadoresJson.PlantillasJSON;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Alexis
 */
public class FlujoServer {

    public void iniciarClienteServerVotos() {

        //ManipuladorSolicitudes manipuladorSolicitudes = new ManipuladorSolicitudes();
        Scanner intEntrada = new Scanner(System.in);
        Scanner mensaje = new Scanner(System.in);

        System.out.println("");
        System.out.println("---------------------------------");
        System.out.println("--Cliente del Servidor de votos--");
        System.out.println("---------------------------------");
        System.out.println("");

        //ClienteServer client = new ClienteServer();
        int port = 0;
        System.out.println("Ingrese el puerto");
        port = intEntrada.nextInt();

        //client.startConnection("localhost", port);

        String msj = "";
        String opcNum = "";

        do {
            System.out.println("");
            System.out.println("");
            ClienteServer client = new ClienteServer();
            client.startConnection("localhost", port);
            //this.ClearConsole();
            System.out.println("Ingrese un numero para registrar un servicio al broker");
            System.out.println("1. votar");
            System.out.println("2. contar");
            System.out.println("3. listar");
            System.out.println("4. registrar");
            System.out.println("0. Terminar registro");
            opcNum = "";
            opcNum = mensaje.nextLine();

            //votar
            if (opcNum.equals("1")) {
                ManipuladorSolicitudes manipuladorSolicitudes = new ManipuladorSolicitudes();
                String json = manipuladorSolicitudes.generarSolicitud(PlantillasJSON.TipoSolicitud.REGISTRAR_SERVICIO_VOTAR, null);
                System.out.println(json);
                String respuesta = client.sendMessage(json);
                System.out.println(respuesta);
                client.stopConnection();
                //contar
            } else if (opcNum.equals("2")) {
                //Object []parametros = {"0"}; si se usa en ejecutar
                ManipuladorSolicitudes manipuladorSolicitudes = new ManipuladorSolicitudes();
                String json = manipuladorSolicitudes.generarSolicitud(PlantillasJSON.TipoSolicitud.REGISTRAR_SERVICIO_CONTAR, null);
                System.out.println(json);
                String respuesta = client.sendMessage(json);
                System.out.println(respuesta);
                client.stopConnection();
            } else if (opcNum.equals("3")) {
                ManipuladorSolicitudes manipuladorSolicitudes = new ManipuladorSolicitudes();
                String json = manipuladorSolicitudes.generarSolicitud(PlantillasJSON.TipoSolicitud.REGISTRAR_SERVICIO_LISTAR, null);
                System.out.println(json);
                String respuesta = client.sendMessage(json);
                System.out.println(respuesta);
                client.stopConnection();
            } else if (opcNum.equals("4")) {
                ManipuladorSolicitudes manipuladorSolicitudes = new ManipuladorSolicitudes();
                String json = manipuladorSolicitudes.generarSolicitud(PlantillasJSON.TipoSolicitud.REGISTRAR_SERVICIO_REGISTRAR, null);
                System.out.println(json);
                String respuesta = client.sendMessage(json);
                System.out.println(respuesta);
                client.stopConnection();
            } else if (opcNum.equals("0")) {
                client.stopConnection();
                break;
            }

        } while (!(opcNum.equals("0")));

    }

    public void iniciarServidorVotos() {

        try {
            Scanner entradaInt = new Scanner(System.in);

            System.out.println("");
            System.out.println("---------------------------------");
            System.out.println("--------Servidor de votos--------");
            System.out.println("---------------------------------");
            System.out.println("");

            //System.out.println("Ingrese el puerto del servidor");
            //int puerto = entradaInt.nextInt();
            
            System.err.println("Puerto del servidor de votos: 8082");
            ServerVotos serverVotos = new ServerVotos();
            serverVotos.start(8082);

        } catch (IOException ex) {
            Logger.getLogger(FlujoServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
