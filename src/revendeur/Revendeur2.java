package revendeur;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpHandler;

import source.Messenger;

public class Revendeur2 implements Runnable{
    
    private int portServeur;
    private Messenger gestionMessage;
    private int portMarcheGros = 4012;

    public Revendeur2(int portServeur) {
        this.portServeur=portServeur;
        this.gestionMessage=new Messenger("Revendeur2");
    }

    @Override
    public void run()
    {
        gestionMessage.afficheMessage("Started");

        HttpServer serveur = null;
        try {
            serveur = HttpServer.create(new InetSocketAddress(80), 0);
        } catch(IOException e) {
            gestionMessage.afficheMessage("Erreur lors de la création du serveur " + e);
            System.exit(0);
        }
        serveur.createContext("/index", new Revendeur());
        serveur.setExecutor(null);
        serveur.start();
    }
}
