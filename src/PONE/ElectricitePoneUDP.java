package PONE;
import Source.*;

public class ElectricitePoneUDP implements Runnable {
    
    private final int portElectricPoneUDP;
    private Messenger gestionMessage;

    public ElectricitePoneUDP(int portElectricPoneUDP)
    {
        this.portElectricPoneUDP = portElectricPoneUDP;
        this.gestionMessage=new Messenger("ElectricitePONEUDP");
    }

    @Override
    public void run()
    {
        System.out.println("Serveur ElectricitePoneUDP started");
    }
}
