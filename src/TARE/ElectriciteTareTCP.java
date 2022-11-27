package tare;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.json.JSONObject;

import source.*;

public class ElectriciteTareTCP implements Runnable{
    
    private int portServeurTCP;
    private Messenger gestionMessage;
    private int portMarcheGros = 4012;

    public ElectriciteTareTCP(int portServeurTCP) {
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

        /**
          * PARTIE UDP
          */

        while(true)
        {

        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Création de la socket
        DatagramSocket socket = null;
        try {        
            socket = new DatagramSocket(1021);
        } catch(SocketException e) {
            gestionMessage.afficheMessage("Erreur lors de la création de la socket : " + e);
            System.exit(0);
        }

        int NumeroDeCommande = 1;
        String TypeEnergie = "Electricite";
        String ModeExtraction = "Nucleaire";
        int QuantiteDemander = 1;
        int QuantiteFournis = 0;
        int PrixTotals = 0; 
        int NumeroDeLot = 0;
    
        Commande commande = new Commande(NumeroDeCommande, TypeEnergie, ModeExtraction, QuantiteFournis, QuantiteDemander, PrixTotals, NumeroDeLot);
        JSONObject commandeJson = commande.toJson();

        try 
        {
            byte[] donnees = commandeJson.toString().getBytes();
            
            InetAddress adresse = InetAddress.getByName("localhost");
            DatagramPacket msg = new DatagramPacket(donnees, donnees.length, adresse, portMarcheGros);
            socket.send(msg);
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
            socket.receive(msgRecu);
        } catch(IOException e) {
            gestionMessage.afficheMessage("Erreur lors de la réception du message : " + e);
            System.exit(0);
        }

        String msgRecuStroString = new String(msgRecu.getData());
        commande = Commande.FromJSON(msgRecuStroString);
        System.out.println(commande);
        gestionMessage.afficheMessage("J'ai bien recu la reponse commande : " + commande.getNumeroDeCommande());

        socket.close();
    }
    }
}
