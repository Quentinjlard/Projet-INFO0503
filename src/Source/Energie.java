package Source;

import org.json.*;

public class Energie {
    private String TypeEnergie;
    private int QuantiteEnvoyer;
    private String ModeExtraction;
    private int PrixTotals;
    private int NumeroDeLot;

    public Energie(int NumeroDeLot, String TypeEnergie,String ModeExtraction,int QuantiteEnvoyer, int PrixTotals) {
        this.NumeroDeLot = NumeroDeLot;
        this.TypeEnergie = TypeEnergie;
        this.QuantiteEnvoyer = QuantiteEnvoyer;
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

    public int getQuantiteEnvoyer()
    {
        return this.QuantiteEnvoyer;
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
        json.put("QuantiteEnvoyer", this.QuantiteEnvoyer);
        json.put("ModeExtraction", this.ModeExtraction);
        json.put("PrixTotals", this.PrixTotals);

        return json;
    }

    public static Energie FromJSON(String json)
    {
        JSONObject objet = new JSONObject(json);
        int NumeroDeLot = objet.getInt("NumeroDeLot");
        String TypeEnergie = objet.getString("TypeEnergie");
        int QuantiteEnvoyer = objet.getInt("QuantiteEnvoyer");
        String ModeExtraction = objet.getString("ModeExtraction");
        int PrixTotals = objet.getInt("PrixTotals");
        Energie code = new Energie(
                            NumeroDeLot,
                            TypeEnergie, 
                            ModeExtraction, 
                            QuantiteEnvoyer,
                            PrixTotals
                            );

        return code;
    }

    @Override
    public String toString() {
            return "\n"+
                                "-  => NumeroDeLot : " + this.NumeroDeLot + "\n" +
                                "-  => Type Enegie :" + this.TypeEnergie +"\n"+
                                "-  => Mode extraction :" + this.ModeExtraction +"\n"+
                                "-  => Quantite Envoyer : " + this.QuantiteEnvoyer +"\n"+
                                "-  => Prix Total :" + this.PrixTotals+ "\n"
                                ;
        
    }
}
