package PONE;

import Source.*;


public class GazPoneUDP implements Runnable {
    

    private int portGazPoneUDP;
    private Messenger gestionMessage;


    public GazPoneUDP(int portGazPoneUDP) 
    {
        this.portGazPoneUDP = portGazPoneUDP;
        this.gestionMessage=new Messenger("GazPONEUDP");
    }

    @Override
    public void run()
    {
        System.out.println("Serveur GazPONEUDP started");
    }
}
