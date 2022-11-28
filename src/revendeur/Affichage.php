<?php
    include_once "Commande.php"
?>
<!DOCTYPE html>
<html lang="fr">
    <head>
        <title>Affichage de PHP</title>
        <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
        <meta name="description" content="Requête HTTP en PHP"/>
        <meta name="author" content="JUILLIARD Quentin & COGNE Romic"/>
    </head>
    <body>
        <h1>Affichage PHP</h1>
        
<?php

    if (isset($_POST["Energie"]) && $_POST["Energie"]!= "" && isset($_POST["extraction"]) && $_POST["extraction"]  != "" && isset($_POST["quantite"]) && $_POST["quantite"]  != "") {

        //Affichage de l'objet commande
        echo "<br><br><br>Objet commande créé :<br>" ;
        $commande = new Commande(
                                    1,
                                    -1,
                                    -1,
                                    -1,
                                    -1,
                                    $_POST["Energie"],
                                    $_POST["extraction"],
                                    $_POST["quantite"],
                                    -1,
                                    -1
                                    -1,
                                );
        echo $commande;
        
        $json_encode_commande = json_encode($commande);
        echo "<br/> <br/>";
        echo $json_encode_commande;

        $json_fromJson_commande = Commande::fromJson(json_decode($json_encode_commande, true));
        echo "<br/> <br/>";
        echo $json_fromJson_commande;

        $json_decode_commande = json_decode($json_fromJson_commande, true);
        echo "<br/> <br/>";
        echo $json_decode_commande;
    }
    else 
    {
        echo "Le TARE n'a pas accepté la commande.";
    }

?>        
    </body>
</html>