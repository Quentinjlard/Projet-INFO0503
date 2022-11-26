package Source;

import org.json.*;

public class Commande {
    private int numeroDeCommande;
    private String TypeEnergie;
    private int QuantiteDemander;
    private String ModeExtraction;
    private int PrixTotals;
    private int NumeroDeLot;

    public Commande(int numeroDeCommande, String TypeEnergie,String ModeExtraction,int QuantiteDemander, int PrixTotals, int NumeroDeLot) {
        this.numeroDeCommande = numeroDeCommande;
        this.TypeEnergie = TypeEnergie;
        this.QuantiteDemander = QuantiteDemander;
        this.ModeExtraction = ModeExtraction;
        this.PrixTotals = PrixTotals;
        if(NumeroDeLot == 0)
            this.NumeroDeLot = -1;
        else
            this.NumeroDeLot = NumeroDeLot;
    }

    public static String[] Split(String reference)
    {
        String[] partiRef = reference.split("&");
        return partiRef;
    }

    public int getNumeroDeCommande() {
        return numeroDeCommande;
    }

    public int getNumeroDeLot() {
        return NumeroDeLot;
    }

    public String getTypeEnergie()
    {
        return this.TypeEnergie;
    }

    public int getQuantiteDemander()
    {
        return this.QuantiteDemander;
    }
    public String getModeExtraction()
    {
        return this.ModeExtraction;
    }
    
    public int getPrixTotals()
    {
        return this.PrixTotals;
    }

    public JSONObject toJson()
    {
        JSONObject json = new JSONObject();
        json.put("NumeroDeCommande", this.numeroDeCommande);
        json.put("TypeEnergie", this.TypeEnergie);
        json.put("QuantiteDemander", this.QuantiteDemander);
        json.put("ModeExtraction", this.ModeExtraction);
        json.put("PrixTotals", this.PrixTotals);
        json.put("NumeroDeLot", this.NumeroDeLot);

        return json;
    }

    public static Commande FromJSON(String json)
    {
        JSONObject objet = new JSONObject(json);
        int numeroDeCommande = objet.getInt("NumeroDeCommande");
        String TypeEnergie = objet.getString("TypeEnergie");
        int QuantiteDemander = objet.getInt("QuantiteDemander");
        String ModeExtraction = objet.getString("ModeExtraction");
        int PrixTotals = objet.getInt("PrixTotals");
        int NumeroDeLot = objet.getInt("NumeroDeLot");
        Commande commande = new Commande(
                            numeroDeCommande,
                            TypeEnergie, 
                            ModeExtraction, 
                            QuantiteDemander,
                            PrixTotals,
                            NumeroDeLot
                            );

        return commande;
    }

    @Override
    public String toString() {
            System.out.println("\n"+
                                "-  => NumeroDeCommande : " + this.numeroDeCommande + "\n" +
                                "-  => Type Enegie :" + this.TypeEnergie +"\n"+
                                "-  => Mode extraction :" + this.ModeExtraction +"\n"+
                                "-  => Quantite Envoyer : " + this.QuantiteDemander +"\n"+
                                "-  => Prix Total :" + this.PrixTotals+ "\n" +
                                "-  => NumeroDeLot : " + this.NumeroDeLot + "\n" 
                                );
        return super.toString();
    }
}
