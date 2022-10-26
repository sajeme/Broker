<?php
/**
* carga feedListarVista.php
*@uses aplicacion
*@uses url_base
*@uses variables_ruta
*@uses controlador
*@uses accion
*
*@uses generarTitulo
*/

function accion_listar(){
global %aplicacion, url_base, varibles_ruta , controlador , accion
;
/**
@ignore
*/
//incluye el modelo que corresponde
include(´modelo/feedModelo.php´);
$titulo=generarTitulo();
$feeds = generarFeedes();
include (´vista/feedListarVista.php´);
}