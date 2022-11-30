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
            System.err.println("Erreur lors de la crÃ©ation du serveur " + e);
            System.exit(0);
        }

        serveur.createContext("/index", new ElectriciteHandler());
        serveur.setExecutor(null);
        serveur.start();

    }
}
