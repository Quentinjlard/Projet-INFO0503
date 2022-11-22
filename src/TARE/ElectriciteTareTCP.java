package TARE;

import Source.Messenger;

public class ElectriciteTareTCP implements Runnable{
    
    private int portServeurTCP;
    private Messenger gestionMessage;

    public ElectriciteTareTCP(int portServeurTCP) {
        this.portServeurTCP=portServeurTCP;
        this.gestionMessage=new Messenger("ElectriciteTARETCP");
    }

    @Override
    public void run()
    {
        System.out.println("Serveur ElectriciteTareTCP started");
    }
}
