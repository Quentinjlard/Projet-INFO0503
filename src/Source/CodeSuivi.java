package Source;

import org.json.*;
import java.util.Scanner;
import java.io.*;

public class CodeSuivi implements Serializable
{
    private int IdClient;
    private int IdRevendeur;
    private int IdTare;
    private String TypeEnergie;
    private int QuantiteMin;
    private int QuantiteDemander;
    private String ModeExtraction;
    private String OrigineGeographique;
    private int PrixUnits;
    private int PrixTotals;

    public CodeSuivi(int IdClient, int IdRevendeur, int IdTare, String TypeEnergie, int QuantiteMin, int QuantiteDemander, String ModeExtraction, String OrigineGeographique, int PrixUnits, int PrixTotals) {
        this.IdClient = IdClient;
        this.IdRevendeur = IdRevendeur;
        this.IdTare = IdTare;
        this.TypeEnergie = TypeEnergie;
        this.QuantiteMin = QuantiteMin;
        this.QuantiteDemander = QuantiteDemander;
        this.ModeExtraction = ModeExtraction;
        this.OrigineGeographique = OrigineGeographique;
        this.PrixUnits = PrixUnits;
        this.PrixTotals = PrixTotals;
    }

    public static CodeSuivi CreeCodeSuivi()
    {
        Scanner sc = new Scanner(System.in);

        System.out.print("Id client : ");
        String IdClientString = sc.nextLine();
        int IdClient = Integer.parseInt(IdClientString);
        System.out.print("Id revendeur : ");
        String IdRevendeurString = sc.nextLine();
        int IdRevendeur = Integer.parseInt(IdRevendeurString);
        System.out.print("Id Tare : ");
        String idTareString = sc.nextLine();
        int IdTare = Integer.parseInt(idTareString);
        System.out.print("Type Energie : ");
        String TypeEnergie = sc.nextLine();
        System.out.print("Quantite min : ");
        String QuantiteMinString = sc.nextLine();
        int QuantiteMin = Integer.parseInt(QuantiteMinString);
        System.out.print("Quantite demande : ");
        String QuantiteDemanderString = sc.nextLine();
        int QuantiteDemander = Integer.parseInt(QuantiteDemanderString);
        System.out.print("Origine Geographique : ");
        String OrigineGeographique = sc.nextLine();
        System.out.print("Mode extrraction : ");
        String ModeExtractions = sc.nextLine();
        int PrixUnits = 0;
        int PrixTotals = 0;
    

        sc.close();

        CodeSuivi CreeCodeSuivi = new CodeSuivi(IdClient, IdRevendeur, IdTare, TypeEnergie, QuantiteMin, QuantiteDemander, ModeExtractions, OrigineGeographique, PrixUnits, PrixTotals);

        return CreeCodeSuivi;
    }

    public static String[] Split(String reference)
    {
        String[] partiRef = reference.split("-");
        return partiRef;
    }

    public int getIdClient()
    {
        return this.IdClient;
    }
    public int getIdRevendeur()
    {
        return this.IdRevendeur;
    }
    public int getIdTare()
    {
        return this.IdTare;
    }
    public String getTypeEnergie()
    {
        return this.TypeEnergie;
    }
    public int getQuantiteMin()
    {
        return this.QuantiteMin;
    }
    public int getQuantiteDemander()
    {
        return this.QuantiteDemander;
    }
    public String getModeExtraction()
    {
        return this.ModeExtraction;
    }
    public String getOrigineGeographique()
    {
        return this.OrigineGeographique;
    }
    public int getPrixUnits()
    {
        return this.PrixUnits;
    }
    public int getPrixTotals()
    {
        return this.PrixTotals;
    }

    public JSONObject toJson()
    {
        JSONObject json = new JSONObject();
        json.put("IdClient", this.IdClient);
        json.put("IdRevendeur", this.IdRevendeur);
        json.put("IdTare", this.IdTare);
        json.put("TypeEnergie", this.TypeEnergie);
        json.put("QuantiteMin", this.QuantiteMin);
        json.put("QuantiteDemander", this.QuantiteDemander);
        json.put("ModeExtraction", this.ModeExtraction);
        json.put("OrigineGeographique", this.OrigineGeographique);
        json.put("PrixUnits", this.PrixUnits);
        json.put("PrixTotals", this.PrixTotals);

        return json;
    }

    public static CodeSuivi FromJSON(String json)
    {
        JSONObject objet = new JSONObject(json);
        int IdClient = objet.getInt("IdClient");
        int IdRevendeur = objet.getInt("IdRevendeur");
        int IdTare = objet.getInt("IdTare");
        String TypeEnergie = objet.getString("TypeEnergie");
        int QuantiteMin = objet.getInt("QuantiteMin");
        int QuantiteDemander = objet.getInt("QuantiteDemander");
        String ModeExtraction = objet.getString("ModeExtraction");
        String OrigineGeographique = objet.getString("OrigineGeographique");
        int PrixUnits = objet.getInt("PrixUnits");
        int PrixTotals = objet.getInt("PrixTotals");
        CodeSuivi code = new CodeSuivi(
                            IdClient, 
                            IdRevendeur, 
                            IdTare, 
                            TypeEnergie, 
                            QuantiteMin, 
                            QuantiteDemander, 
                            ModeExtraction, 
                            OrigineGeographique, 
                            PrixUnits,
                            PrixTotals
                            );

        return code;
    }

    @Override
    public String toString() {
            System.out.println("\n"+
                                "  => Id Client : " + this.IdClient +"\n"+
                                "  => Id Revendeur :" + this.IdRevendeur +"\n"+
                                "  => Id TARE : " + this.IdTare +"\n"+
                                "  => Type Enegie :" + this.TypeEnergie +"\n"+
                                "  => Quantite Min :" + this.QuantiteMin +"\n"+
                                "  => Quantite Demander : " + this.QuantiteDemander +"\n"+
                                "  => Mode extraction :" + this.ModeExtraction +"\n"+
                                "  => Origine Geographique : " + this.OrigineGeographique +"\n"+
                                "  => Prix d'une unite :" + this.PrixUnits +"\n"+
                                "  => Prix Total :" + this.PrixTotals+ "\n"
                                );
        return super.toString();
    }

    // @Override
    // public int compareTo(Object code) {

    //     if (((CodeDeSuivi)code).getQuantiteDemander() == this.QuantiteDemander) {
    //         return 0;
    //     } else if (((CodeDeSuivi)code).getQuantiteDemander() < this.QuantiteDemander) {
    //         return 1;
    //     }
    //     return -1;
    // }
}
