package revendeur;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpHandler;

import source.Messenger;

public class Revendeur implements Runnable{
    
    private int portServeur;
    private Messenger gestionMessage;

    public Revendeur(int portServeur) {
        this.portServeur=portServeur;
        this.gestionMessage=new Messenger("Revendeur");
    }

    @Override
    public void run()
    {
        gestionMessage.afficheMessage("Started");

        HttpServer serveur = null;
        try {
            serveur = HttpServer.create(new InetSocketAddress(8080), 0);
        } catch(IOException e) {
            gestionMessage.afficheMessage("Erreur lors de la création du serveur " + e);
            System.exit(0);
        }
        serveur.createContext("/index", new RevendeurHandler());
        serveur.setExecutor(null);
        serveur.start();
    }
}
