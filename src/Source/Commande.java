package source;

import org.json.*;

public class Commande {
    private int NumeroDeCommande;
    private String TypeEnergie;
    private int QuantiteDemander;
    private int QuantiteFournis;
    private String ModeExtraction;
    private int PrixTotals;
    private int NumeroDeLot;

    /**
     * 
     * @param NumeroDeCommande
     * @param TypeEnergie
     * @param ModeExtraction
     * @param QuantiteDemander
     * @param PrixTotals
     * @param NumeroDeLot
     */
    public Commande(int NumeroDeCommande, String TypeEnergie,String ModeExtraction,int QuantiteDemander, int QuantiteFournis, int PrixTotals, int NumeroDeLot) {
        this.NumeroDeCommande = NumeroDeCommande;
        this.TypeEnergie = TypeEnergie;
        this.ModeExtraction = ModeExtraction;
        this.QuantiteDemander = QuantiteDemander;
        this.QuantiteFournis = QuantiteFournis;
        this.PrixTotals = PrixTotals;
        this.NumeroDeLot = NumeroDeLot;
    }

    /**
     * 
     * @param reference
     * @return
     */
    public static String[] Split(String reference)
    {
        String[] partiRef = reference.split("&");
        return partiRef;
    }

    /**
     * 
     * @return
     */
    public int getNumeroDeCommande() {
        return NumeroDeCommande;
    }

    /**
     * 
     * @return
     */
    public int getNumeroDeLot() {
        return NumeroDeLot;
    }

    /**
     * 
     * @return
     */
    public String getTypeEnergie()
    {
        return this.TypeEnergie;
    }

    /**
     * 
     * @return
     */
    public int getQuantiteDemander()
    {
        return this.QuantiteDemander;
    }

    /**
     * 
     * @return
     */
    public String getModeExtraction()
    {
        return this.ModeExtraction;
    }
    
    /**
     * 
     * @return
     */
    public int getPrixTotals()
    {
        return this.PrixTotals;
    }

    /**
     * 
     * @return
     */
    public JSONObject toJson()
    {
        JSONObject json = new JSONObject();
        json.put("NumeroDeCommande", this.NumeroDeCommande);
        json.put("TypeEnergie", this.TypeEnergie);
        json.put("ModeExtraction", this.ModeExtraction);
        json.put("QuantiteDemander", this.QuantiteDemander);
        json.put("QuantiteFournis", this.QuantiteFournis);
        json.put("PrixTotals", this.PrixTotals);
        json.put("NumeroDeLotPone", this.NumeroDeLot);

        return json;
    }

    /**
     * 
     * @param json
     * @return
     */
    public static Commande FromJSON(String json)
    {
        JSONObject objet = new JSONObject(json);
        int NumeroDeCommande = objet.getInt("NumeroDeCommande");
        String TypeEnergie = objet.getString("TypeEnergie");
        String ModeExtraction = objet.getString("ModeExtraction");
        int QuantiteDemander = objet.getInt("QuantiteDemander");
        int QuantiteFournis = objet.getInt("QuantiteFournis");
        int PrixTotals = objet.getInt("PrixTotals");
        int NumeroDeLot = objet.getInt("NumeroDeLotPone");
        Commande commande = new Commande(
                            NumeroDeCommande,
                            TypeEnergie, 
                            ModeExtraction, 
                            QuantiteFournis,
                            QuantiteDemander,
                            PrixTotals,
                            NumeroDeLot
                            );

        return commande;
    }

    /**
     * 
     */
    @Override
    public String toString() {
        return ("\n"+
                                "-  => NumeroDeCommande : " + this.NumeroDeCommande + "\n" +
                                "-  => Type Enegie :" + this.TypeEnergie +"\n"+
                                "-  => Mode extraction :" + this.ModeExtraction +"\n"+
                                "-  => Quantite Envoyer : " + this.QuantiteDemander +"\n"+
                                "-  => Prix Total :" + this.PrixTotals+ "\n" +
                                "-  => NumeroDeLot : " + this.NumeroDeLot + "\n" 
                                );
    }
}
