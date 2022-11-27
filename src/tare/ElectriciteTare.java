package tare;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.json.JSONObject;

import source.*;

public class ElectriciteTare implements Runnable{
    
    private int portServeurTCP;
    private Messenger gestionMessage;
    private int portMarcheGros = 4012;

    public ElectriciteTare(int portServeurTCP) {
        this.portServeurTCP=portServeurTCP;
        this.gestionMessage=new Messenger("TARE - Electricite");
    }

    @Override
    public void run()
    {
        System.out.println("Serveur ElectriciteTareTCP started");


        /**
         * 
         * PARTIE TCP
         * 
         */

        // Création de la socket
        Socket socketTCP = null;
        try {
            socketTCP = new Socket("localhost", 8080);
        } catch(UnknownHostException e) {
            gestionMessage.afficheMessage("Erreur sur l'hôte : " + e);
            System.exit(0);
        } catch(IOException e) {
            gestionMessage.afficheMessage("Création de la socket impossible : " + e);
            System.exit(0);
        }

        //Recuepere mon suiviCommande
        SuiviCommande suiviCommandeEntrante = null;
        try {
            InputStream var = socketTCP.getInputStream();
            InputStreamReader var2 = new InputStreamReader(var);
            BufferedReader var3 = new BufferedReader(var2);
            suiviCommandeEntrante = SuiviCommande.FromJSON(var3.toString());
        } catch(IOException e) {
            gestionMessage.afficheMessage("Erreur lors de la lecture : " + e);
            System.exit(0);
        }

        /**
          * PARTIE UDP
          */

        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Création de la socket
        DatagramSocket socketUDP = null;
        try {        
            socketUDP = new DatagramSocket(1021);
        } catch(SocketException e) {
            gestionMessage.afficheMessage("Erreur lors de la création de la socket : " + e);
            System.exit(0);
        }

        int NumeroDeCommande = 1;
        String TypeEnergie = suiviCommandeEntrante.getTypeEnergie();
        String ModeExtraction = suiviCommandeEntrante.getModeExtraction();
        int QuantiteDemander = suiviCommandeEntrante.getQuantiteDemander();
        int QuantiteFournis = -1;
        int PrixUnits = -1;
        int PrixTotals = -1; 
        int NumeroDeLot = -1;
    
        Commande commande = new Commande(NumeroDeCommande, TypeEnergie, ModeExtraction, QuantiteFournis, QuantiteDemander,PrixUnits, PrixTotals, NumeroDeLot);
        JSONObject commandeJson = commande.toJson();

        try 
        {
            byte[] donnees = commandeJson.toString().getBytes();
            
            InetAddress adresse = InetAddress.getByName("localhost");
            DatagramPacket msg = new DatagramPacket(donnees, donnees.length, adresse, portMarcheGros);
            socketUDP.send(msg);
        } 
        catch(UnknownHostException e) 
        {
            gestionMessage.afficheMessage("Erreur lors de la création de l'adresse : " + e);
            System.exit(0); 
        } 
        catch(IOException e) 
        {
            gestionMessage.afficheMessage("Erreur lors de l'envoi du message : " + e);
            System.exit(0);
        }
        gestionMessage.afficheMessage("J'ai envoyé une commande : " + commande.getNumeroDeCommande());

        

        // Lecture du message du client
        DatagramPacket msgRecu = null;
        try {
            byte[] tampon = new byte[1024];
            msgRecu = new DatagramPacket(tampon, tampon.length);
            socketUDP.receive(msgRecu);
        } catch(IOException e) {
            gestionMessage.afficheMessage("Erreur lors de la réception du message : " + e);
            System.exit(0);
        }

        String msgRecuStroString = new String(msgRecu.getData());
        commande = Commande.FromJSON(msgRecuStroString);
        
        gestionMessage.afficheMessage("J'ai bien recu la reponse commande : " + commande.getNumeroDeCommande());

        SuiviCommande suiviCommande = new SuiviCommande (
                                                            suiviCommandeEntrante.getIdClient()     ,// idClient
                                                            suiviCommandeEntrante.getIdRevendeur()  ,// idRevendeur
                                                            suiviCommandeEntrante.getIdTare()       ,// idTare
                                                            commande.getNumeroDeCommande()          ,// numeroDeCommande
                                                            commande.getNumeroDeLot()               ,// NumeroDeLot
                                                            commande.getTypeEnergie()               ,// typeEnergie
                                                            commande.getModeExtraction()            ,// modeExtraction
                                                            commande.getQuantiteDemander()          ,// quantiteDemander
                                                            commande.getQuantiteFournis()           ,// quantiteEnvoyer
                                                            commande.getPrixUnites()                ,// prixUnites
                                                            commande.getPrixTotals()                // prixTotal
                                                        );

        socketUDP.close();
    }
}

