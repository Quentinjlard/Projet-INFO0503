<?php

    #author Romain Cogné (romain.cogne@etudiant.univ-reims.fr)
    #author Quentin Juilliard (quentin.juilliard@etudiant.univ-reims.fr)

    class SuiviCommande implements JsonSerializable{
        //Variables
        private int $idClient;
        private int $idRevendeur;
        private int $idTare;
        private int $numeroDeCommande;
        private int $numerodDeLot;
        private String $typeEnergie;
        private String $modeExtraction;
        private int $quantiteDemander;
        private int $quantiteEnvoyer;
        private int $prixUnites;
        private int $prixTotal;

        //Constructeur
        public function __construct($idClient,$idRevendeur,$idTare,$numeroDeCommande,$numerodDeLot,$typeEnergie,$modeExtraction,$quantiteDemander,$quantiteEnvoyer,$prixUnites,$prixTotal)
        {    
            $this->idClient= $idClient;
            $this->idRevendeur= $idRevendeur;
            $this->idTare= $idTare;
            $this->numeroDeCommande= $numeroDeCommande;
            $this->numerodDeLot= $numerodDeLot;
            $this->typeEnergie= $typeEnergie;
            $this->modeExtraction= $modeExtraction;
            $this->quantiteDemander= $quantiteDemander;
            $this->quantiteEnvoyer= $quantiteEnvoyer;
            $this->prixUnites= $prixUnites;
            $this->prixTotal= $prixTotal;
            
        }

        public function getIdClient()
        {
            return $this->idClient;
        }
        public function getIdRevendeur()
        {
            return $this->idRevendeur;
        }
        public function getIdTare()
        {
            return $this->idTare;
        }
        public function getNumeroDeCommande()
        {
            return $this->numeroDeCommande;
        }
        public function getNumerodDeLot()
        {
            return $this->numerodDeLot;
        }
        public function getTypeEnergie()
        {
            return $this->typeEnergie;
        }
        public function getModeExtraction()
        {
            return $this->modeExtraction;
        }
        public function getQuantiteDemander()
        {
            return $this->quantiteDemander;
        }
        public function getQuantiteEnvoyer()
        {
            return $this->quantiteEnvoyer;
        }
        public function getPrixUnites()
        {
            return $this->prixUnites;
        }
        public function getPrixTotal()
        {
            return $this->prixTotal;
        }


        //Pour sérialiser l'objet
        public function jsonSerialize() : array 
        {
            return [
                "IdClient"=>$this->idClient,
                "IdRevendeur"=>$this->idRevendeur,
                "IdTare"=>$this->idTare,
                "NumeroDeCommande"=>$this->numeroDeCommande,
                "NumeroDeLot"=>$this->numerodDeLot,
                "TypeEnergie"=>$this->typeEnergie,
                "ModeExtraction"=>$this->modeExtraction,
                "QuantiteDemander"=>$this->quantiteDemander,
                "QuantiteEnvoyer"=>$this->quantiteEnvoyer,
                "PrixUnites"=>$this->prixUnites,
                "PrixTotal"=>$this->prixTotal,
            ];
        }

        // decode un Json 
        public static function fromJson(array $arr) : SuiviCommande 
        {   
            $idClient = $arr['IdClient'];
            $idRevendeur = $arr['IdRevendeur'];
            $idTare = $arr['IdTare'];
            $numeroDeCommande = $arr['NumeroDeCommande'];
            $numerodDeLot = $arr['NumeroDeLot'];
            $typeEnergie = $arr['TypeEnergie'];
            $modeExtraction = $arr['ModeExtraction'];
            $quantiteDemander = $arr['QuantiteDemander'];
            $quantiteEnvoyer = $arr['QuantiteEnvoyer'];
            $prixUnites = $arr['PrixUnites'];
            $prixTotal = $arr['PrixTotal'];

            return new SuiviCommande (
                                    $idClient,
                                    $idRevendeur,
                                    $idTare,
                                    $numeroDeCommande,
                                    $numerodDeLot,
                                    $typeEnergie,
                                    $modeExtraction,
                                    $quantiteDemander,
                                    $quantiteEnvoyer,
                                    $prixUnites,
                                    $prixTotal,
                                );
        }

        //Affcihe la commande
        public function __toString()
        {
            return  " -> IdClient : " . $this->idClient . "<br/>" . 
                    " -> IdRevendeur : " . $this->idRevendeur . "<br/>" . 
                    " -> IdTare : " . $this->idTare . "<br/>" . 
                    " -> numeroDeCommande : " . $this->numeroDeCommande . "<br/>".
                    " -> numerodDeLot : " . $this->numerodDeLot . "<br/>".
                    " -> typeEnergie : " . $this->typeEnergie . "<br/>".
                    " -> modeExtraction : " . $this->modeExtraction . "<br/>".
                    " -> quantiteDemander : " . $this->quantiteDemander . "<br/>".
                    " -> quantiteEnvoyer : " . $this->quantiteEnvoyer . "<br/>".
                    " -> prixUnites : " . $this->prixUnites . "<br/>".
                    " -> prixTotal : " . $this->prixTotal . "<br/>"
                    ;
        }

        //Créattion de la socket d'envoie
        public function envoieSocket(){

            switch ($this->typeEnergie) 
            {
                case 'Electricite':
                    $port = 1021;
                    break;

                case 'Gaz':
                    $port = 1022;
                    break;

                case 'Petrole':
                    $port = 1023;
                    break;
                
                default :
                    break;
            }

            $data = urlencode(json_encode($this));
            
            $options = [
                'http' =>
                [
                    'method'  => 'POST',
                    'header'  => 'Content-type: application/x-www-form-urlencoded',
                    'content' => $data
                ]
            ];
            // Envoi de la requête et lecture du JSON reçu
            $URL = "http://localhost:".$port."/traitement";
            //echo $URL . "<br/>";
            $contexte  = stream_context_create($options);
            $jsonTexte = @file_get_contents($URL, false, $contexte);
            //echo "JSON TEXTE => " . $jsonTexte;

            return $jsonTexte;
        }
        
    }
?>
