package tare;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.json.JSONObject;

import source.*;

public class GazTare implements Runnable {
    
    private int portServeurTCP;
    private Messenger gestionMessage;

    public GazTare(int portServeurTCP) {
        this.portServeurTCP=portServeurTCP;
        this.gestionMessage=new Messenger("TARE - Gaz");
    }

    @Override
    public void run()
    {
        System.out.println("Serveur GazTareTCP started");

        // Création de la socket
        DatagramSocket socket = null;
        try {        
            socket = new DatagramSocket(1022);
        } catch(SocketException e) {
            gestionMessage.afficheMessage("Erreur lors de la création de la socket : " + e);
            System.exit(0);
        }

    }
}
