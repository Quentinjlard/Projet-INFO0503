package tare;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.Headers;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

public class GazHandler  implements HttpHandler {
    
    public void handle(HttpExchange t) {

        String reponse = "<h1>Demande recue</h1>";
        String query ="";
        //'
        // Utilisation d'un flux pour lire les données du message Http
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(t.getRequestBody(),"utf-8"));
        } catch(UnsupportedEncodingException e) {
            System.err.println("Erreur lors de la récupération du flux " + e);
            System.exit(0);
        }
	
        // Récupération des données en POST
        try {
            query = br.readLine();
        } catch(IOException e) {
            System.err.println("Erreur lors de la lecture d'une ligne " + e);
            System.exit(0);
        }
        reponse += "<p>Demande du revendeur : ";
        if(query == null) { 
            reponse += "<b>Aucune</b></p>";
        }
        else {
            String[] partiRef = query.split("&");
            /**
             * Le form action renvoie vers mon serveur PHP, modifier le lien pour rediriger vers le votre
            */
            reponse+=
                "</br><strong>Type d'energie </strong>: "+partiRef[0].split("=")[1]+
                "</br><strong>Mode d'extraction</strong> : "+partiRef[1].split("=")[1]+
                "</br><strong>Quantite d'energie</strong> : "+partiRef[2].split("=")[1]+
                "</br> <strong>Budget :</strong> "+partiRef[3].split("=")[1]+
                """
                <form action="http://rendutp4/Affichage.php" target="_blank" method="POST" > 
                    <br>
                    <input type="hidden" name="Energie" id="Energie" value="""+"\""+partiRef[0].split("=")[1]+"\""+""" 
                        >
                    <input type="hidden" name="extraction" id="extraction" value="""+"\""+partiRef[1].split("=")[1]+"\""+""" 
                    >
                    <input type="hidden" name="quantite" id="quantite" value="""+"\""+partiRef[2].split("=")[1]+"\""+""" 
                    >
                    <input type="hidden" name="budget" id="budget" value="""+"\""+partiRef[3].split("=")[1]+"\""+""" 
                    >
                    <div>
                    <input class="btn btn-primary" type="submit" name="oui" value="Oui">
                    </div>
                </form>
                <form action="http://rendutp4/Affichage.php" target="_blank" method="POST" >
                    <div>
                    <input class="btn btn-primary" type="submit" name="non" value="Non">
                    </div>
                </form>
                """;
            
        }
        // Envoi de l'en-tête Http
        try {
            Headers h = t.getResponseHeaders();
            h.set("Content-Type", "text/html; charset=utf-8");
            t.sendResponseHeaders(200, reponse.getBytes().length);
        } catch(IOException e) {
            System.err.println("Erreur lors de l'envoi de l'en-tête : " + e);
            System.exit(0);
        }

        // Envoi du corps (données HTML)
        try {
            OutputStream os = t.getResponseBody();
            os.write(reponse.getBytes());
            os.close();
        } catch(IOException e) {
            System.err.println("Erreur lors de l'envoi du corps : " + e);
        }
    }
}

