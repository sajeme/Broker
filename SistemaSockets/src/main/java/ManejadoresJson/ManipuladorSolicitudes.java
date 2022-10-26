/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManejadoresJson;

import ManejadoresJson.PlantillasJSON.TipoRespuesta;
import ManejadoresJson.PlantillasJSON.TipoSolicitud;
import java.util.Arrays;
import org.json.simple.JSONObject;

/**
 *
 * @author Alexis
 */
public class ManipuladorSolicitudes {

    PlantillasJSON plantillasJSON = new PlantillasJSON();

    public void inicializarSolicitud(Object[][] plantilla, Object[] parametros, JSONObject jsonObject) {

        int contadorParametro = 0;

        for (int i = 0; i < plantilla.length; i++) {
            //System.out.println("i:" + i);
            for (int j = 0; j < 2; j++) {

                //System.out.println("j:" + j);

                //System.out.println("parametros: " + parametros[contadorParametro]);
                //System.out.println("platilla: " + plantilla[i][j]);

                if (plantilla[i][j] == null) {
                    //System.out.println(i);
                    //System.out.println("j: [" + (j) + "] " + parametros[contadorParametro]);
                    plantilla[i][j] = parametros[contadorParametro];
                    contadorParametro++;
                }
            }
        }

        for (int i = 0; i < plantilla.length; i++) {

            jsonObject.put((String) plantilla[i][0], plantilla[i][1]);

            if (plantilla[i][0] == "parametros") {
                //List<Object> arregloString = Arrays.asList(parametros).toArray(new String[parametros.length]);
                Object[] arregloObjetos = (Object[]) parametros[contadorParametro - 1];
                String[] stringArray = Arrays.copyOf(arregloObjetos, arregloObjetos.length, String[].class);
                //System.out.println(Arrays.toString(stringArray));
                jsonObject.put("parametros", "" + Arrays.toString(stringArray));
            }
        }
    }

    public void inicializarRespuesta(Object[][] plantilla, Object[] parametros, JSONObject jsonObject) {
        int contadorParametro = 0;
        for (int i = 0; i < plantilla.length; i++) {
            //System.out.println("i:" + i);
            for (int j = 0; j < 2; j++) {

                //System.out.println("j:" + j);
                if (plantilla[i][j] == null) {
                    //System.out.println(i);
                    //System.out.println("j: [" + (j) + "] " + parametros[contadorParametro]);
                    plantilla[i][j] = parametros[contadorParametro];
                    contadorParametro++;
                }
            }
        }

        for (int i = 0; i < plantilla.length; i++) {

            jsonObject.put((String) plantilla[i][0], plantilla[i][1]);

            if (plantilla[i][0] == "respuestas") {
                //List<Object> arregloString = Arrays.asList(parametros).toArray(new String[parametros.length]);
                Object[] arregloObjetos = (Object[]) parametros[contadorParametro - 1];
                String[] stringArray = Arrays.copyOf(arregloObjetos, arregloObjetos.length, String[].class);
                //System.out.println(Arrays.toString(stringArray));
                jsonObject.put("respuestas", "" + Arrays.toString(stringArray));
            }

        }

    }

    //Esto es lo que se ejecuta en el flujo server
    //hacer otra funcion para generar respuestas
    public String generarSolicitud(TipoSolicitud tipo, Object arregloParametros) {
        JSONObject solicitud = crearObjetoSolicitud(tipo, arregloParametros);
        return pasearJsonToString(solicitud);
    }

    public JSONObject crearObjetoSolicitud(TipoSolicitud tipo, Object arregloParametros) {

        JSONObject objJson = new JSONObject();

        switch (tipo) {
            case REGISTRAR_SERVICIO_VOTAR:
                objJson.put("tipoServicio", "registrar");
                objJson.put("variables", 4);

                Object[][] plantillasSolicitud_votar = plantillasJSON.getPlantilla(PlantillasJSON.TipoPlantilla.REGISTRAR);
                Object[] parametros_registrar_votar = {"votar", new Object[]{"1"}};
                //{"servicio",Object[]{respuesta1,respuesta2}}

                inicializarSolicitud(plantillasSolicitud_votar, parametros_registrar_votar, objJson);
                break;
            case REGISTRAR_SERVICIO_CONTAR:
                objJson.put("tipoServicio", "registrar");
                objJson.put("variables", 4);

                Object[][] plantillasSolicitud_registrar_contar = plantillasJSON.getPlantilla(PlantillasJSON.TipoPlantilla.REGISTRAR);

                Object[] parametros_contar = {"contar", new Object[]{"0"}};

                inicializarSolicitud(plantillasSolicitud_registrar_contar, parametros_contar, objJson);
                break;
            case REGISTRAR_SERVICIO_LISTAR:
                objJson.put("tipoServicio", "registrar");
                objJson.put("variables", 4);

                Object[][] plantillasSolicitud_registrar_listar = plantillasJSON.getPlantilla(PlantillasJSON.TipoPlantilla.REGISTRAR);
                Object[] parametros_listar = {"listar", new Object[]{"0"}};

                inicializarSolicitud(plantillasSolicitud_registrar_listar, parametros_listar, objJson);
                break;
            case REGISTRAR_SERVICIO_REGISTRAR:
                objJson.put("tipoServicio", "registrar");
                objJson.put("variables", 4);

                Object[][] plantillasSolicitud_registrar_registrar = plantillasJSON.getPlantilla(PlantillasJSON.TipoPlantilla.REGISTRAR);
                Object[] parametros_registrar = {"registrar", new Object[]{"2"}};

                inicializarSolicitud(plantillasSolicitud_registrar_registrar, parametros_registrar, objJson);
                break;

            //---------------------------EjECUTAR----------------------------------         
            case EJECUTAR_SERVICIO_VOTAR:
                objJson.put("tipoServicio", "ejecutar");
                objJson.put("variables", 2);

                Object[][] plantillasSolicitud_ejectar_votar = plantillasJSON.getPlantilla(PlantillasJSON.TipoPlantilla.EJECUTAR);
                //aqui va lo que voto el usuario

                Object[] parametros_ejectar_votar = {"votar", arregloParametros};

                inicializarSolicitud(plantillasSolicitud_ejectar_votar, parametros_ejectar_votar, objJson);
                break;
            case EJECUTAR_SERVICIO_CONTAR:
                objJson.put("tipoServicio", "ejecutar");
                objJson.put("variables", 2);

                Object[][] plantillasSolicitud_ejectar_contar = plantillasJSON.getPlantilla(PlantillasJSON.TipoPlantilla.EJECUTAR);
                //aqui va lo que voto el usuario
                Object[] parametros_ejectar_contar = {"contar", new Object[]{"0"}};

                inicializarSolicitud(plantillasSolicitud_ejectar_contar, parametros_ejectar_contar, objJson);
                break;
            case EJECUTAR_SERVICIO_LISTAR:
                objJson.put("tipoServicio", "ejecutar");
                objJson.put("variables", 2);

                Object[][] plantillasSolicitud_ejectar_listar = plantillasJSON.getPlantilla(PlantillasJSON.TipoPlantilla.EJECUTAR);
                Object[] parametros_ejectar_listar = {"listar", new Object[]{"0"}};

                inicializarSolicitud(plantillasSolicitud_ejectar_listar, parametros_ejectar_listar, objJson);
                break;
            case EJECUTAR_SERVICIO_REGISTRAR:
                objJson.put("tipoServicio", "ejecutar");
                objJson.put("variables", 2);

                Object[][] plantillasSolicitud_ejectar_registrar = plantillasJSON.getPlantilla(PlantillasJSON.TipoPlantilla.EJECUTAR);
                Object[] parametros_ejectar_registrar = {"registrar", arregloParametros};

                inicializarSolicitud(plantillasSolicitud_ejectar_registrar, parametros_ejectar_registrar, objJson);
                break;

            //ACA ME QUEDE
            case LISTAR_SERVICIOS_BROKER:
                objJson.put("tipoServicio", "listar");
                objJson.put("variables", 1);

                Object[][] plantillasSolicitud_LISTAR_SERVICIOS = plantillasJSON.getPlantilla(PlantillasJSON.TipoPlantilla.LISTAR);
                Object[] parametros_LISTAR_SERVICIOS = {arregloParametros};

                inicializarSolicitud(plantillasSolicitud_LISTAR_SERVICIOS, parametros_LISTAR_SERVICIOS, objJson);
                break;
        }   

        return objJson;
    }

    //Respuestas 
    public String generarRespuesta(TipoRespuesta tipo, Object arregloParametros) {
        JSONObject solicitud = crearObjetoRespuesta(tipo, arregloParametros);
        return pasearJsonToString(solicitud);
    }

    public JSONObject crearObjetoRespuesta(TipoRespuesta tipo, Object arregloParametros) {

        JSONObject objJson = new JSONObject();

        switch (tipo) {
            case REGISTRAR_SERVICIO_VOTAR:
                objJson.put("tipoServicio", "registrar");
                objJson.put("variables", 1);

                Object[][] plantillasRespuesta_votar = plantillasJSON.getPlantilla(PlantillasJSON.TipoPlantilla.RESPUESTA);
                Object[] parametros_votar = {"votar", new Object[]{"1"}};
                //{"servicio",Object[]{respuesta1,respuesta2}}

                inicializarRespuesta(plantillasRespuesta_votar, parametros_votar, objJson);
                break;
            case REGISTRAR_SERVICIO_CONTAR:
                objJson.put("tipoServicio", "registrar");
                objJson.put("variables", 1);

                Object[][] plantillasRespuesta_contar = plantillasJSON.getPlantilla(PlantillasJSON.TipoPlantilla.RESPUESTA);

                Object[] parametros_contar = {"contar", new Object[]{"1"}};

                inicializarRespuesta(plantillasRespuesta_contar, parametros_contar, objJson);
                break;
            case REGISTRAR_SERVICIO_LISTAR:
                objJson.put("tipoServicio", "registrar");
                objJson.put("variables", 1);

                Object[][] plantillasRespuesta_listar = plantillasJSON.getPlantilla(PlantillasJSON.TipoPlantilla.RESPUESTA);
                Object[] parametros_listar = {"listar", new Object[]{"1"}};

                inicializarRespuesta(plantillasRespuesta_listar, parametros_listar, objJson);
                break;
            case REGISTRAR_SERVICIO_REGISTRAR:
                objJson.put("tipoServicio", "registrar");
                objJson.put("variables", 1);

                Object[][] plantillasRespuesta_registrar = plantillasJSON.getPlantilla(PlantillasJSON.TipoPlantilla.RESPUESTA);
                Object[] parametros_registrar = {"registrar", new Object[]{"1"}};

                inicializarRespuesta(plantillasRespuesta_registrar, parametros_registrar, objJson);
                break;

            //EjECUTAR   
            case EJECUTAR_SERVICIO_VOTAR:
                objJson.put("tipoServicio", "ejecutar");
                objJson.put("variables", 2);

                Object[][] plantillasSolicitud_ejectar_votar = plantillasJSON.getPlantilla(PlantillasJSON.TipoPlantilla.RESPUESTA);
                Object[] parametros_ejectar_votar = {"votar", arregloParametros};
                inicializarRespuesta(plantillasSolicitud_ejectar_votar, parametros_ejectar_votar, objJson);
                break;
            case EJECUTAR_SERVICIO_CONTAR:
                objJson.put("tipoServicio", "ejecutar");
                objJson.put("variables", 2);

                Object[][] plantillasSolicitud_ejectar_contar = plantillasJSON.getPlantilla(PlantillasJSON.TipoPlantilla.RESPUESTA);
                Object[] parametros_ejectar_contar = {"contar", arregloParametros};
                inicializarRespuesta(plantillasSolicitud_ejectar_contar, parametros_ejectar_contar, objJson);
                break;
            case EJECUTAR_SERVICIO_LISTAR:
                objJson.put("tipoServicio", "ejecutar");
                objJson.put("variables", 2);

                Object[][] plantillasSolicitud_ejectar_listar = plantillasJSON.getPlantilla(PlantillasJSON.TipoPlantilla.RESPUESTA);
                Object[] parametros_ejectar_listar = {"listar", arregloParametros};
                inicializarRespuesta(plantillasSolicitud_ejectar_listar, parametros_ejectar_listar, objJson);
                break;
            case EJECUTAR_SERVICIO_REGISTRAR:
                objJson.put("tipoServicio", "ejecutar");
                objJson.put("variables", 2);

                Object[][] plantillasSolicitud_ejectar_registrar = plantillasJSON.getPlantilla(PlantillasJSON.TipoPlantilla.RESPUESTA);
                Object[] parametros_ejectar_registrar = {"registrar", arregloParametros};
                inicializarRespuesta(plantillasSolicitud_ejectar_registrar, parametros_ejectar_registrar, objJson);
                break;

                case LISTAR_SERVICIOS_BROKER:
                objJson.put("tipoServicio", "listar");
                objJson.put("variables", 1);
                Object[][] plantillasSolicitud_LISTAR_SERVICIOS_BROKER = plantillasJSON.getPlantilla(PlantillasJSON.TipoPlantilla.RESPUESTA_LISTAR);
                Object[] parametros_LISTAR_SERVICIOS_BROKER  = {arregloParametros};
                inicializarSolicitud(plantillasSolicitud_LISTAR_SERVICIOS_BROKER, parametros_LISTAR_SERVICIOS_BROKER, objJson);
                break;
        }

        return objJson;
    }

    public String pasearJsonToString(JSONObject jSONObject) {
        return jSONObject.toJSONString();

    }

}
