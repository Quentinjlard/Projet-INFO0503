package TARE;

import Source.*;
public class GazTareTCP implements Runnable {
    
    private int portServeurTCP;
    private Messenger gestionMessage;

    public GazTareTCP(int portServeurTCP) {
        this.portServeurTCP=portServeurTCP;
        this.gestionMessage=new Messenger("GazTARETCP");
    }

    @Override
    public void run()
    {
        System.out.println("Serveur GazTareTCP started");
    }
}
