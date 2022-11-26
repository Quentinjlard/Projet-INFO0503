package TARE;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.PrintWriter;

import java.net.ServerSocket;
import java.net.Socket;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.json.JSONObject;

import org.json.JSONObject;

import Source.Messenger;

public class ElectriciteTareTCP implements Runnable{
    
    private int portServeurTCP;
    private Messenger gestionMessage;
    private int portMarcheGros = 4012;

    public ElectriciteTareTCP(int portServeurTCP) {
        this.portServeurTCP=portServeurTCP;
        this.gestionMessage=new Messenger("ElectriciteTARETCP");
    }

    @Override
    public void run()
    {
        System.out.println("Serveur ElectriciteTareTCP started");


        // // Reception d une requete en TCP

        // ServerSocket socketServeur = null;
        // try {    
        //     socketServeur = new ServerSocket(portServeurTCP);
        // } catch(IOException e) {
        //     System.err.println("Création de la socket impossible : " + e);
        //     System.exit(0);
        // }

        // Socket socketClient = null;
        // try {
        //     socketClient = socketServeur.accept();
        // } catch(IOException e) {
        //     System.err.println("Erreur lors de l'attente d'une connexion : " + e);
        //     System.exit(0);
        // }

        // BufferedReader input = null;
        // PrintWriter output = null;
        // try {
        //     input = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
        // } catch(IOException e) {
        //     System.err.println("Association des flux impossible : " + e);
        //     System.exit(0);
        // }

        // JSONObject commandeJSon;
        
        // try {
        //     // Faire le FromJSON
            
        // // } catch(IOException e) {
        //     // System.err.println("Erreur lors de la lecture : " + e);
        //     // System.exit(0);
        // // }
        // // gestionMessage.afficheMessage("Demande reçu par le TARE Electricite" + message);
        
        // try {
        //     input.close();
        //     output.close();
        //     socketClient.close();
        //     socketServeur.close();
        // } catch(IOException e) {
        //     System.err.println("Erreur lors de la fermeture des flux et des sockets : " + e);
        //     System.exit(0);
        // }

        
        // // Socket en UDP

        // // Création de la socket
        // DatagramSocket socket = null;
        // try 
        // {
        //     socket = new DatagramSocket();
        // } 
        // catch(SocketException e) 
        // {
        //     System.err.println("Erreur lors de la création du socket : " + e);
        //     System.exit(0);
        // }
        // // Transformation en tableau d'octets
        
        // ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // try 
        // {
        //     ObjectOutputStream oos = new ObjectOutputStream(baos);
        //     // oos.writeObject(JSON.toString());
        // } 
        // catch(IOException e) 
        // {
        //     System.err.println("Erreur lors de la sérialisation : " + e);
        //     System.exit(0);
        // }

        // // Création et envoi du segment UDP
        // try 
        // {
        //     byte[] donnees = baos.toByteArray();
        //     InetAddress adresse = InetAddress.getByName("localhost");
        //     DatagramPacket msg = new DatagramPacket(donnees, donnees.length, adresse, portMarcheGros);
        //     socket.send(msg);
        // } 
        // catch(UnknownHostException e) 
        // {
        //     System.err.println("Erreur lors de la création de l'adresse : " + e);
        //     System.exit(0); 
        // } 
        // catch(IOException e) 
        // {
        //     System.err.println("Erreur lors de l'envoi du message : " + e);
        //     System.exit(0);
        // }
        // // gestionMessage.afficheMessage("J'ai fournis le lot d'energie suivant : " + commande.getNumeroDeLot());

        // socket.close();
            
        // try {
        //     Thread.sleep(30000);
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }
    }
}
