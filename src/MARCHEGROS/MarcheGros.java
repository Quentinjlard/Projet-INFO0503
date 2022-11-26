package MARCHEGROS;

import java.beans.Encoder;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.json.JSONObject;


import Source.*;

public class MarcheGros implements Runnable{
    
    private final int portMarcheGros;
    private Messenger gestionMessage;
    private int portEcoute = 4012;
    private int portEcouteTareElectricite = 1021;
    private int portEcouteTareGaz = 1022;
    private int portEcouteTarePetrole = 1023;

    public MarcheGros(int portMarcheGros) 
    {
        this.portMarcheGros = portMarcheGros;
        this.gestionMessage=new Messenger("Serveur Marche Gros");
    }

    @Override
    public void run()
    {
        System.out.println("Serveur MarcheGros started");

        // gestionMessage.afficheMessage("J'ai bien recu le paquet : " + message);

        ArrayList<Energie> electriciteNucleaire = new ArrayList<Energie>();
        ArrayList<Energie> electriciteEolienne = new ArrayList<Energie>();
        ArrayList<Energie> electriciteCharbon = new ArrayList<Energie>();
        ArrayList<Energie> gazNatuel = new ArrayList<Energie>();
        ArrayList<Energie> gazPropoane = new ArrayList<Energie>();
        ArrayList<Energie> gazButane = new ArrayList<Energie>();
        ArrayList<Energie> petroleDiesel = new ArrayList<Energie>();
        ArrayList<Energie> petroleSP95 = new ArrayList<Energie>();
        ArrayList<Energie> petroleSP98 = new ArrayList<Energie>();

        // Création de la socket
        DatagramSocket socket = null;
        try {        
            socket = new DatagramSocket(portEcoute);
        } catch(SocketException e) {
            System.err.println("Erreur lors de la création de la socket : " + e);
            System.exit(0);
        }

        // Lecture du message du client
        while(true)
        {
            // Lecture du message du client
            DatagramPacket msgRecu = null;
            try {
                byte[] tampon = new byte[1024];
                msgRecu = new DatagramPacket(tampon, tampon.length);
                socket.receive(msgRecu);
            } catch(IOException e) {
                System.err.println("Erreur lors de la réception du message : " + e);
                System.exit(0);
            }
            String msgRecuStroString = new String(msgRecu.getData());
            String test = msgRecuStroString.substring(2, 13);
            Energie energie = null;
            Commande commande = null;

            if(test.equals("NumeroDeLot"))
            {
                energie = Energie.FromJSON(msgRecuStroString);
                gestionMessage.afficheMessage("J'ai bien recu le paquet : " + energie.getNumeroDeLot());
            }
            else
            {
                commande = Commande.FromJSON(msgRecuStroString);
                gestionMessage.afficheMessage("J'ai bien recu la commande : " + commande.getNumeroDeCommande());
            }
            
            

            if(energie.getTypeEnergie().equals("Electricite"))
                if(energie.getModeExtraction().equals("Nucleaire"))
                    electriciteNucleaire.add(energie);
                else
                    if(energie.getModeExtraction().equals("Eolienne"))
                        electriciteEolienne.add(energie);
                    else
                        if(energie.getModeExtraction().equals("Charbon"))
                            electriciteCharbon.add(energie);
            else
                if(energie.getTypeEnergie().equals("Gaz"))
                    if(energie.getModeExtraction().equals("Naturel"))
                        gazNatuel.add(energie);
                    else
                        if(energie.getModeExtraction().equals("Butane"))
                            gazButane.add(energie);
                        else
                            if(energie.getModeExtraction().equals("Propane"))
                                gazPropoane.add(energie);
                else
                    if(energie.getTypeEnergie().equals("Petrole"))
                        if(energie.getModeExtraction().equals("Diesel"))
                            petroleDiesel.add(energie);
                        else
                            if(energie.getModeExtraction().equals("SP98"))
                                petroleSP98.add(energie);
                            else
                                if(energie.getModeExtraction().equals("SP95"))
                                    petroleSP95.add(energie);
            
            if(commande != null)
            {
                String TypeEnergie = commande.getTypeEnergie();
                int QuantiteDemander = commande.getQuantiteDemander();
                String ModeExtraction = commande.getModeExtraction();

                Commande reponseCommande = null;
                int portTare = 0000;

                if(TypeEnergie.equals("Electricite") && ModeExtraction.equals("Nucleaire"))
                    for( Energie n : electriciteNucleaire)
                        if(n.getQuantiteEnvoyer() >= QuantiteDemander)
                        {
                            reponseCommande = new Commande  (
                                                                commande.getNumeroDeCommande(),
                                                                commande.getTypeEnergie(),
                                                                commande.getModeExtraction(),
                                                                n.getQuantiteEnvoyer(),
                                                                n.getQuantiteEnvoyer() * n.getPrixTotals(),
                                                                n.getNumeroDeLot()
                                                            );
                            electriciteNucleaire.remove(n);
                        }
                        
                if(TypeEnergie.equals("Electricite") && ModeExtraction.equals("Eolienne"))
                    for( Energie n : electriciteEolienne)
                        if(n.getQuantiteEnvoyer() >= QuantiteDemander)
                        {
                            reponseCommande = new Commande  (
                                                                commande.getNumeroDeCommande(),
                                                                commande.getTypeEnergie(),
                                                                commande.getModeExtraction(),
                                                                n.getQuantiteEnvoyer(),
                                                                n.getQuantiteEnvoyer() * n.getPrixTotals(),
                                                                n.getNumeroDeLot()
                                                            );
                            electriciteEolienne.remove(n);
                        }
                if(TypeEnergie.equals("Electricite") && ModeExtraction.equals("Charbon"))
                    for( Energie n : electriciteCharbon)
                        if(n.getQuantiteEnvoyer() >= QuantiteDemander)
                        {
                            reponseCommande = new Commande (commande.getNumeroDeCommande(),commande.getTypeEnergie(),commande.getModeExtraction(),n.getQuantiteEnvoyer(),n.getQuantiteEnvoyer() * n.getPrixTotals(),n.getNumeroDeLot());
                            electriciteCharbon.remove(n);
                        }

                if(TypeEnergie.equals("Gaz") && ModeExtraction.equals("Natuel"))
                    for( Energie n : gazNatuel)
                        if(n.getQuantiteEnvoyer() >= QuantiteDemander)
                        {
                            reponseCommande = new Commande (commande.getNumeroDeCommande(),commande.getTypeEnergie(),commande.getModeExtraction(),n.getQuantiteEnvoyer(),n.getQuantiteEnvoyer() * n.getPrixTotals(),n.getNumeroDeLot());
                            gazNatuel.remove(n);
                        }
                
                if(TypeEnergie.equals("Gaz") && ModeExtraction.equals("Butane"))
                    for( Energie n : gazButane)
                        if(n.getQuantiteEnvoyer() >= QuantiteDemander)
                        {
                            reponseCommande = new Commande (commande.getNumeroDeCommande(),commande.getTypeEnergie(),commande.getModeExtraction(),n.getQuantiteEnvoyer(),n.getQuantiteEnvoyer() * n.getPrixTotals(),n.getNumeroDeLot());
                            gazButane.remove(n);
                        }
                
                if(TypeEnergie.equals("Gaz") && ModeExtraction.equals("Propane"))
                    for( Energie n : gazPropoane)
                        if(n.getQuantiteEnvoyer() >= QuantiteDemander)
                        {
                            reponseCommande = new Commande (commande.getNumeroDeCommande(),commande.getTypeEnergie(),commande.getModeExtraction(),n.getQuantiteEnvoyer(),n.getQuantiteEnvoyer() * n.getPrixTotals(),n.getNumeroDeLot());
                            gazPropoane.remove(n);
                        }

                if(TypeEnergie.equals("Petrole") && ModeExtraction.equals("Diesel"))
                    for( Energie n : petroleDiesel)
                        if(n.getQuantiteEnvoyer() >= QuantiteDemander)
                        {
                            reponseCommande = new Commande (commande.getNumeroDeCommande(),commande.getTypeEnergie(),commande.getModeExtraction(),n.getQuantiteEnvoyer(),n.getQuantiteEnvoyer() * n.getPrixTotals(),n.getNumeroDeLot());
                            petroleDiesel.remove(n);
                        }

                if(TypeEnergie.equals("Petrole") && ModeExtraction.equals("SP98"))
                    for( Energie n : petroleSP98)
                        if(n.getQuantiteEnvoyer() >= QuantiteDemander)
                        {
                            reponseCommande = new Commande (commande.getNumeroDeCommande(),commande.getTypeEnergie(),commande.getModeExtraction(),n.getQuantiteEnvoyer(),n.getQuantiteEnvoyer() * n.getPrixTotals(),n.getNumeroDeLot());
                            petroleSP98.remove(n);
                        }
                        
                if(TypeEnergie.equals("Petrole") && ModeExtraction.equals("SP95"))
                    for( Energie n : petroleSP95)
                        if(n.getQuantiteEnvoyer() >= QuantiteDemander)
                        {
                            reponseCommande = new Commande (commande.getNumeroDeCommande(),commande.getTypeEnergie(),commande.getModeExtraction(),n.getQuantiteEnvoyer(),n.getQuantiteEnvoyer() * n.getPrixTotals(),n.getNumeroDeLot());
                            petroleSP95.remove(n);
                        }

                if(TypeEnergie.equals("Petrole"))
                    portTare = 1023;
                else
                    if(TypeEnergie.equals("Gaz"))
                        portTare = 1022;
                    else
                        if(TypeEnergie.equals("Electricite"))
                            portTare = 1021;

                if(reponseCommande != null && portTare != 0000)
                {
                    try 
                    {
                        byte[] donnees = reponseCommande.toString().getBytes();

                        InetAddress adresse = InetAddress.getByName("localhost");
                        DatagramPacket msg = new DatagramPacket(donnees, donnees.length, adresse, portTare);
                        socket.send(msg);
                    } 
                    catch(UnknownHostException e) 
                    {
                        System.err.println("Erreur lors de la création de l'adresse : " + e);
                        System.exit(0); 
                    } 
                    catch(IOException e) 
                    {
                        System.err.println("Erreur lors de l'envoi du message : " + e);
                        System.exit(0);
                    }
                    
                    gestionMessage.afficheMessage("J'ai fournis la commande suivant : " + reponseCommande.getNumeroDeCommande());
                    
                    socket.close();
                }
                
            }
        }
    }
}
