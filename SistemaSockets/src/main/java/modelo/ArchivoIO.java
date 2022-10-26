package modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ArchivoIO {

    private static ArchivoIO instancia;

    private ArchivoIO() {
    }

    public static ArchivoIO getInstancia() {
        if (instancia == null) {
            instancia = new ArchivoIO();
        }
        return instancia;
    }

    public ArrayList<String> leerArchivo(String direccionArchivo) {
        ArrayList<String> retorno = new ArrayList<String>();
        try {
            FileReader fileReader = new FileReader(direccionArchivo);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String lineaLeida;
            while ((lineaLeida = bufferedReader.readLine()) != null) {
                retorno.add(lineaLeida);
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return retorno;
    }

    public void escribirArchivo(String direccionArchivo, String datos) {
        try {
            FileWriter fileWriter = new FileWriter(direccionArchivo, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(datos);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File crearArchivo(String direccion) {
        File archivoNuevo = new File(direccion);

        try {
            // A partir del objeto File creamos el fichero físicamente
            if (archivoNuevo.createNewFile()) {
                System.out.println("El fichero se ha creado correctamente");
            } else {
                System.out.println("No ha podido ser creado el fichero");
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return archivoNuevo;
    }

    public void limpiarDirectorio(String direccion) {
        File directorio = new File(direccion);

        if (!(directorio == null)) {
            for (File archivo : directorio.listFiles()) {
                if (archivo.isDirectory()) {
                    limpiarDirectorio(archivo.getPath());
                }
                archivo.delete();
            }
        }

    }

    public void limpiarArchivo(String direccion) {
        try {
            File archivo = new File(direccion);
            PrintWriter pWriter = new PrintWriter(archivo);
            pWriter.print("");
            pWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
