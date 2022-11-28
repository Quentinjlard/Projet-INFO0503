<?php
    include("Commande.php");
    #author Romain Cogné (romain.cogne@etudiant.univ-reims.fr)
    #author Quentin Juilliard (quentin.juilliard@etudiant.univ-reims.fr)
?>

<!DOCTYPE html>
    <html lang = "fr">
    <head>
        <meta charset="UTF-8">
        <title>Site de commande d'énergie</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

    <body>
        <div class="container">
            <H2>Passer une commande</H2>
            <form action="http://localhost:8080/index" method="POST" >
                <div>
                    <label for="Energie" class="form-group"> Type d'énergie voulu :</label>
                        <select name="Energie" id="Energie" required="required" class="form-control">
                            <option value="Electricite">Electricité</option>
                            <option value="Petrole">Pétrole</option>
                            <option value="Gaz">Gaz</option>
                        </select>
                    <br>
                    <Label class = "form-group">Mode d'extraction souhaité :</Label>
                        <select name="extraction" id = "extraction" required="required" class = "form-control">
                            <option value="Aucune restriction">Aucune restriction</option>
                            <option value="Nucleaire">Electicité - Nucleaire</option>
                            <option value="Eolienne">Electicité - Eolienne</option>
                            <option value="Charbon">Electicité - Charbon</option>
                            <option value="Naturel">Gaz - Naturel</option>
                            <option value="Propane">Gaz - Propane</option>
                            <option value="Butane">Gaz - Butane</option>
                            <option value="Diesel"> Petrole - Diesel</option>
                            <option value="SP98"> Petrole - SP98</option>
                            <option value="SP95"> Petrole - SP95</option>
                        </select>
                </div>
                <br>
                <div class ="form-group">
                    <label for="quantite" class="form-group"> Quantité d'énergie : </label>
                    <input name="quantite" id="quantite" type="number" min="1" max ="99999" placeholder="20000" required="required" class="form-control">
                </div>
                <br>
                
                <br>
                <div>
                <input class="btn btn-primary" type="submit" name="submit" value="Commander">
                </div>
            </form> 
            <br><br>
            2022/2023 | Info0503 | TP2 | Copyright Romain Cogné & Quentin Juilliard
        </div>
    </body>
</html>
