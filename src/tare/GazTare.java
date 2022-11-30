package tare;

import java.io.IOException;
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;

import source.Messenger;

public class GazTare implements Runnable {
    
    private int portServeurUDP;
    private Messenger gestionMessage;

    public GazTare(int portServeurUDP) {
        this.portServeurUDP=portServeurUDP;
        this.gestionMessage=new Messenger("TARE - Gaz");
    }
    
    @Override
    public void run() 
    {
        gestionMessage.afficheMessage("Started");

        HttpServer serveur = null;
        try {
            serveur = HttpServer.create(new InetSocketAddress(1022), 0);
        } catch(IOException e) {
            System.err.println("Erreur lors de la crÃ©ation du serveur " + e);
            System.exit(0);
        }

        serveur.createContext("/index", new GazHandler());
        serveur.setExecutor(null);
        serveur.start();

    }
    
}
