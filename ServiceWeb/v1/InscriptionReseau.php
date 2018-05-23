<?php

	require_once '../includes/DbOperations.php';

	$reponse = array();

	if($_SERVER['REQUEST_METHOD'] == 'POST'){
		if(isset($_POST['Administrateur']) and isset($_POST['Nom']) and isset($_POST['Coordonnees']) and isset($_POST['MotCle']) and isset($_POST['Public'])){
			
			$db = new DbOperations();

			$resultat = $db->inscriptionReseau($_POST['Administrateur'], $_POST['Nom'], $_POST['Coordonnees'], $_POST['MotCle'], $_POST['Public']);

			if($resultat == 2){
				
				$reponse['error'] = false;
				$reponse['message'] = "Inscription du réseau réalisée avec succès";
			}
			else if($resultat == 0){
				
				$reponse['error'] = true;
				$reponse['message'] = "Un réseau possède déjà ce nom, merci d'en choisir un autre";
			}
			else if($resultat == 1){
				
				$reponse['error'] = true;
				$reponse['message'] = "Un réseau existe déjà à cet emplacement";
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