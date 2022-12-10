package marchegros;


import java.io.IOException;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import source.*;

public class MarcheGros implements Runnable{
    
    private int portMarcheGros = 0000;
    private Messenger gestionMessage;
    private int portEcoute = 4012;


    public MarcheGros(int portMarcheGros) 
    {
        this.portMarcheGros = portMarcheGros;
        this.gestionMessage=new Messenger("MarcheGros");
    }

    @Override
    public void run()
    {

        gestionMessage.afficheMessage("Started");
        
        List<Energie> electriciteNucleaire = new ArrayList<Energie>();
        List<Energie> electriciteEolienne = new ArrayList<Energie>();
        List<Energie> electriciteCharbon = new ArrayList<Energie>();
        List<Energie> gazNatuel = new ArrayList<Energie>();
        List<Energie> gazPropoane = new ArrayList<Energie>();
        List<Energie> gazButane = new ArrayList<Energie>();
        List<Energie> petroleDiesel = new ArrayList<Energie>();
        List<Energie> petroleSP95 = new ArrayList<Energie>();
        List<Energie> petroleSP98 = new ArrayList<Energie>();

        

        while(true)
        {
            // Création de la socket
            DatagramSocket socket = null;
            try {        
                socket = new DatagramSocket(portEcoute);
            } catch(SocketException e) {
                gestionMessage.afficheMessage("Erreur lors de la création de la socket : " + e);
                System.exit(0);
            }

            // Lecture du message du client
            DatagramPacket msgRecu = null;
            try {
                byte[] tampon = new byte[1024];
                msgRecu = new DatagramPacket(tampon, tampon.length);
                socket.receive(msgRecu);
            } catch(IOException e) {
                gestionMessage.afficheMessage("Erreur lors de la réception du message : " + e);
                System.exit(0);
            }
            String msgRecuStroString = new String(msgRecu.getData());
            String test = msgRecuStroString.substring(2, 13);
            Energie energie = null;
            SuiviCommande commande = null;

            if(test.equals("NumeroDeLot"))
            {
                energie = Energie.FromJSON(msgRecuStroString);
                gestionMessage.afficheMessage("J'ai bien recu le paquet : " + energie.getNumeroDeLot());
                //gestionMessage.afficheMessage(energie.toString());
            }
            else
            {
                commande = SuiviCommande.FromJSON(msgRecuStroString);
                gestionMessage.afficheMessage("J'ai bien recu la commande : " + commande.getNumeroDeCommande());
            }
            
            
            if(energie != null)
            {
                if(energie.getTypeEnergie().equals("Electricite"))
                    if(energie.getModeExtraction().equals("Nucleaire"))
                        electriciteNucleaire.add(energie);
                    else
                        if(energie.getModeExtraction().equals("Eolienne"))
                            electriciteEolienne.add(energie);
                        else
                            if(energie.getModeExtraction().equals("Charbon"))
                                electriciteCharbon.add(energie);
        
                if(energie.getTypeEnergie().equals("Gaz"))
                    if(energie.getModeExtraction().equals("Naturel"))
                        gazNatuel.add(energie);
                    else
                        if(energie.getModeExtraction().equals("Butane"))
                            gazButane.add(energie);
                        else
                            if(energie.getModeExtraction().equals("Propane"))
                                gazPropoane.add(energie);
                
                if(energie.getTypeEnergie().equals("Petrole"))
                    if(energie.getModeExtraction().equals("Diesel"))
                        petroleDiesel.add(energie);
                    else
                        if(energie.getModeExtraction().equals("SP98"))
                            petroleSP98.add(energie);
                        else
                            if(energie.getModeExtraction().equals("SP95"))
                                petroleSP95.add(energie);
            }
            
            if(commande != null)
            {
                String TypeEnergie = commande.getTypeEnergie();
                int QuantiteDemander = commande.getQuantiteDemander();
                String ModeExtraction = commande.getModeExtraction();

                SuiviCommande reponseCommande = null;
                int portTare = 0000;

                if(TypeEnergie.equals("Electricite") && (ModeExtraction.equals("Nucleaire") || ModeExtraction.equals("Aucune restriction")))
                    for (int i = 0; i < electriciteNucleaire.size(); i++)
                        if((electriciteNucleaire.get(i)).getQuantiteEnvoyer() >= QuantiteDemander)
                        {
                            Energie nrj = electriciteNucleaire.get(i);
                            reponseCommande = new SuiviCommande( 
                                commande.getIdClient(), 
                                2, 
                                1021, 
                                commande.getNumeroDeCommande(),  
                                nrj.getNumeroDeLot(),
                                commande.getTypeEnergie(),
                                commande.getModeExtraction(),
                                commande.getQuantiteDemander(),
                                nrj.getQuantiteEnvoyer(), 
                                nrj.getPrixUnite(),
                                nrj.getQuantiteEnvoyer() * nrj.getPrixUnite()
                                );
                            electriciteNucleaire.remove(nrj);
                        }
                        
                if(TypeEnergie.equals("Electricite") && (ModeExtraction.equals("Eolienne") || ModeExtraction.equals("Aucune restriction")))
                    for (int i = 0; i < electriciteEolienne.size(); i++)
                        if((electriciteEolienne.get(i)).getQuantiteEnvoyer() >= QuantiteDemander)
                        {
                            Energie nrj = electriciteEolienne.get(i);
                            reponseCommande = new SuiviCommande(commande.getIdClient(), 2, 1021, commande.getNumeroDeCommande(),  nrj.getNumeroDeLot(),commande.getTypeEnergie(),commande.getModeExtraction(),commande.getQuantiteDemander(),nrj.getQuantiteEnvoyer(), nrj.getPrixUnite(),nrj.getQuantiteEnvoyer() * nrj.getPrixUnite());
                            electriciteEolienne.remove(nrj);
                        }

                if(TypeEnergie.equals("Electricite") && (ModeExtraction.equals("Charbon") || ModeExtraction.equals("Aucune restriction")))
                    for (int i = 0; i < electriciteCharbon.size(); i++)
                        if((electriciteCharbon.get(i)).getQuantiteEnvoyer() >= QuantiteDemander)
                        {
                            Energie nrj = electriciteCharbon.get(i);
                            reponseCommande = new SuiviCommande(commande.getIdClient(), 2, 1021, commande.getNumeroDeCommande(),  nrj.getNumeroDeLot(),commande.getTypeEnergie(),commande.getModeExtraction(),commande.getQuantiteDemander(),nrj.getQuantiteEnvoyer(), nrj.getPrixUnite(),nrj.getQuantiteEnvoyer() * nrj.getPrixUnite());
                            electriciteCharbon.remove(nrj);
                        }

                if(TypeEnergie.equals("Gaz") && (ModeExtraction.equals("Naturel") || ModeExtraction.equals("Aucune restriction")))
                    for (int i = 0; i < gazNatuel.size(); i++)
                        if((gazNatuel.get(i)).getQuantiteEnvoyer() >= QuantiteDemander)
                        {
                            Energie nrj = gazNatuel.get(i);
                            reponseCommande = new SuiviCommande(commande.getIdClient(), 2, 1022, commande.getNumeroDeCommande(),  nrj.getNumeroDeLot(),commande.getTypeEnergie(),commande.getModeExtraction(),commande.getQuantiteDemander(),nrj.getQuantiteEnvoyer(), nrj.getPrixUnite(),nrj.getQuantiteEnvoyer() * nrj.getPrixUnite());
                            gazNatuel.remove(nrj);
                        }
                
                if(TypeEnergie.equals("Gaz") && (ModeExtraction.equals("Butane") || ModeExtraction.equals("Aucune restriction")))
                    for (int i = 0; i < gazButane.size(); i++)
                        if((gazButane.get(i)).getQuantiteEnvoyer() >= QuantiteDemander)
                        {
                            Energie nrj = gazButane.get(i);
                            reponseCommande = new SuiviCommande(commande.getIdClient(), 2, 1022, commande.getNumeroDeCommande(),  nrj.getNumeroDeLot(),commande.getTypeEnergie(),commande.getModeExtraction(),commande.getQuantiteDemander(),nrj.getQuantiteEnvoyer(), nrj.getPrixUnite(),nrj.getQuantiteEnvoyer() * nrj.getPrixUnite());
                            gazButane.remove(nrj);
                        }
                
                if(TypeEnergie.equals("Gaz") && (ModeExtraction.equals("Propane") || ModeExtraction.equals("Aucune restriction")))
                    for (int i = 0; i < gazPropoane.size(); i++)
                        if((gazPropoane.get(i)).getQuantiteEnvoyer() >= QuantiteDemander)
                        {
                            Energie nrj = gazPropoane.get(i);
                            reponseCommande = new SuiviCommande(commande.getIdClient(), 2, 1022, commande.getNumeroDeCommande(),  nrj.getNumeroDeLot(),commande.getTypeEnergie(),commande.getModeExtraction(),commande.getQuantiteDemander(),nrj.getQuantiteEnvoyer(), nrj.getPrixUnite(),nrj.getQuantiteEnvoyer() * nrj.getPrixUnite());
                            gazPropoane.remove(nrj);
                        }

                if(TypeEnergie.equals("Petrole") && (ModeExtraction.equals("Diesel") || ModeExtraction.equals("Aucune restriction")))
                    for (int i = 0; i < petroleDiesel.size(); i++)
                        if((petroleDiesel.get(i)).getQuantiteEnvoyer() >= QuantiteDemander)
                        {
                            Energie nrj = petroleDiesel.get(i);
                            reponseCommande = new SuiviCommande(commande.getIdClient(), 2, 1023, commande.getNumeroDeCommande(),  nrj.getNumeroDeLot(),commande.getTypeEnergie(),commande.getModeExtraction(),commande.getQuantiteDemander(),nrj.getQuantiteEnvoyer(), nrj.getPrixUnite(),nrj.getQuantiteEnvoyer() * nrj.getPrixUnite());
                            petroleDiesel.remove(nrj);
                        }

                if(TypeEnergie.equals("Petrole") && (ModeExtraction.equals("SP98") || ModeExtraction.equals("Aucune restriction")))
                    for (int i = 0; i < petroleSP98.size(); i++)
                        if((petroleSP98.get(i)).getQuantiteEnvoyer() >= QuantiteDemander)
                        {
                            Energie nrj = petroleSP98.get(i);
                            reponseCommande = new SuiviCommande(commande.getIdClient(), 2, 1023, commande.getNumeroDeCommande(),  nrj.getNumeroDeLot(),commande.getTypeEnergie(),commande.getModeExtraction(),commande.getQuantiteDemander(),nrj.getQuantiteEnvoyer(), nrj.getPrixUnite(),nrj.getQuantiteEnvoyer() * nrj.getPrixUnite());
                            petroleSP98.remove(nrj);
                        }
                        
                if(TypeEnergie.equals("Petrole") && (ModeExtraction.equals("SP95") || ModeExtraction.equals("Aucune restriction")))
                    for (int i = 0; i < petroleSP95.size(); i++)
                        if((petroleSP95.get(i)).getQuantiteEnvoyer() >= QuantiteDemander)
                        {
                            Energie nrj = petroleSP95.get(i);
                            reponseCommande = new SuiviCommande(commande.getIdClient(), 2, 1023, commande.getNumeroDeCommande(),  nrj.getNumeroDeLot(),commande.getTypeEnergie(),commande.getModeExtraction(),commande.getQuantiteDemander(),nrj.getQuantiteEnvoyer(), nrj.getPrixUnite(),nrj.getQuantiteEnvoyer() * nrj.getPrixUnite());
                            petroleSP95.remove(nrj);
                        }

                if(TypeEnergie.equals("Petrole"))
                    portTare = 1023;
                else
                    if(TypeEnergie.equals("Gaz"))
                        portTare = 1022;
                    else
                        if(TypeEnergie.equals("Electricite"))
                            portTare = 1021;

                if(reponseCommande == null)
                {
                    reponseCommande = new SuiviCommande  (
                                                        commande.getIdClient(), 
                                                        2, 
                                                        portTare, 
                                                        commande.getNumeroDeCommande(),  
                                                        -1,
                                                        commande.getTypeEnergie(),
                                                        commande.getModeExtraction(),
                                                        commande.getQuantiteDemander(),
                                                        0, 
                                                        0,
                                                        0
                                                    );
                }

                //gestionMessage.afficheMessage(reponseCommande.toString());
                JSONObject reponseCommandeJson = reponseCommande.toJson();

                try 
                {
                    byte[] donnees = reponseCommandeJson.toString().getBytes();

                    InetAddress adresse = InetAddress.getByName("localhost");
                    DatagramPacket msg = new DatagramPacket(donnees, donnees.length, adresse, portTare);
                    socket.send(msg);
                } 
                catch(UnknownHostException e) 
                {
                    gestionMessage.afficheMessage("Erreur lors de la création de l'adresse : " + e);
                    System.exit(0); 
                } 
                catch(IOException e) 
                {
                    gestionMessage.afficheMessage("Erreur lors de l'envoi du message : " + e);
                    System.exit(0);
                }
                
                gestionMessage.afficheMessage("J'ai fournis la commande suivant : " + reponseCommande.getNumeroDeCommande());
                    
                
            }
            socket.close();
        }
        
    }
}
