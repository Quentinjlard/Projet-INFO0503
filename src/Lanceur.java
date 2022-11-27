import ami.*;
import marchegros.*;
import pone.*;
import source.ClientTCP;
import source.Configuration;
import source.ServeurTCP;
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

        String adresseElecticiteTareTCP = config.getString("adresseElecticiteTareTCP");
        int portElecticiteTareTCP = config.getInt( "portElecticiteTareTCP");
        String adresseElecticitePoneUDP = config.getString("adresseElecticitePoneUDP");
        int portElecticitePoneUDP = config.getInt("portElecticitePoneUDP");

        String adresseGazTareTCP = config.getString("adresseGazTareTCP");
        int portGazTareTCP = config.getInt("portGazTareTCP");
        String adresseGazPoneUDP = config.getString("adresseGazPoneUDP");
        int portGazPoneUDP = config.getInt("portGazPoneUDP");

        String adressePetroleTareTCP = config.getString("adressePetroleTareTCP");
        int portPetroleTareTCP = config.getInt("portPetroleTareTCP");
        String adressePetrolePoneUDP = config.getString("adressePetrolePoneUDP");
        int portPetrolePoneUDP = config.getInt("portPetrolePoneUDP");

        String adresseMarcheGros = config.getString("adresseMarcheGros");
        int portMarcheGros = config.getInt("portMarcheGros");

        String adresseAMI = config.getString("adresseAMI");
        int portAMI = config.getInt("portAMI");

        java.util.ArrayList<Thread> mesServices = new java.util.ArrayList<Thread>();

	// On doit donner une référence d'objet implémentant l'interface Runnable pour créer un Thread 
        // mesServices.add(new Thread(new ServeurTCP(portServeurTCP)));
        // mesServices.add(new Thread(new ClientTCP(adresseServeurTCP,portServeurTCP)));

        mesServices.add(new Thread(new ElectriciteTare(portElecticiteTareTCP)));
        // mesServices.add(new Thread(new ClientTCP(adresseElecticiteTareTCP,portElecticiteTareTCP)));
        mesServices.add(new Thread(new ElectricitePone(portElecticitePoneUDP)));
        // mesServices.add(new Thread(new ClientTCP(adresseServeurTCP,portServeurTCP)));

        mesServices.add(new Thread(new GazTare(portGazTareTCP)));
        // mesServices.add(new Thread(new ClientTCP(adresseServeurTCP,portServeurTCP)));
        mesServices.add(new Thread(new GazPone(portGazPoneUDP)));
        // mesServices.add(new Thread(new ClientTCP(adresseServeurTCP,portServeurTCP)))

        mesServices.add(new Thread(new PetroleTare(portPetroleTareTCP)));
        // mesServices.add(new Thread(new ClientTCP(adresseServeurTCP,portServeurTCP)));
        mesServices.add(new Thread(new PetrolePone(portPetrolePoneUDP)));
        // mesServices.add(new Thread(new ClientTCP(adresseServeurTCP,portServeurTCP)));

        mesServices.add(new Thread(new MarcheGros(portMarcheGros)));
        // mesServices.add(new Thread(new ClientTCP(adresseServeurTCP,portServeurTCP)));

        mesServices.add(new Thread(new AMI(portAMI)));
        // mesServices.add(new Thread(new ClientTCP(adresseServeurTCP,portServeurTCP)));


        java.util.Iterator<Thread> it=mesServices.iterator();
        // Cela fonctionne ici car le serveur est démarré avant le client
        // Il faudrait mieux appeler spécifiquement chaque Thread (notamment dans le projet)
        while(it.hasNext()) {
            Thread thread = it.next();
            thread.start();
        }
    }
}
