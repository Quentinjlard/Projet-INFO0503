package ami;

import source.*;

public class AMI implements Runnable{

    private final int portAMI;
    private Messenger gestionMessage;


    public AMI(int portAMI)
    {
        this.portAMI = portAMI;
        this.gestionMessage=new Messenger("AMI ");
    }

    @Override
    public void run() 
    {
        gestionMessage.afficheMessage("Started");
    }
}
