package MARCHEGROS;

import Source.*;

public class MarcheGros implements Runnable{
    
    private final int portMarcheGros;
    private Messenger gestionMessage;

    public MarcheGros(int portMarcheGros) 
    {
        this.portMarcheGros = portMarcheGros;
        this.gestionMessage=new Messenger("Serveur Marche Gros");
    }

    @Override
    public void run()
    {
        System.out.println("Serveur MarcheGros started");
    }
}
