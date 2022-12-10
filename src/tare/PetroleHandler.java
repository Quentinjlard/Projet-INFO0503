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
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.URLDecoder;
import java.net.UnknownHostException;

import org.json.JSONObject;

import source.*;

public class PetroleHandler  implements HttpHandler {

    private int portEcouteMarche = 4012;
    private int portEcoutePetrole = 1023;
    
    public void handle(HttpExchange t) {

        Messenger gestionMessage=new Messenger("Handler - Electricite");
        //gestionMessage.afficheMessage("GO ! ");

        String reponse = "";

        // Récupération des données
        URI requestedUri = t.getRequestURI();
        String query = requestedUri.getRawQuery();

        // Utilisation d'un flux pour lire les données du message Http
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(t.getRequestBody(), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            gestionMessage.afficheMessage("Erreur lors de la recuperation du flux " + e);
            System.exit(0);
        }

        // Récupération des données en POST
        try {
            query = br.readLine();
        } catch(IOException e) {
            gestionMessage.afficheMessage("Erreur lors de la lecture d'une ligne " + e);
            System.exit(0);
        }

        // Affichage des données recu
        if (query == null)
            reponse += "Aucune";
        else {
            try {
                query = URLDecoder.decode(query, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                query = "";
            }
            reponse += query;
        }
        //gestionMessage.afficheMessage("REPONSE => " + reponse);

        // création du code de suivi
        //gestionMessage.afficheMessage("TEST");
        SuiviCommande suiviCommande = SuiviCommande.FromJSON(reponse);
        //gestionMessage.afficheMessage("Suivi Commande => " + suiviCommande);

        JSONObject objet = suiviCommande.toJson();

        //gestionMessage.afficheMessage("Lu     " + reponse);
        //gestionMessage.afficheMessage("Envoye " + objet.toString());
        // System.out.println(commande.toString());
        // codeCommande.afficher();



        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            gestionMessage.afficheMessage("Erreur lors de la creation du socket : " + e);
            System.exit(0);
        }

        
        try {
            byte[] donnees = objet.toString().getBytes();
            InetAddress adresse = InetAddress.getByName("localhost");
            DatagramPacket msg = new DatagramPacket(donnees, donnees.length, adresse, portEcouteMarche);
            socket.send(msg);
        } catch (UnknownHostException e) {
            gestionMessage.afficheMessage("Erreur lors de la creation de l'adresse : " + e);
            System.exit(0);
        } catch (IOException e) {
            gestionMessage.afficheMessage("Erreur lors de l'envoi du message : " + e);
            System.exit(0);
        }

        // reception du message
        
        try {
            socket = new DatagramSocket(portEcoutePetrole);
        } catch (SocketException e) {
            gestionMessage.afficheMessage("Erreur lors de la creation de la socket : " + e);
            System.exit(0);
        }

        //Reponse
        DatagramPacket msgRecu = null;
        try {
            byte[] tampon = new byte[1024];
            msgRecu = new DatagramPacket(tampon, tampon.length);
            socket.receive(msgRecu);
        } catch(IOException e) {
            gestionMessage.afficheMessage("Erreur lors de la réception du message : " + e);
            System.exit(0);
        }
        String msgRecuStroString = new String(msgRecu.getData());
        SuiviCommande commande = SuiviCommande.FromJSON(msgRecuStroString);
        //gestionMessage.afficheMessage("=> Lu " + commande);

        JSONObject commanderetour = commande.toJson();

        // Fermeture de la socket
        socket.close();

        // Envoi de l'en-tête Http
        try {
            Headers h = t.getResponseHeaders();
            // Content-type: application/x-www-form-urlencoded
            h.set("Content-Type", "text/html; charset=utf-8");
            t.sendResponseHeaders(200, commanderetour.toString().getBytes().length);
        } catch (IOException e) {
                gestionMessage.afficheMessage("Erreur lors de l'envoi de l'en-tête : " + e);
                System.exit(0);
        }
        
        // Envoi du corps (données HTML)
        try {
            OutputStream os = t.getResponseBody();
            os.write(commanderetour.toString().getBytes());
            os.close();
        } catch (IOException e) {
            gestionMessage.afficheMessage("Erreur lors de l'envoi du corps : " + e);
        }
    }
}
