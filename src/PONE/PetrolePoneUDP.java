package PONE;

import Source.*;


public class PetrolePoneUDP implements Runnable {

    private static int portPetrolePoneUDP;
    private Messenger gestionMessage;


    public PetrolePoneUDP(int portPetrolePoneUDP)
    {
        this.portPetrolePoneUDP=portPetrolePoneUDP;
        this.gestionMessage=new Messenger("PetrolePONEUDP");

    }
    @Override
    public void run() 
    {
        System.out.println("Serveur PetrolePONEUDP started");
    }
    
}
