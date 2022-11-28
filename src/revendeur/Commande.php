<?php

    #author Romain Cogné (romain.cogne@etudiant.univ-reims.fr)
    #author Quentin Juilliard (quentin.juilliard@etudiant.univ-reims.fr)

    class Commande implements JsonSerializable{
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
            $this->$idClient= $idClient;
            $this->$idRevendeur= $idRevendeur;
            $this->$idTare= $idTare;
            $this->$numeroDeCommande= $numeroDeCommande;
            $this->$numerodDeLot= $numerodDeLot;
            $this->$typeEnergie= $typeEnergie;
            $this->$modeExtraction= $modeExtraction;
            $this->$quantiteDemander= $quantiteDemander;
            $this->$quantiteEnvoyer= $quantiteEnvoyer;
            $this->$prixUnites= $prixUnites;
            $this->$prixTotal= $prixTotal;
        }

        //Pour sérialiser l'objet
        public function jsonSerialize() : array 
        {
            return [
                "IdClient"=>$this->$idClient,
                "IdRevendeur"=>$this->$idRevendeur,
                "IdTare"=>$this->$idTare,
                "NumeroDeCommande"=>$this->$numeroDeCommande,
                "NumeroDeLot"=>$this->$numerodDeLot,
                "TypeEnergie"=>$this->$typeEnergie,
                "ModeExtraction"=>$this->$modeExtraction,
                "QuantiteDemander"=>$this->$quantiteDemander,
                "QuantiteEnvoyer"=>$this->$quantiteEnvoyer,
                "PrixUnites"=>$this->$prixUnites,
                "PrixTotal"=>$this->$prixTotal,
            ];
        }

        // decode un Json 
        public static function fromJson(array $arr) : Commande 
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

            return new Commande (
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
        public function envoieSocket($commande)
        {
            $host    = "localhost";
            switch ($commande->energie) 
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
            

            echo "Message au serveur :".$commande;
            
            $socket = socket_create(AF_INET, SOCK_STREAM, 0) or die("Impossible de créer un socket\n");
            $resultat = socket_connect($socket, $host, $port) or die("Impossible de se connecter au serveur\n"); 
            socket_write($socket, $message, strlen($message)) or die("Impossible d'envoyer des données au serveur\n");
            $resultat = socket_read ($socket, $port) or die("Impossible de lire la réponse du serveur\n");
            echo "Réponse du serveur   :".$resultat;
            
            socket_close($socket);
        }
        
    }
?>
