package AMI;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import org.json.JSONObject;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import source.*;

public class AMI implements Runnable{

    private final int portAMI;
    private int portEcouteMarche = 5010;
    private Messenger gestionMessage;


    public AMI(int portAMI)
    {
        this.portAMI = portAMI;
        this.gestionMessage=new Messenger("AMI ");
    }

    @Override
    public void run() 
    {
        gestionMessage.afficheMessage("Started");

        while(true) {
            //Mes variables
            String message = "";
            Energie demande;
            SuiviCommande demandeCommande;
            String nomFichierClePrivee = "src"+java.io.File.separator+"privee";
            String nomFichierClePublique = "src"+java.io.File.separator+"publique";

            // Récupération de la clé privée
            PrivateKey clePrivee = GestionClesRSA.lectureClePrivee(nomFichierClePrivee);

            // Recuperation de la cle publique
            PublicKey clePublique = GestionClesRSA.lectureClePublique(nomFichierClePublique);

            // Création de la socket serveur
            ServerSocket socketServeur = null;
            try {    
                socketServeur = new ServerSocket(portEcouteMarche);
            } catch(IOException e) {
                System.err.println("Création de la socket impossible : " + e);
                System.exit(0);
            }

            // Attente d'une connexion d'un client
            Socket socketClient = null;
            try {
                socketClient = socketServeur.accept();
            } catch(IOException e) {
                System.err.println("Erreur lors de l'attente d'une connexion : " + e);
                System.exit(0);
            }

            // Association d'un flux d'entrée et de sortie
            BufferedReader input = null;
            PrintWriter output = null;
            try {
                input = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
                output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socketClient.getOutputStream())), true);
            } catch(IOException e) {
                System.err.println("Association des flux impossible : " + e);
                System.exit(0);
            }

            //Récupération de la demande
            try {
                message = input.readLine();
            } catch(IOException e) {
                System.err.println("Erreur lors de la lecture : " + e);
                System.exit(0);
            }
            gestionMessage.afficheMessage("J'ai reçu une demande à valider");
            // Déchiffrement du message
            byte[] array = java.util.Base64.getDecoder().decode(message);
            byte[] bytes = null;
            try {
                Cipher dechiffreur = Cipher.getInstance("RSA");
                dechiffreur.init(Cipher.DECRYPT_MODE, clePrivee);
                bytes = dechiffreur.doFinal(array);
            } catch(NoSuchAlgorithmException e) {
                System.err.println("Erreur lors du dechiffrement : " + e);
                System.exit(0);
            } catch(NoSuchPaddingException e) {
                System.err.println("Erreur lors du dechiffrement : " + e);
                System.exit(0);
            } catch(InvalidKeyException e) {
                System.err.println("Erreur lors du dechiffrement : " + e);
                System.exit(0);
            } catch(IllegalBlockSizeException e) {
                System.err.println("Erreur lors du dechiffrement : " + e);
                System.exit(0);
            } catch(BadPaddingException e) {
                System.err.println("Erreur lors du dechiffrement : " + e);
                System.exit(0);
            }
            String reponse = new String(bytes);
            int cas = Integer.parseInt(reponse.substring(0,1));

            switch(cas) {
                case 1: //Demande validation prix énergie
                    String energieJSON = new String(bytes);
                    energieJSON = energieJSON.substring(1);
                    demande = Energie.FromJSON(energieJSON);
                    int prix = demande.getPrixUnite();
                    message = "Le prix proposé par le PONE (" + prix + ") est accepté.";
        
                    // Chiffrement du message
                    bytes = null;
                    try {
                        Cipher chiffreur = Cipher.getInstance("RSA");
                        chiffreur.init(Cipher.ENCRYPT_MODE, clePublique);
                        bytes = chiffreur.doFinal(message.getBytes());
                        message = java.util.Base64.getEncoder().encodeToString(bytes);
        
                    } catch(NoSuchAlgorithmException e) {
                        System.err.println("Erreur lors du chiffrement : " + e);
                        System.exit(0);
                    } catch(NoSuchPaddingException e) {
                        System.err.println("Erreur lors du chiffrement : " + e);
                        System.exit(0);
                    } catch(InvalidKeyException e) {
                        System.err.println("Erreur lors du chiffrement : " + e);
                        System.exit(0);
                    } catch(IllegalBlockSizeException e) {
                        System.err.println("Erreur lors du chiffrement : " + e);
                        System.exit(0);
                    } catch(BadPaddingException e) {
                        System.err.println("Erreur lors du chiffrement : " + e);
                        System.exit(0);
                    } 
                    gestionMessage.afficheMessage("J'envoie ma réponse au marche de gros (positive)." );
                    output.println(message);
                break;
                case 2: //demande validation achat par TARE
                    String achat = new String(bytes);
                    achat = achat.substring(1);
                    demandeCommande = SuiviCommande.FromJSON(achat);
                    message = "L'achat  n°(" + demandeCommande.getNumeroDeCommande() + ") est accepté.";
        
                    // Chiffrement du message
                    bytes = null;
                    try {
                        Cipher chiffreur = Cipher.getInstance("RSA");
                        chiffreur.init(Cipher.ENCRYPT_MODE, clePublique);
                        bytes = chiffreur.doFinal(message.getBytes());
                        message = java.util.Base64.getEncoder().encodeToString(bytes);
        
                    } catch(NoSuchAlgorithmException e) {
                        System.err.println("Erreur lors du chiffrement : " + e);
                        System.exit(0);
                    } catch(NoSuchPaddingException e) {
                        System.err.println("Erreur lors du chiffrement : " + e);
                        System.exit(0);
                    } catch(InvalidKeyException e) {
                        System.err.println("Erreur lors du chiffrement : " + e);
                        System.exit(0);
                    } catch(IllegalBlockSizeException e) {
                        System.err.println("Erreur lors du chiffrement : " + e);
                        System.exit(0);
                    } catch(BadPaddingException e) {
                        System.err.println("Erreur lors du chiffrement : " + e);
                        System.exit(0);
                    } 
                    gestionMessage.afficheMessage("J'envoie ma réponse au marche de gros (positive)." );
                    output.println(message);
            }
            

            // Fermeture des flux et des sockets
            try {
                input.close();
                output.close();
                socketClient.close();
                socketServeur.close();
            } catch(IOException e) {
                System.err.println("Erreur lors de la fermeture des flux et des sockets : " + e);
                System.exit(0);
            }
        }
    }
}
