<?php

	require_once '../includes/DbOperations.php';

	$reponse = array();

	if($_SERVER['REQUEST_METHOD'] == 'POST'){
		if(isset($_POST['Nom']) and isset($_POST['Prenom']) and isset($_POST['Pseudo']) and isset($_POST['Email']) and isset($_POST['Age']) and isset($_POST['MotDePasse'])){
			
			$db = new DbOperations();

			$resultat = $db->inscription($_POST['Nom'], $_POST['Prenom'], $_POST['Pseudo'], $_POST['Email'], $_POST['Age'], $_POST['MotDePasse']);

			if($resultat == 2){
				
				$reponse['error'] = false;
				$reponse['message'] = "Inscription réalisée avec succès";
			}
			else if($resultat == 0){
				
				$reponse['error'] = true;
				$reponse['message'] = "Pseudo déjà existant, merci d'en choisir un autre";
			}
			else if($resultat == 1){
				
				$reponse['error'] = true;
				$reponse['message'] = "Compte déjà existant, merci de vous connecter";
			}
			else if($resultat == 3){
				
				$reponse['error'] = true;
				$reponse['message'] = "Une erreur est apparue, merci de réessayer";
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