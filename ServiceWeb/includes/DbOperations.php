<?php
	
	class DbOperations{

		private $con;

		function __construct(){

			require_once dirname(__FILE__).'/DbConnexion.php';

			$db = new DbConnexion();

			$this->con = $db->connexion();
		}

		//UTILISATEUR

		public function inscription($nom, $prenom, $pseudo, $email, $age, $mdp){
			
			if($this->pseudoExistant($pseudo)){
				return 0;
			}
			else if($this->emailExistant($email)){
				return 1;
			}
			else{
				$motDePasse = md5($mdp);
				if($stmt = $this->con->prepare('INSERT INTO Utilisateur (Id, Nom, Prenom, Pseudo, Email, Age, MotDePasse) VALUES (NULL, ?, ?, ?, ?, ?, ?)')){
					$stmt->bind_param("ssssis",$nom, $prenom, $pseudo, $email, $age, $motDePasse);
					$stmt->execute();
					return 2;
				}
				else{
					var_dump($this->con->error);
					return 3;
				}
			}
		}

		public function connexion($pseudo, $motDePasse){
			$mdp = md5($motDePasse);
			$stmt = $this->con->prepare("SELECT Id FROM Utilisateur WHERE Pseudo = ? AND MotDePasse = ?");
			$stmt->bind_param("ss", $pseudo, $mdp);
			$stmt->execute();
			$stmt->store_result();
			return $stmt->num_rows > 0;
		}

		public function getUserByPseudo($pseudo){
			$stmt = $this->con->prepare("SELECT * FROM Utilisateur WHERE Pseudo = ?");
			$stmt->bind_param("s",$pseudo);
			$stmt->execute();
			return $stmt->get_result()->fetch_assoc();
		}

		public function getUserById($id){
			if($stmt = $this->con->prepare("SELECT * FROM Utilisateur WHERE Id = ?")){
				$stmt->bind_param("i",$id);
				$stmt->execute();
				return $stmt->get_result()->fetch_assoc();
			}
			else{
				var_dump($this->con->error);
			}
				
		}

		private function pseudoExistant($pseudo){
			$stmt = $this->con->prepare('SELECT Id FROM Utilisateur WHERE Pseudo = ?');
			$stmt->bind_param("s", $pseudo);
			$stmt->execute();
			$stmt->store_result();
			return $stmt->num_rows > 0;
		}

		private function emailExistant($email){
			$stmt = $this->con->prepare('SELECT Id FROM Utilisateur WHERE Email = ?');
			$stmt->bind_param("s", $email);
			$stmt->execute();
			$stmt->store_result();
			return $stmt->num_rows > 0;
		}

		//RESEAUX_SOCIAUX

		public function inscriptionReseau($admin, $nom, $coord, $motcle, $public){
			if($this->nomReseauExistant($nom)){
				return 0;
			}
			else if($this->coordonneesExistantes($coord)){
				return 1;
			}
			else{
				if($stmt = $this->con->prepare('INSERT INTO Reseau_Social (IdReseau, Administrateur, Nom, Coordonnees, MotCle, Public) VALUES (NULL, ?, ?, ?, ?, ?)')){
					$stmt->bind_param("isssi",$admin, $nom, $coord, $motcle, $public);
					$stmt->execute();
					return 2;
				}
				else{
					var_dump($this->con->error);
					return 3;
				}
			}
		}

		private function nomReseauExistant($nom){
			if($stmt = $this->con->prepare('SELECT IdReseau FROM Reseau_Social WHERE Nom = ?')){
				$stmt->bind_param("s", $nom);
				$stmt->execute();
				$stmt->store_result();
				return $stmt->num_rows > 0;	
			}
			else{
				var_dump($this->con->error);
			}
			
		}

		private function coordonneesExistantes($coord){
			if($stmt = $this->con->prepare('SELECT IdReseau FROM Reseau_Social WHERE Coordonnees = ?')){
				$stmt->bind_param("s", $coord);
				$stmt->execute();
				$stmt->store_result();
				return $stmt->num_rows > 0;
			}
			else{
				var_dump($this->con->error);
			}
		}

		public function getReseauByNom($nom){
			if($stmt = $this->con->prepare("SELECT * FROM Reseau_Social WHERE Nom = ?")){
				$stmt->bind_param("s",$nom);
				$stmt->execute();
				return $stmt->get_result()->fetch_assoc();
			}
			else{
				var_dump($this->con->error);
			}
		}

		//ABONNEMENT

		public function abonnement($idU, $idR){
			if($this->dejaAbonne($idU, $idR)){
				return 0;
			}
			else{
				if($stmt = $this->con->prepare('INSERT INTO Abonnement (IdAbonne, IdAbonnement) VALUES (?, ?)')){
					$stmt->bind_param("ii",$idU, $idR);
					$stmt->execute();
					return 1;
				}
				else{
					var_dump($this->con->error);
					return 2;
				}
			}
		}

		public function dejaAbonne($idU, $idR){
			if($stmt = $this->con->prepare('SELECT IdAbonne, IdAbonnement FROM Abonnement WHERE IdAbonne = ? AND IdAbonnement = ?')){
				$stmt->bind_param("ii", $idU, $idR);
				$stmt->execute();
				$stmt->store_result();
				return $stmt->num_rows > 0;
			}
			else{
				var_dump($this->con->error);
			}
		}

		public function desabonnement($idU, $idR){
			if(!$this->dejaAbonne($idU, $idR)){
				return 0;
			}
			else{
				if($stmt = $this->con->prepare('DELETE FROM Abonnement WHERE IdAbonne = ? AND IdAbonnement = ?')){
					$stmt->bind_param("ii",$idU, $idR);
					$stmt->execute();
					return 1;
				}
				else{
					var_dump($this->con->error);
				}
			}
		}

		//MESSAGE

		public function ajouterMessage($idA, $idD, $contenu, $public){
			if($this->spam($idA, $contenu)){
				return 0;
			}
			else{
				if($stmt = $this->con->prepare('INSERT INTO Message (IdMessage, IdAuteur, IdDestinataire, Contenu, Public) VALUES (NULL, ?, ?, ?, ?)')){
					$stmt->bind_param("iisi",$idA, $idD, $contenu, $public);
					$stmt->execute();
					return 1;
				}
				else{
					var_dump($this->con->error);
					return 2;
				}
			}
		}

		public function spam($idA, $contenu){
			if($stmt = $this->con->prepare('SELECT IdMessage FROM Message WHERE IdAuteur = ? AND contenu = ?')){
				$stmt->bind_param("is", $idA, $contenu);
				$stmt->execute();
				$stmt->store_result();
				return $stmt->num_rows > 0;
			}
			else{
				var_dump($this->con->error);
			}
		}

		public function suppressionMessage($idA, $idD, $contenu, $public){
			if($stmt = $this->con->prepare('DELETE FROM Message WHERE IdAuteur = ? AND IdDestinataire = ? AND Contenu = ? AND Public = ')){
				$stmt->bind_param("iisi",$idA, $idD, $contenu, $public);
				$stmt->execute();
				return 1;
			}
			else{
				var_dump($this->con->error);
			}
		}
	}

?>