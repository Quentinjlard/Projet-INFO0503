<?php
    include "SuiviCommande.php";

    if (isset($_POST["Energie"]) && $_POST["Energie"]!= "" && isset($_POST["extraction"]) && $_POST["extraction"]  != "" && isset($_POST["quantite"]) && $_POST["quantite"]  != "") 
    {

        // echo $_POST["Energie"];
        // echo "<br>";
        // echo $_POST["extraction"];
        // echo "<br>";
        // echo $_POST["quantite"];
        // echo "<br> ";

        //Affichage de l'objet commande
        // echo "<br><br><br>Objet commande créé :<br>" ;
        $commande = new SuiviCommande(1, 0, 0, 1, 0, $_POST["Energie"], $_POST["extraction"], $_POST["quantite"], 0, 0, 0);
        // var_dump($commande);
        
        // echo "<br><br><br> SUIVI Commande => JSON :<br>";
        $json_encode_commande = json_encode($commande);
        // echo "<br/> <br/>";
        // echo $json_encode_commande;

        // echo "<br><br><br>Envoie de la socket :<br>";
        $jsonreponse = $commande->envoieSocket();
        // echo "<br/> <br/>";
        // echo $jsonreponse;

        // echo "<br><br><br> JSON => SUIVI Commande :<br>";
        $json_fromJson_commande = SuiviCommande::fromJson(json_decode($jsonreponse, true));
        // echo "<br/> <br/>";
        // echo $json_fromJson_commande;

    }
?>

<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="UTF-8">
    <title>Information de la commande d'énergie</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-iYQeCzEYFbKjA/T2uDLTpkwGzCiq6soy8tYaI1GyVh/UjpbCx/TYkiZhlZB6+fzT" crossorigin="anonymous">
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <meta name="description" content="Requête HTTP en PHP"/>
    <meta name="author" content="JUILLIARD Quentin & COGNE Romic"/>
</head>
<body>
    
<div class="container contenir">
<header>
    <br><h1 class="text-center">Réponse de votre demande d'achat energie</h1>
</header>
<br>

    <?php
        if($json_fromJson_commande->getNumerodDeLot() == -1)
        {
            echo '<div class="alert alert-danger text-center" role="alert">';
                echo 'Commande non fourni!';
            echo '</div>';
        }
        else
        {
            echo '<div class="alert alert-success text-center" role="alert">';
                echo 'Commande fourni!';
            echo '</div>';
        }
        echo "<br/>";
    ?>
    
    <div class="" >
        <p>Information surt votre commande : </p>      
           <?php 
                
                echo "<br/>";
        
                // affiche un tableau avec les informations de l'utilisateur
                echo '<table class="table table-bordered">'; 
                echo '<tr>';    
                        echo '<td>Id client  :</td>';
                        echo '<td>'.$json_fromJson_commande->getIdClient().'</td>';         
                    echo '</tr>';
                    echo '<tr>';    
                        echo '<td>Id revendeur  :</td>';
                        echo '<td>'.$json_fromJson_commande->getIdRevendeur().'</td>';
                    echo '</tr>';
                    echo '<tr>';
                        echo '<td>Id Tare  :</td>';
                        echo '<td>'.$json_fromJson_commande->getIdTare().'</td>';
                    echo '</tr>';
                    echo '<tr>';
                        echo '<td>Numero De Commande :</td>';
                        echo '<td>'.$json_fromJson_commande->getNumeroDeCommande().'</td>';
                    echo '</tr>';
                    echo '<tr>';
                        echo '<td>Numero de lot :</td>';
                        echo '<td>'.$json_fromJson_commande->getNumerodDeLot(). '</td>';
                    echo '</tr>';
                    echo '<tr>';
                        echo ' <td> Type Energie : </td>';
                        echo '<td>'.$json_fromJson_commande->getTypeEnergie().'</td>';
                    echo '</tr>';
                    echo '<tr>';
                        echo'<td> Mode Extraction : </td>';
                        echo '<td>'.$json_fromJson_commande->getModeExtraction().'</td>';
                    echo '</tr>';
                    echo '<tr>';
                        echo '<td> Qauntite Demande : </td>';
                        echo '<td>'.$json_fromJson_commande->getQuantiteDemander().'</td>';
                    echo '</tr>';
                    echo '<tr>';
                        echo '<td> Quantite Envoyer : </td>';
                        echo '<td>'.$json_fromJson_commande->getQuantiteEnvoyer().'</td>';
                    echo '</tr>';
                    echo '<tr>';
                        echo '<td> Prix Unites : </td>';
                        echo '<td>'.$json_fromJson_commande->getPrixUnites().'</td>';
                    echo '</tr>';
                    echo '<tr>';
                        echo '<td> Prix Total : </td>';
                        echo '<td>'.$json_fromJson_commande->getPrixTotal().'</td>';
                    echo '</tr>';
                        
                echo '</table>';                              
            ?>  
            
        </div>

    </div>
</div>
  

<footer class="pied_page">
    <div class="container">
        <span class="text-muted">JUILLIARD Quentin & COGNE Romic - </span>
    </div>
</footer>
</body>
</html>
