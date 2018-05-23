<?php

	require_once '../includes/DbOperations.php';

	$reponse = array();

	if($_SERVER['REQUEST_METHOD'] == 'POST'){
		if(isset($_POST['Pseudo']) and isset($_POST['MotDePasse'])){
			
			$db = new DbOperations();

			if($db->connexion($_POST['Pseudo'], $_POST['MotDePasse'])){
				
				$utilisateur = $db->getUserByPseudo($_POST['Pseudo']);
				$reponse['error'] = false;
				$reponse['id'] = $utilisateur['Id'];
				$reponse['nom'] = $utilisateur['Nom'];
				$reponse['prenom'] = $utilisateur['Prenom'];
				$reponse['pseudo'] = $utilisateur['Pseudo'];
				$reponse['email'] = $utilisateur['Email'];
				$reponse['age'] = $utilisateur['Age'];
			}
			else{
				
				$reponse['error'] = true;
				$reponse['message'] = "Authentification incorrect";
			}
		}
		else{
			
			$reponse['error'] = true;
			$reponse['message'] = "Certaines informations sont manquantes";
		}
	}
	else{
		
		$reponse['error'] = true;
		$reponse['message'] = "Requete invalide"; 
	}

	echo json_encode($reponse);
?>