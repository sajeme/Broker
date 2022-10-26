/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketsmulticliente.ServidorVotos;

/**
 *
 * @author Alexis
 */
public class MainServidorVotos {
    
    public static void main(String[] args) {
        FlujoServer flujoServer = new FlujoServer();
        
        flujoServer.iniciarClienteServerVotos();
        flujoServer.iniciarServidorVotos();
        
    }
    
}
