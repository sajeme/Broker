/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketsmulticliente.ServidorVotos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Alexis
 */
public class ClienteServer {

    private Socket clientSocket;
    private PrintWriter writer;
    private BufferedReader reader;

    public void startConnection(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            writer = new PrintWriter(clientSocket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException ex) {
            //Logger.getLogger(ClienteServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String sendMessage(String msg) {
        try {
            writer.println(msg);
            //System.out.println("ClienteServer: sendMessage");
            String resp = reader.readLine();
            //System.out.println("ClienteServer: sendMessage");
            return resp;
        } catch (IOException ex) {
            //Logger.getLogger(ClienteServer.class.getName()).log(Level.SEVERE, null, ex);
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
            //Logger.getLogger(ClienteServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
