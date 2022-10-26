/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketsmulticliente.ServidorVotos;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Alexis
 */
public class ManejadorClienteVotos extends Thread{
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
            System.out.println("Cliente y servidor emparejados: " + clientSocket.getLocalPort());
            Scanner escaner = new Scanner(System.in);
            String inputLine = "";

            String text;
            do {
                //aca llega el JSON
                text = reader.readLine();
                if (text != null) {
                    /*
                    
                    */
                      
                    if (text.equals("solicitar")) {
                        System.out.println("Servicios disponibles hola");
                        writer.println("el servicio de solcitar esta disponible");
                    } else {
                        String reversed = new StringBuilder(text).reverse().toString();
                        writer.println("Server: " + reversed);
                        System.out.println(text);
                    }

                }
            } while (!"bye".equals(text));

            clientSocket.close();
        } catch (Exception e) {
        }

    }
}
