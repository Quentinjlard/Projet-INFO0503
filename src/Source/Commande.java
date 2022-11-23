package Source;

import org.json.*;

public class Commande {
    private String TypeEnergie;
    private int QuantiteDemander;
    private String ModeExtraction;
    private int PrixTotals;
    private int NumeroDeLot;

    public Commande(int NumeroDeLot, String TypeEnergie,String ModeExtraction,int QuantiteDemander, int PrixTotals) {
        this.NumeroDeLot = NumeroDeLot;
        this.TypeEnergie = TypeEnergie;
        this.QuantiteDemander = QuantiteDemander;
        this.ModeExtraction = ModeExtraction;
        this.PrixTotals = PrixTotals;
    }

    public static String[] Split(String reference)
    {
        String[] partiRef = reference.split("&");
        return partiRef;
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
        json.put("NumeroDeLot", this.NumeroDeLot);
        json.put("TypeEnergie", this.TypeEnergie);
        json.put("QuantiteDemander", this.QuantiteDemander);
        json.put("ModeExtraction", this.ModeExtraction);
        json.put("PrixTotals", this.PrixTotals);

        return json;
    }

    public static Commande FromJSON(String json)
    {
        JSONObject objet = new JSONObject(json);
        int NumeroDeLot = objet.getInt("NumeroDeLot");
        String TypeEnergie = objet.getString("TypeEnergie");
        int QuantiteDemander = objet.getInt("QuantiteDemander");
        String ModeExtraction = objet.getString("ModeExtraction");
        int PrixTotals = objet.getInt("PrixTotals");
        Commande code = new Commande(
                            NumeroDeLot,
                            TypeEnergie, 
                            ModeExtraction, 
                            QuantiteDemander,
                            PrixTotals
                            );

        return code;
    }

    @Override
    public String toString() {
            System.out.println("\n"+
                                "-  => NumeroDeLot : " + this.NumeroDeLot + "\n" +
                                "-  => Type Enegie :" + this.TypeEnergie +"\n"+
                                "-  => Mode extraction :" + this.ModeExtraction +"\n"+
                                "-  => Quantite Envoyer : " + this.QuantiteDemander +"\n"+
                                "-  => Prix Total :" + this.PrixTotals+ "\n"
                                );
        return super.toString();
    }
}
