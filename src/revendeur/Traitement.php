<?php
    include "SuiviCommande.php"
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

        echo $_POST["Energie"];
        echo "<br>";
        echo $_POST["extraction"];
        echo "<br>";
        echo $_POST["quantite"];
        echo "<br>";

        //Affichage de l'objet commande
        echo "<br><br><br>Objet commande créé :<br>" ;
        $commande = new SuiviCommande(1, -1, -1, -1, -1, $_POST["Energie"], $_POST["extraction"], $_POST["quantite"], -1, -1, -1);
        echo $commande;
        
        $json_encode_commande = json_encode($commande);
        echo "<br/> <br/>";
        echo $json_encode_commande;

        $json_fromJson_commande = Commande::fromJson(json_decode($json_encode_commande, true));
        echo "<br/> <br/>";
        echo $json_fromJson_commande;

        $jsonreponse = $json_fromJson_commande->envoieSocket();

        $json_decode_commande = json_decode($jsonreponse, true);
        echo "<br/> <br/>";
        echo $json_decode_commande;

        echo "<br/> <br/>";
        
                // affiche un tableau avec les informations de l'utilisateur
                echo '<table class="table table-bordered">'; 
                echo '<tr>';    
                        echo '<td>Id client  :</td>';
                        echo '<td>'.$json_decode_commande->getIdClient().'</td>';         
                    echo '</tr>';
                    echo '<tr>';    
                        echo '<td>Id revendeur  :</td>';
                        echo '<td>'.$json_decode_commande->getIdRevendeur().'</td>';
                    echo '</tr>';
                    echo '<tr>';
                        echo '<td>Id Tare  :</td>';
                        echo '<td>'.$json_decode_commande->getIdTare().'</td>';
                    echo '</tr>';
                    echo '<tr>';
                        echo '<td>Numero De Commande :</td>';
                        echo '<td>'.$json_decode_commande->getNumeroDeCommande().'</td>';
                    echo '</tr>';
                    echo '<tr>';
                        echo '<td>Numero de lot :</td>';
                        echo '<td>'.$json_decode_commande->getNumerodDeLot(). '</td>';
                    echo '</tr>';
                    echo '<tr>';
                        echo ' <td> Type Energie : </td>';
                        echo '<td>'.$json_decode_commande->getTypeEnergie().'</td>';
                    echo '</tr>';
                    echo '<tr>';
                        echo'<td> Mode Extraction : </td>';
                        echo '<td>'.$json_decode_commande->getModeExtraction().'</td>';
                    echo '</tr>';
                    echo '<tr>';
                        echo '<td> Mode Extraction : </td>';
                        echo '<td>'.$json_decode_commande->getModeQuantite().'</td>';
                    echo '</tr>';
                    echo '<tr>';
                        echo '<td> Quantite Envoyer : </td>';
                        echo '<td>'.$json_decode_commande->getQuantiteEnvoyer().'</td>';
                    echo '</tr>';
                    echo '<tr>';
                        echo '<td> Quantite Envoyer : </td>';
                        echo '<td>'.$json_decode_commande->getQuantiteLote().'</td>';
                    echo '</tr>';
                    echo '<tr>';
                        echo '<td> Prix Unites : </td>';
                        echo '<td>'.$json_decode_commande->getPrixUnites().'</td>';
                    echo '</tr>';
                    echo '<tr>';
                        echo '<td> Prix Total : </td>';
                        echo '<td>'.$json_decode_commande->getPrixTotal().'</td>';
                    echo '</tr>';
                        
                echo '</table>';                        
            
    }


?>        
    </body>
</html>