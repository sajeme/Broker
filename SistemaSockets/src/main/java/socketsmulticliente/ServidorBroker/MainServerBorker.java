/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketsmulticliente.ServidorBroker;

import ManejadoresJson.ManipuladorSolicitudes;
import ManejadoresJson.PlantillasJSON;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Alexis
 */
public class MainServerBorker {

    ArrayList<JSONObject> listaServicios = new ArrayList<>();

    Scanner entradaStr = new Scanner(System.in);
    private ServerSocket serverSocket;

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        while (true) {
            System.out.println("The server is listening at port: " + serverSocket.getLocalPort());
            ManejadorClienteBroker manejadorClienteBroker = new ManejadorClienteBroker(serverSocket.accept());
            manejadorClienteBroker.start();
        }
    }

    public void stop() {
        try {
            serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(ManejadorClienteBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String addService(Object obj) {

        //System.out.println("llego addService");
        JSONObject json = (JSONObject) obj;
        boolean existeServicio = false;

        if (listaServicios.isEmpty()) {
            listaServicios.add(json);
            //System.out.println("primera un vez aniadiendo servicio");
            String opcServicio = (String) json.get("nombreServicio");
            String respuestaJsonStr = manejarRespuestasRegistroServicios(opcServicio);

            return respuestaJsonStr;
        } else {
            for (int i = 0; i < listaServicios.size(); i++) {
                if (listaServicios.get(i).get("nombreServicio").equals(json.get("nombreServicio"))) {
                    existeServicio = true;
                    //System.out.println("Este servicio ya esta registrado!");
                }
            }
            if (existeServicio == false) {
                listaServicios.add(json);
                String opcServicio = (String) json.get("nombreServicio");
                String respuesta = manejarRespuestasRegistroServicios(opcServicio);
                return respuesta;
            }
        }
        return "{Servicio NO registrado}";
    }

    public String manejarRespuestasRegistroServicios(String opcServicio) {
        //System.out.println("llego manejarRespuestasRegistroServicios");
        ManipuladorSolicitudes manipuladorSolicitudes = new ManipuladorSolicitudes();
        if (opcServicio.equals("votar")) {
            //System.out.println("entro aca");
            String json = manipuladorSolicitudes.generarRespuesta(PlantillasJSON.TipoRespuesta.REGISTRAR_SERVICIO_VOTAR, null);
            //System.out.println("Respuesta de registro votar: " + json);
            //System.out.println("retorno manejarRespuestasRegistroServicios");
            return json;
        } else if (opcServicio.equals("contar")) {
            String json = manipuladorSolicitudes.generarRespuesta(PlantillasJSON.TipoRespuesta.REGISTRAR_SERVICIO_CONTAR, null);
            //System.out.println("Respuesta de registro votar: " + json);
            return json;
        } else if (opcServicio.equals("listar")) {
            String json = manipuladorSolicitudes.generarRespuesta(PlantillasJSON.TipoRespuesta.REGISTRAR_SERVICIO_LISTAR, null);
            //System.out.println("Respuesta de registro votar: " + json);
            return json;
        } else if (opcServicio.equals("registrar")) {
            String json = manipuladorSolicitudes.generarRespuesta(PlantillasJSON.TipoRespuesta.REGISTRAR_SERVICIO_REGISTRAR, null);
            //System.out.println("Respuesta de registro votar: " + json);
            return json;
        }
        return null;
    }

    public void imprimirServicios() {
        for (int i = 0; i < listaServicios.size(); i++) {
            System.out.println("parametros del servicio: " + listaServicios.get(i).get("tipoServicio"));
            String arreglo = listaServicios.get(i).get("parametros").toString();
            String[] items = arreglo.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");
            System.out.println(items[0]);
            System.out.println(Arrays.toString(items));
        }
    }

    public boolean isServiceRegistrer(String nombreServicio) {
        for (JSONObject listaServicio : listaServicios) {
            if (listaServicio.containsValue(nombreServicio)) {
                return true;
            }
        }
        return false;
    }

    public Object[] obtenerServicios(String servicio) {
        if (servicio.equals("") && listaServicios.size() > 0) {
            //System.out.println("entro primer if");
            Object[] object = new Object[listaServicios.size() * 2];
            int contador = 0;
            //System.out.println("tamanio listaServicios" + listaServicios.size());
            for (int i = 0; i < listaServicios.size(); i++) {
                //System.out.println(listaServicios.get(i).get("nombreServicio"));
                //System.out.println(listaServicios.get(i).get("servidor"));
                object[contador] = (String) listaServicios.get(i).get("nombreServicio");

                object[contador + 1] = (String) listaServicios.get(i).get("servidor");

                contador = contador + 2;
            }
            //System.out.println(Arrays.toString(object));
            //System.out.println("retorno object");
            return object;
        } else if (isServiceRegistrer(servicio) && listaServicios.size() > 0) {
            for (int i = 0; i < listaServicios.size(); i++) {
                if (listaServicios.get(i).get("nombreServicio").equals(servicio)) {
                    Object[] object = new Object[2];
                    object[0] = listaServicios.get(i).get("nombreServicio");
                    object[1] = listaServicios.get(i).get("servidor");
                    return object;
                }
            }
        }
        return new Object[]{"{Error al solicitar servicio}"};
    }

    public class ManejadorClienteBroker extends Thread {

        ManipuladorSolicitudes manipuladorSolicitudes = new ManipuladorSolicitudes();
        private Socket clientSocket;
        private PrintWriter writer;
        private BufferedReader reader;

        public ManejadorClienteBroker(Socket clientoSocket) {
            this.clientSocket = clientoSocket;
        }

        public Object parsearParameetros(JSONObject jsonObject) {
            return null;
        }

        public void manejarSolicitudEjecutar(String nombreServicio, String stringJson) throws ParseException {
            if (nombreServicio.equals("votar") && isServiceRegistrer("votar")) {
                System.out.println("--Ejecutando servicio votar--");
                ClienteBroker clienteBroker = new ClienteBroker();
                clienteBroker.startConnection("localhost", 8082);
                String respuestaServidorVotos = clienteBroker.sendMessage(stringJson);
                System.out.println("Solicitud: " + respuestaServidorVotos);
                JSONParser parser1 = new JSONParser();
                JSONObject jsonNuevo1 = (JSONObject) parser1.parse(respuestaServidorVotos);
                String arreglo1 = (String) jsonNuevo1.get("respuestas");
                String[] items1 = arreglo1.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");
                Object[] parametros2 = {items1[0], items1[1]};

                String strJsonRespuesta = manipuladorSolicitudes.generarRespuesta(PlantillasJSON.TipoRespuesta.EJECUTAR_SERVICIO_VOTAR, parametros2);
                System.out.println("Respuesta: " + strJsonRespuesta);
 
                writer.println(strJsonRespuesta);
            } else if (nombreServicio.equals("contar") && isServiceRegistrer("contar")) {
                System.out.println("--Ejecutando servicio contar--");
                //System.out.println("entro contar");
                ClienteBroker clienteBroker = new ClienteBroker();
                clienteBroker.startConnection("localhost", 8082);
                String respuestaServidorVotos = clienteBroker.sendMessage(stringJson);
                System.out.println("Solicitud: " + respuestaServidorVotos);
                //generando "parametros"
                JSONParser parser2 = new JSONParser();
                JSONObject jsonNuevo2 = (JSONObject) parser2.parse(respuestaServidorVotos);
                String arreglo2 = (String) jsonNuevo2.get("respuestas");
                Object[] items2 = arreglo2.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");
                //System.out.println(Arrays.toString(items2));
                Object[] parametros2 = items2;

                String strJsonRespuesta = manipuladorSolicitudes.generarRespuesta(PlantillasJSON.TipoRespuesta.EJECUTAR_SERVICIO_VOTAR, parametros2);
                System.out.println("Respuesta: " + strJsonRespuesta);

                writer.println(strJsonRespuesta);
            } else if (nombreServicio.equals("listar") && isServiceRegistrer("listar")) {
                System.out.println("--Ejecutando servicio listar--");
                //System.out.println("entro listar");
                ClienteBroker clienteBroker = new ClienteBroker();
                clienteBroker.startConnection("localhost", 8082);
                String respuestaServidorVotos = clienteBroker.sendMessage(stringJson);
                System.out.println("Solicitud: " + respuestaServidorVotos);
                //clienteBroker.stopConnection();

                //generando "parametros"
                JSONParser parser3 = new JSONParser();
                JSONObject jsonNuevo3 = (JSONObject) parser3.parse(respuestaServidorVotos);
                String arreglo3 = jsonNuevo3.get("respuestas").toString();
                Object[] items3 = arreglo3.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");
                Object[] parametros3 = items3;

                String strJsonRespuesta = manipuladorSolicitudes.generarRespuesta(PlantillasJSON.TipoRespuesta.EJECUTAR_SERVICIO_LISTAR, parametros3);
                System.out.println("Respuesta: " + strJsonRespuesta);

                writer.println(strJsonRespuesta);
            } else if (nombreServicio.equals("registrar") && isServiceRegistrer("registrar")) {
                System.out.println("--ejecutando servicio registrar--");
                //System.out.println("entro registrar");
                ClienteBroker clienteBroker = new ClienteBroker();
                clienteBroker.startConnection("localhost", 8082);
                String respuestaServidorVotos = clienteBroker.sendMessage(stringJson);
                System.out.println("Solicitud: " + respuestaServidorVotos);

                //generando "parametros"
                JSONParser parser4 = new JSONParser();
                JSONObject jsonNuevo4 = (JSONObject) parser4.parse(respuestaServidorVotos);
                String arreglo1 = (String) jsonNuevo4.get("respuestas");
                String[] objetoArray = arreglo1.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");
                Object[] parametros2 = {objetoArray[0], objetoArray[1]};
                String strJsonRespuesta = manipuladorSolicitudes.generarRespuesta(PlantillasJSON.TipoRespuesta.EJECUTAR_SERVICIO_REGISTRAR, parametros2);
                System.out.println("Respuesta: " + strJsonRespuesta);
                writer.println(strJsonRespuesta);
            }
            writer.println("{Servicio NO disponible}");
        }

        @Override
        public void run() {
            try {
                System.out.println("");
                System.out.println("");
                reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                writer = new PrintWriter(clientSocket.getOutputStream(), true);
                System.out.println("Cliente y servidor emparejados: " + clientSocket.getLocalPort());
                String text;
                text = reader.readLine();
                if (text != null) {
                    String stringJson = text;
                    JSONParser parser = new JSONParser();
                    JSONObject json = (JSONObject) parser.parse(stringJson);
                    String tipoServicio = (String) json.get("tipoServicio");

                    if (tipoServicio.equals("registrar")) {
                        System.out.println("--Servicio registrar servicio--");
                        String respuesta = addService(json);
                        System.out.println("Respuesta: " + respuesta);
                        writer.println(respuesta);
                    } else if (tipoServicio.equals("ejecutar")) {
                        System.out.println("--Servicio ejecutar servicio--");
                        String nombreServicio = (String) json.get("nombreServicio");
                        manejarSolicitudEjecutar(nombreServicio, stringJson);

                        //Servicio listar, aqui devolvemos lo servicios registrados en el broker
                    } else if (tipoServicio.equals("listar")) {
                        String arreglo1 = (String) json.get("parametros");
                        //System.out.println("parametros: " + json.get("parametros"));
                        String[] objetoArray = arreglo1.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");
                        //System.out.println(objetoArray[0]);
                        Object[] parametros = obtenerServicios(objetoArray[0] + "");
                        String strJsonRespuesta = manipuladorSolicitudes.generarRespuesta(PlantillasJSON.TipoRespuesta.LISTAR_SERVICIOS_BROKER, parametros);
                        System.out.println("Respuesta: " + strJsonRespuesta);
                        writer.println(strJsonRespuesta);

                    } else {
                        System.out.println("Error en algun servicio");
                    }
                }
                clientSocket.close();
            } catch (Exception e) {
            }

        }
    }

    public static void main(String[] args) {
        try {
            Scanner strEntrada = new Scanner(System.in);
            Scanner intEntrada = new Scanner(System.in);

            String msj = "";
            int port = 0;

            MainServerBorker serverBroker = new MainServerBorker();
            System.out.println("Ingrese el puerto del servidor");
            port = intEntrada.nextInt();
            serverBroker.start(port);
        } catch (IOException ex) {
            Logger.getLogger(MainServerBorker.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
