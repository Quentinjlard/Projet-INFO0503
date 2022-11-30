package revendeur;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.Headers;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.util.Vector;

import org.json.JSONObject;

import source.*;


public class RevendeurHandler implements HttpHandler {

    private Messenger gestionMessage;

    public void handle(HttpExchange t) {

        String reponse = "";

        // Récupération des données
        URI requestedUri = t.getRequestURI();
        String query = requestedUri.getRawQuery();

        // Utilisation d'un flux pour lire les données du message Http
        BufferedReader br = null;
        br = utiliserflux(br, t);

        // Récupération des données en POST
        query = recuperationPost(query, br);

        // Affichage des données
        reponse = affichageDonne(reponse, query);

        // création du code de suivi
        SuiviCommande suiviCommande = SuiviCommande.FromJSON(reponse);
        // CodeSuivi codeCommande = new CodeSuivi(commande);
        JSONObject objet = suiviCommande.toJson();

        gestionMessage.afficheMessage("Lu     " + reponse);
        gestionMessage.afficheMessage("Envoye " + objet.toString());
        // System.out.println(commande.toString());
        // codeCommande.afficher();

        // Envoi de l'en-tête Http
        envoieEnteteHTTP(t, objet);

        // Envoi du corps (données HTML)
        envoieCorpsHTTP(t, objet);

        // ------------------création socket TARE------------------
        DatagramSocket socket = null;
        socket = creerSocket(socket);

        // Transformation en tableau d'octets
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        transformationTabOctet(suiviCommande, baos);

        int portEcoute = 9999;
        int portEnvoiTare = 0000;
        if(suiviCommande.getTypeEnergie().equals("Electricite"))
            portEnvoiTare = 1021;
        if(suiviCommande.getTypeEnergie().equals("Gaz"))
            portEnvoiTare = 1022;
        if(suiviCommande.getTypeEnergie().equals("Petrole"))
            portEnvoiTare = 1023;
        // Création et envoi du segment UDP
        creerSegmentUDP(baos, socket, portEnvoiTare);

        // reception du message
        socket = recevoirMessage(socket, portEcoute);

        // Création du message
        byte[] tampon = new byte[1024];
        DatagramPacket msg = new DatagramPacket(tampon, tampon.length);

        // Lecture du message du client
        lectureMessageClient(msg, socket);

        // Fermeture de la socket
        socket.close();
    }

    // Utilisation d'un flux pour lire les données du message Http
    public BufferedReader utiliserflux(BufferedReader br, HttpExchange t) {

        try {
            br = new BufferedReader(new InputStreamReader(t.getRequestBody(), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            System.err.println("Erreur lors de la recuperation du flux " + e);
            System.exit(0);
        }
        return br;
    }

    // Récupération des données en POST
    public String recuperationPost(String query, BufferedReader br) {

        try {
            query = br.readLine();
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture d'une ligne " + e);
            System.exit(0);
        }
        return query;
    }

    // Affichage des données
    public String affichageDonne(String reponse, String query) {

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
        return reponse;
    }

    // Envoi de l'en-tête Http
    public void envoieEnteteHTTP(HttpExchange t, JSONObject objet) {

        try {
            Headers h = t.getResponseHeaders();
            // Content-type: application/x-www-form-urlencoded
            h.set("Content-Type", "text/html; charset=utf-8");
            t.sendResponseHeaders(200, objet.toString().getBytes().length);
        } catch (IOException e) {
            System.err.println("Erreur lors de l'envoi de l'en-tête : " + e);
            System.exit(0);
        }
    }

    // Envoi du corps (données HTML)
    public void envoieCorpsHTTP(HttpExchange t, JSONObject objet) {

        try {
            OutputStream os = t.getResponseBody();
            os.write(objet.toString().getBytes());
            os.close();
        } catch (IOException e) {
            System.err.println("Erreur lors de l'envoi du corps : " + e);
        }
    }

    // ------------------création socket TARE UDP------------------

    // creation de la socket
    public DatagramSocket creerSocket(DatagramSocket socket) {
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            System.err.println("Erreur lors de la creation du socket : " + e);
            System.exit(0);
        }
        return socket;
    }

    // Transformation en tableau d'octets d'un objet energie
    public void transformationTabOctet(SuiviCommande objet, ByteArrayOutputStream baos) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(objet);
        } catch (IOException e) {
            System.err.println("Erreur lors de la serialisation : " + e);
            System.exit(0);
        }

    }

    public void creerSegmentUDP(ByteArrayOutputStream baos, DatagramSocket socket, int portEcoute) {
        try {
            byte[] donnees = baos.toByteArray();
            InetAddress adresse = InetAddress.getByName("localhost");
            DatagramPacket msg = new DatagramPacket(donnees, donnees.length,
                    adresse, portEcoute);
            socket.send(msg);
        } catch (UnknownHostException e) {
            System.err.println("Erreur lors de la creation de l'adresse : " + e);
            System.exit(0);
        } catch (IOException e) {
            System.err.println("Erreur lors de l'envoi du message : " + e);
            System.exit(0);
        }
    }

    public DatagramSocket recevoirMessage(DatagramSocket socket, int portEnvoi) {
        try {
            socket = new DatagramSocket(portEnvoi);
        } catch (SocketException e) {
            System.err.println("Erreur lors de la creation de la socket : " + e);
            System.exit(0);
        }
        return socket;
    }

    public void lectureMessageClient(DatagramPacket msg, DatagramSocket socket) {
        try {
            socket.receive(msg);
            String texte = new String(msg.getData(), 0, msg.getLength());

            gestionMessage.afficheMessage("Lu " + texte);
        } catch (IOException e) {
            System.err.println("Erreur lors de la reception du message : " + e);
            System.exit(0);
        }
    }
}

