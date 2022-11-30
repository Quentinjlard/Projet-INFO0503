package tare;

import java.io.IOException;
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;

import source.Messenger;

public class PetroleTare implements Runnable {
    
    private int portServeurUDP;
    private Messenger gestionMessage;

    public PetroleTare(int portServeurUDP) {
        this.portServeurUDP=portServeurUDP;
        this.gestionMessage=new Messenger("TARE - Petrole");
    }
    
    @Override
    public void run() 
    {
        gestionMessage.afficheMessage("Started");

        HttpServer serveur = null;
        try {
            serveur = HttpServer.create(new InetSocketAddress(1023), 0);
        } catch(IOException e) {
            System.err.println("Erreur lors de la crÃ©ation du serveur " + e);
            System.exit(0);
        }

        serveur.createContext("/index", new PetroleHandler());
        serveur.setExecutor(null);
        serveur.start();

    }
    
}
