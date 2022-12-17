<?php
    include("SuiviCommande.php");
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
            <H2>Passer une commande Généraliste</H2>
            <form action="traitement.php" method="POST" >
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
                    <input name="quantite" id="quantite" type="number" min="1" max ="300" placeholder="150" required="required" class="form-control">
                </div>
                <br>
                
                <br>
                <div>
                <input class="btn btn-primary" type="submit" name="submit" value="Commander">
                </div>
            </form> 
            <br><br>

            <h2 class="text-center"> Les différents sénario : </h2>
            <br><br>
            <table class="table text-center">
            <thead>
                <tr>
                    <th scope="col"></th>
                    <th scope="col">Sénario A</th>
                    <th scope="col">Sénario B</th>
                    <th scope="col">Sénario C</th>
                    <th scope="col">Sénario D</th>
                    <th scope="col">Sénario A2</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <th scope="row">
                        
                        Type d'énergie <br><br><br>
                        Mode Extraction <br><br><br>
                        Quantite <br><br><br>
                        Lancer <br>
                    </th>
                    <td>
                        <form action="traitement.php" method="POST" >
                            <input name="Energie" id="Energie" 
                                required="required" class="form-control" value="Electricite">
                            <br>
                            <input name="extraction" id="extraction" 
                                required="required" class="form-control" value="Aucune restriction">
                            <br>
                            <input name="quantite" id="quantite" 
                                type="number" min="1" max ="300" placeholder="150" required="required" class="form-control" value="1">
                            <br>
                            <input class="btn btn-secondary" type="submit" name="submit" value="Sénario A">
                        </form>
                    </td>
                    <td>
                        <form action="traitement.php" method="POST" >
                            <input name="Energie" id="Energie" 
                                required="required" class="form-control" value="Electricite">
                            <br>
                            <input name="extraction" id="extraction" 
                                required="required" class="form-control" value="Aucune restriction">
                            <br>
                            <input name="quantite" id="quantite" 
                                type="number" min="1" max ="300" placeholder="150" required="required" class="form-control" value="100">
                            <br>
                            <input class="btn btn-info" type="submit" name="submit" value="Sénario B">
                        </form>
                    </td>
                    <td>
                        <form action="traitement.php" method="POST" >
                            <input name="Energie" id="Energie" 
                                required="required" class="form-control" value="Petrole">
                            <br>
                            <input name="extraction" id="extraction" 
                                required="required" class="form-control" value="Aucune restriction">
                            <br>
                            <input name="quantite" id="quantite" 
                                type="number" min="1" max ="300" placeholder="150" required="required" class="form-control" value="50">
                            <br>
                            <input class="btn btn-warning" type="submit" name="submit" value="Sénario C">
                        </form>
                    </td>
                    <td>
                        <form action="traitement.php" method="POST" >
                            <input name="Energie" id="Energie" 
                                required="required" class="form-control" value="Electricite">
                            <br>
                            <input name="extraction" id="extraction" 
                                required="required" class="form-control" value="Aucune restriction">
                            <br>
                            <input name="quantite" id="quantite" 
                                type="number" min="1" max ="300" placeholder="150" required="required" class="form-control" value="1">
                            <br>
                            <input class="btn btn-danger" type="submit" name="submit" value="Sénario D">
                        </form>
                    </td>
                    <td>
                        <form action="traitement.php" method="POST" >
                            <input name="Energie" id="Energie" 
                                required="required" class="form-control" value="Electricite">
                            <br>
                            <input name="extraction" id="extraction" 
                                required="required" class="form-control" value="Aucune restriction">
                            <br>
                            <input name="quantite" id="quantite" 
                                type="number" min="1" max ="300" placeholder="150" required="required" class="form-control" value="1">
                            <br>
                            <input class="btn btn-dark" type="submit" name="submit" value="Sénario A2">
                        </form>
                    </td>
                </tr>
                <tr>
                    <th scope="row">Remarque</th>
                    <td>Satifait au 1er envoie <br> Attendre 5 seconde aprés lancement</td>
                    <td>Appuyer sur bon <br>Si non statisfait revenir et recommencer <br> Sinon relancer l'application en cas de reponse toujpours satisifaite</td>
                    <td>Attendre 30 seconde <br>aprés lancement du lanceur <br> Puis presser le bouton</td>
                    <td>Non operationnel</td>
                    <td>Non operationnel</td>
                </tr>
            </tbody>
            </table>
            <br><br>    

            2022/2023 | Info0503 | TP2 | Copyright Romain Cogné & Quentin Juilliard
        </div>
    </body>
</html>
