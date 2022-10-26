/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManejadoresJson;

/**
 *
 * @author Alexis
 */
public class PlantillasJSON {

    private static final String servidorRegistrar = "localhost";
    private static final String puertoServidorRegistrar = "8083";

    private Object plantillaSolicitud_REGISTRAR_SERVICIO[][] = {
        {"servidor", servidorRegistrar},
        {"puerto", puertoServidorRegistrar},
        {"nombreServicio", null},
        {"parametros", null}
    };

    private Object plantillaSolicitud_EJECUTAR_SERVICIO[][] = {
        {"nombreServicio", null},
        {"parametros", null}
    };

    private Object plantillaSolicitud_LISTAR_SERVICIO[][] = {
        {"parametros", null}
    };

    private Object plantillaRespuesta_LISTAR_SERVICIO[][] = {
        {"parametros", null}
    };

    private Object plantillaRespuesta[][] = {
        {"nombreServicio", null},
        {"respuestas", null}
    };

    public Object[][] getPlantilla(TipoPlantilla tipoPlantilla) {
        if (tipoPlantilla.equals(TipoPlantilla.REGISTRAR)) {
            return plantillaSolicitud_REGISTRAR_SERVICIO;
        } else if (tipoPlantilla.equals(TipoPlantilla.EJECUTAR)) {
            return plantillaSolicitud_EJECUTAR_SERVICIO;
        } else if (tipoPlantilla.equals(TipoPlantilla.RESPUESTA)) {
            return plantillaRespuesta;
        } else if (tipoPlantilla.equals(TipoPlantilla.LISTAR)) {
            return plantillaSolicitud_LISTAR_SERVICIO;
        }else if (tipoPlantilla.equals(TipoPlantilla.RESPUESTA_LISTAR)) {
            return plantillaRespuesta_LISTAR_SERVICIO;
        }
        return null;
    }

    //
    /*
     public static Object plantillaSolicitud_SERVICIO [][] = { 
        {"nombreServicio", null},
        {"parametros", null}
    };
    
    public static Object plantillaSolicitud_LISTAR_SERVICIOS [][] = { 
        {"parametros", null}
    };
     */
    public enum TipoPlantilla {
        REGISTRAR,
        EJECUTAR,
        RESPUESTA,
        LISTAR,
        RESPUESTA_LISTAR
    }

    public enum TipoSolicitud {

        REGISTRAR_SERVICIO_VOTAR, //*
        REGISTRAR_SERVICIO_CONTAR,
        REGISTRAR_SERVICIO_REGISTRAR, //*
        REGISTRAR_SERVICIO_LISTAR,
        EJECUTAR_SERVICIO_VOTAR, //*
        EJECUTAR_SERVICIO_CONTAR,
        EJECUTAR_SERVICIO_REGISTRAR,
        EJECUTAR_SERVICIO_LISTAR,
        LISTAR_SERVICIOS_BROKER
    }

    public enum TipoRespuesta {

        REGISTRAR_SERVICIO_VOTAR, //*
        REGISTRAR_SERVICIO_CONTAR,
        REGISTRAR_SERVICIO_REGISTRAR, //*
        REGISTRAR_SERVICIO_LISTAR,
        EJECUTAR_SERVICIO_VOTAR, //*
        EJECUTAR_SERVICIO_CONTAR,
        EJECUTAR_SERVICIO_REGISTRAR,
        EJECUTAR_SERVICIO_LISTAR,
        LISTAR_SERVICIOS_BROKER
    }

}
