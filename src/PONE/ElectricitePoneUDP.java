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

public class ElectricitePoneUDP implements Runnable {
    
    private int portElectricPoneUDP;
    private Messenger gestionMessage;

    private int portMarcheGros = 4012;

    public ElectricitePoneUDP(int portElectricPoneUDP)
    {
        this.portElectricPoneUDP = portElectricPoneUDP;
        this.gestionMessage=new Messenger("ElectricitePONEUDP");
    }

    @Override
    public void run()
    {
        // System.out.println("Serveur ElectricitePoneUDP started");

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
            String [] ListeTypeEnergie = {"Nucleaire","Eolienne","Charbon"};
            int casetableaux = (int) ((Math.random() * (2 - 0)) + 0);
            String TypeEnergie ="Electricite";
            String ModeExtraction = ListeTypeEnergie[casetableaux];
            int QuantiteDemander =  (int) ((Math.random() * (250 - 50)) + 50);
            int PrixTotals = 5 * QuantiteDemander;
            Energie Energie = new Energie(NumeroDeLot,TypeEnergie, ModeExtraction,QuantiteDemander, PrixTotals);
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
                System.err.println("Erreur lors de la création de l'adresse : " + e);
                System.exit(0); 
            } 
            catch(IOException e) 
            {
                System.err.println("Erreur lors de l'envoi du message : " + e);
                System.exit(0);
            }
            gestionMessage.afficheMessage("J'ai fournis le lot d'energie suivant : " + Energie.getNumeroDeLot());

            socket.close();
            
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
