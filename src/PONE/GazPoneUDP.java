package pone;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.json.JSONObject;

import source.*;


public class GazPoneUDP implements Runnable {
    

    private int portGazPoneUDP;
    private Messenger gestionMessage;

    private int portMarcheGros = 4012;

    public GazPoneUDP(int portGazPoneUDP) 
    {
        this.portGazPoneUDP = portGazPoneUDP;
        this.gestionMessage=new Messenger("PONE - Gaz");
    }

    @Override
    public void run()
    {

        // Création de la requete energie
        int i = 0;
        while(true)
        {
            // Création de la socket
            DatagramSocket socket = null;
            try 
            {
                socket = new DatagramSocket();
            } 
            catch(SocketException e) 
            {
                gestionMessage.afficheMessage("Erreur lors de la création du socket : " + e);
                System.exit(0);
            }
            i++;
            int NumeroDeLot = i;
            String [] ListeTypeEnergie = {"Naturel","Propane","Butane"};
            int casetableaux = (int) ((Math.random() * (2 - 0)) + 0);
            String TypeEnergie ="Gaz";
            String ModeExtraction = ListeTypeEnergie[casetableaux];
            int QuantiteEnvoyer =  (int) ((Math.random() * (250 - 50)) + 50);
            int PrixUnite = (int) ((Math.random() * (300 - 50)) + 50);
            Energie Energie = new Energie(NumeroDeLot,TypeEnergie, ModeExtraction,QuantiteEnvoyer, PrixUnite);
            Energie.toString();

            JSONObject EnergieJson = Energie.toJson();
            

            
            // Création et envoi du segment UDP
            try 
            {
                byte[] donnees = EnergieJson.toString().getBytes();
                
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
            gestionMessage.afficheMessage("J'ai fournis le lot d'energie suivant : " + Energie.getNumeroDeLot());

            socket.close();
            
            try {
                Thread.sleep(20000);
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
