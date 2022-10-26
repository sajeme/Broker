/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketsmulticliente.ServidorBroker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Alexis
 */
public class ClienteBroker {
    private Socket clientSocket;
    private PrintWriter writer;
    private BufferedReader reader;

    public void startConnection(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            writer = new PrintWriter(clientSocket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException ex) {
        }
    }

    public String sendMessage(String msg) {
        try {
            writer.println(msg);
            String resp = reader.readLine();
            return resp;
        } catch (IOException ex) {
        }
        return null;
    }

    public void stopConnection() {
        try {
            reader.close();
            writer.close();
            clientSocket.close();
        } catch (IOException ex) {
        }
    }
}
