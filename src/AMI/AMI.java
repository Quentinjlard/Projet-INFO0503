package AMI;

import Source.*;

public class AMI implements Runnable{

    private final int portAMI;
    private Messenger gestionMessage;


    public AMI(int portAMI)
    {
        this.portAMI = portAMI;
        this.gestionMessage=new Messenger("Serveur AMI");
    }

    @Override
    public void run() 
    {
        System.out.println("Serveur AMI started");
    }
}
