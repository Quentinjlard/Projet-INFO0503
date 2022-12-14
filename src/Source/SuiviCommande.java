package source;

import org.json.*;
import java.util.Scanner;
import java.io.*;

public class SuiviCommande implements Serializable
{
    private int idClient;
    private int idRevendeur;
    private int idTare;
    private int numeroDeCommande;
    private int numeroDeLot;
    private String typeEnergie;
    private String modeExtraction;
    private int quantiteDemander;
    private int quantiteEnvoyer;
    private int prixUnites;
    private int prixTotal;

    public SuiviCommande(int idClient,int idRevendeur,int idTare,int numeroDeCommande,int numeroDeLot, String typeEnergie,String modeExtraction,int quantiteDemander,int quantiteEnvoyer,int prixUnites,int prixTotal) {
        this.idClient = idClient;
        this.idRevendeur = idRevendeur;
        this.idTare = idTare;
        this.numeroDeCommande = numeroDeCommande;
        this.numeroDeLot = numeroDeLot;
        this.typeEnergie = typeEnergie; 
        this.modeExtraction = modeExtraction;   
        this.quantiteDemander = quantiteDemander;
        this.quantiteEnvoyer = quantiteEnvoyer;
        this.prixUnites = prixUnites;
        this.prixTotal = prixTotal;
    }

    public static String[] Split(String reference)
    {
        String[] partiRef = reference.split("-");
        return partiRef;
    }

    public int getIdClient() {
        return idClient;
    }
    public int getIdRevendeur() {
        return idRevendeur;
    }
    public int getIdTare() {
        return idTare;
    }
    public String getModeExtraction() {
        return modeExtraction;
    }
    public int getNumeroDeCommande() {
        return numeroDeCommande;
    }
    public int getPrixTotal() {
        return prixTotal;
    }
    public int getPrixUnites() {
        return prixUnites;
    }
    public int getQuantiteDemander() {
        return quantiteDemander;
    }
    public int getQuantiteEnvoyer() {
        return quantiteEnvoyer;
    }
    public String getTypeEnergie() {
        return typeEnergie;
    }
    public int getNumeroDeLot() 
    {
        return numeroDeLot;
    }

    public JSONObject toJson()
    {
        JSONObject json = new JSONObject();
        json.put("IdClient", this.idClient);
        json.put("IdRevendeur", this.idRevendeur);
        json.put("IdTare", this.idTare);
        json.put("NumeroDeCommande", this.numeroDeCommande);
        json.put("NumeroDeLot", this.numeroDeLot);
        json.put("TypeEnergie", this.typeEnergie);
        json.put("ModeExtraction", this.modeExtraction);
        json.put("QuantiteDemander", this.quantiteDemander);
        json.put("QuantiteEnvoyer", this.quantiteEnvoyer);
        json.put("PrixUnites", this.prixUnites);
        json.put("PrixTotal", this.prixTotal);

        return json;
    }

    public static SuiviCommande FromJSON(String json)
    {
        JSONObject objet = new JSONObject(json);

        int IdClient = objet.getInt("IdClient");
        int IdRevendeur = objet.getInt("IdRevendeur");
        int IdTare = objet.getInt("IdTare");
        int numeroDeCommande = objet.getInt("NumeroDeCommande");
        int numeroDeLot = objet.getInt("NumeroDeLot");
        String typeEnergie = objet.getString("TypeEnergie");
        String modeExtraction = objet.getString("ModeExtraction");
        int quantiteDemander = objet.getInt("QuantiteDemander");
        int quantiteEnvoyer = objet.getInt("QuantiteEnvoyer");
        int prixUnites = objet.getInt("PrixUnites");
        int prixTotal = objet.getInt("PrixTotal");

        SuiviCommande suiviCommande = new SuiviCommande(
                            IdClient, 
                            IdRevendeur, 
                            IdTare, 
                            numeroDeCommande, 
                            numeroDeLot,
                            typeEnergie, 
                            modeExtraction, 
                            quantiteDemander, 
                            quantiteEnvoyer, 
                            prixUnites,
                            prixTotal
                            );
        return suiviCommande;
    }

    @Override
    public String toString() {
            return (                                                                              "\n" +
                                "  => Id Client : "              + this.idClient                + "\n" +
                                "  => Id Revendeur : "           + this.idRevendeur             + "\n" +
                                "  => Id Tare : "                + this.idTare                  + "\n" +
                                "  => Numero de commande : "     + this.numeroDeCommande        + "\n" +
                                "  => Numero de lot : "          + this.numeroDeLot            + "\n" +
                                "  => Type Energie : "           + this.typeEnergie             + "\n" +
                                "  => Mode Extraction : "        + this.modeExtraction          + "\n" +
                                "  => Quantite Demander : "      + this.quantiteDemander        + "\n" +
                                "  => Quantite Envoyer : "       + this.quantiteEnvoyer         + "\n" +
                                "  => Prix Units : "             + this.prixUnites              + "\n" + 
                                "  => Prix Totals : "            + this.prixTotal               + "\n"
                    );
    }

}
