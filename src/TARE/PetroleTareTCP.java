package TARE;

import Source.*;

public class PetroleTareTCP  implements Runnable {
    
    private int portServeurTCP;
    private Messenger gestionMessage;

    public PetroleTareTCP(int portServeurTCP) {
        this.portServeurTCP=portServeurTCP;
        this.gestionMessage=new Messenger("PetroleTARETCP");
    }

    @Override
    public void run()
    {
        System.out.println("Serveur PetroleTareTCP started");
    }
    
}
