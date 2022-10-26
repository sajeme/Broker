<?php

/*
vista pagina feeds
contiene la plantilla para representar la pagina feeds

@package
@author
@version 
@date
*/
?>
<html>
<head>
    <title><?php echo $aplicacion; ?></title>
</head>
<body>
    <h1><?=$titulo ?> </h1>
    <p> Resultados del archivo RSS </p>
    <?php echo $feeds; ?>
</body>