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

            Energie energie = Energie.FromJSON(new String(msgRecu.getData()));
            gestionMessage.afficheMessage("J'ai bien recu le paquet : " + energie.getNumeroDeLot());

            if(energie.getTypeEnergie().equals("Electricite"))
            {
                System.out.print("  => Electricite => ");
                if(energie.getModeExtraction().equals("Nucleaire"))
                {
                    System.out.print("Nucleaire \n");
                    electriciteNucleaire.add(energie);
                }
                else
                {
                    if(energie.getModeExtraction().equals("Eolienne"))
                    {
                        System.out.print("Eolienne \n");
                        electriciteEolienne.add(energie);
                    }
                    else
                    {
                        if(energie.getModeExtraction().equals("Charbon"))
                        {
                            System.out.print("Charbon \n");
                            electriciteCharbon.add(energie);
                        }
                    }
                }
            }
            else
            {
                if(energie.getTypeEnergie().equals("Gaz"))
                {
                    System.out.print("  => Gaz => ");
                    if(energie.getModeExtraction().equals("Naturel"))
                    {
                        System.out.print("Naturel \n");
                        gazNatuel.add(energie);
                    }
                    else
                    {
                        if(energie.getModeExtraction().equals("Butane"))
                        {
                            System.out.print("Butane \n");
                            gazButane.add(energie);
                        }
                        else
                        {
                            if(energie.getModeExtraction().equals("Propane"))
                            {
                                System.out.print("Propane \n");
                                gazPropoane.add(energie);
                            }
                        }
                    }
                }else
                {
                    if(energie.getTypeEnergie().equals("Petrole"))
                    {
                        System.out.print("  => Petrole => ");
                        if(energie.getModeExtraction().equals("Diesel"))
                        {
                            System.out.println("Diesel \n");
                            petroleDiesel.add(energie);
                        }
                        else
                        {
                            if(energie.getModeExtraction().equals("SP98"))
                            {
                                System.out.print("SP98 \n");
                                petroleSP98.add(energie);
                            }
                            else
                            {
                                if(energie.getModeExtraction().equals("SP95"))
                                {
                                    System.out.print("SP95 \n");
                                    petroleSP95.add(energie);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
