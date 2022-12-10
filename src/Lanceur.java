import java.util.concurrent.TimeUnit;

import AMI.*;
import MARCHEGROS.*;
import pone.*;
import source.Configuration;
import tare.*;


/**
 * Lanceur de test pour démarrer une communication TCP (basé sur la fiche 1 du TP sur la communication TCP).
 * 
 * @author Jean-Charles BOISSON 11/2022
 * 
 * @version 1.0
 * 
 */
public class Lanceur {

    /** Constructeur par défaut (pour éviter un warning lors de la génération de la documentation) */
    Lanceur () {}

    /**
     * Code de test : on prépare les Thread et on les lance (serveur puis client).
     * 
     * @param args Les arguments de la ligne de commande notamment pour fournir le chemin vers le fichier de configuration json.
     */
    public static void main(String[] args) {
        
        if(args.length == 0) {
            System.out.println("Merci de donner un fichier de configuration json");
            System.exit(0);
        }
        
        Configuration config     = new Configuration(args[0]);

        String adresseServeurTCP = config.getString("adresseServeurTCP");
        int portServeurTCP       = config.getInt("portServeurTCP");

        int portElecticiteTareUDP = config.getInt( "portElecticiteTareUDP");
        int portElecticitePoneUDP = config.getInt("portElecticitePoneUDP");

        int portGazTareUDP = config.getInt("portGazTareUDP");
        int portGazPoneUDP = config.getInt("portGazPoneUDP");

        int portPetroleTareUDP = config.getInt("portPetroleTareUDP");
        int portPetrolePoneUDP = config.getInt("portPetrolePoneUDP");

        int portMarcheGros = config.getInt("portMarcheGros");

        int portAMI = config.getInt("portAMI");

        java.util.ArrayList<Thread> mesServices = new java.util.ArrayList<Thread>();

	// On doit donner une référence d'objet implémentant l'interface Runnable pour créer un Thread 
        // mesServices.add(new Thread(new ServeurUDP(portServeurUDP)));
        // mesServices.add(new Thread(new ClientUDP(adresseServeurUDP,portServeurUDP)));

        mesServices.add(new Thread(new ElectriciteTare(portElecticiteTareUDP)));
        mesServices.add(new Thread(new ElectricitePone(portElecticitePoneUDP)));

        mesServices.add(new Thread(new GazTare(portGazTareUDP)));
        mesServices.add(new Thread(new GazPone(portGazPoneUDP)));

        mesServices.add(new Thread(new PetroleTare(portPetroleTareUDP)));
        //mesServices.add(new Thread(new PetrolePone(portPetrolePoneUDP)));

        mesServices.add(new Thread(new AMI(portAMI)));
        mesServices.add(new Thread(new MarcheGros(portMarcheGros)));





        java.util.Iterator<Thread> it=mesServices.iterator();
        // Cela fonctionne ici car le serveur est démarré avant le client
        // Il faudrait mieux appeler spécifiquement chaque Thread (notamment dans le projet)
        while(it.hasNext()) {
            Thread thread = it.next();
            thread.start();
        }

        try {
            TimeUnit.SECONDS.sleep(30);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Thread Petrole = new Thread(new PetrolePone(portPetrolePoneUDP));
        Petrole.start();

        
    }
}
