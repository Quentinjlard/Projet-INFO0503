package tare;

import java.io.IOException;
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;

import source.Messenger;

public class ElectriciteTare implements Runnable {
    
    private int portServeurUDP;
    private Messenger gestionMessage;

    public ElectriciteTare(int portServeurUDP) {
        this.portServeurUDP=portServeurUDP;
        this.gestionMessage=new Messenger("TARE - Electricite");
    }
    
    @Override
    public void run() 
    {
        gestionMessage.afficheMessage("Started");
        
        HttpServer serveur = null;
        try {
            serveur = HttpServer.create(new InetSocketAddress(1021), 0);
        } catch(IOException e) {
            gestionMessage.afficheMessage("Erreur lors de la creation du serveur " + e);
            System.exit(0);
        }

        serveur.createContext("/traitement", new ElectriciteHandler());
        serveur.setExecutor(null);
        serveur.start();

    }
}
