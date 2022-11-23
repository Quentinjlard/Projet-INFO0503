package PONE;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.json.JSONObject;

import Source.*;


public class GazPoneUDP implements Runnable {
    

    private int portGazPoneUDP;
    private Messenger gestionMessage;

    private int portMarcheGros = 4012;

    public GazPoneUDP(int portGazPoneUDP) 
    {
        this.portGazPoneUDP = portGazPoneUDP;
        this.gestionMessage=new Messenger("GazPONEUDP");
    }

    @Override
    public void run()
    {
        // System.out.println("Serveur GazPONEUDP started");

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
                System.err.println("Erreur lors de la création du socket : " + e);
                System.exit(0);
            }
            i++;
            int NumeroDeLot = i;
            String [] ListeTypeEnergie = {"Natuel","Propane","Butane"};
            int casetableaux = (int) ((Math.random() * (2 - 0)) + 0);
            String TypeEnergie ="Gaz";
            String ModeExtraction = ListeTypeEnergie[casetableaux];
            int QuantiteDemander =  (int) ((Math.random() * (250 - 50)) + 50);
            int PrixTotals = 5 * QuantiteDemander;
            Commande commande = new Commande(NumeroDeLot,TypeEnergie, ModeExtraction,QuantiteDemander, PrixTotals);
            commande.toString();

            JSONObject commandeJson = commande.toJson();
            

            // Transformation en tableau d'octets
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try 
            {
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(commandeJson.toString());
            } 
            catch(IOException e) 
            {
                System.err.println("Erreur lors de la sérialisation : " + e);
                System.exit(0);
            }

            // Création et envoi du segment UDP
            try 
            {
                byte[] donnees = baos.toByteArray();
                InetAddress adresse = InetAddress.getByName("localhost");
                DatagramPacket msg = new DatagramPacket(donnees, donnees.length, adresse, portMarcheGros);
                socket.send(msg);
            } 
            catch(UnknownHostException e) 
            {
                System.err.println("Erreur lors de la création de l'adresse : " + e);
                System.exit(0); 
            } 
            catch(IOException e) 
            {
                System.err.println("Erreur lors de l'envoi du message : " + e);
                System.exit(0);
            }
            gestionMessage.afficheMessage("J'ai fournis le lot d'energie suivant : " + commande.getNumeroDeLot());

            socket.close();
            
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
