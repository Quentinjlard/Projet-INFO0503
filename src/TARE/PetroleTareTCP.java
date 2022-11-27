package tare;

import source.*;

public class PetroleTareTCP  implements Runnable {
    
    private int portServeurTCP;
    private Messenger gestionMessage;

    public PetroleTareTCP(int portServeurTCP) {
        this.portServeurTCP=portServeurTCP;
        this.gestionMessage=new Messenger("TARE - Petrole");
    }

    @Override
    public void run()
    {
        System.out.println("Serveur PetroleTareTCP started");
    }
    
}
