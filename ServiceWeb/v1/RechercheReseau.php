<?php

	require_once '../includes/DbOperations.php';

	$reponse = array();

	if($_SERVER['REQUEST_METHOD'] == 'POST'){
		if(isset($_POST['Nom'])) {
			
			$db = new DbOperations();
				
			$reseau = $db->getReseauByNom($_POST['Nom']);
			$utilisateur = $db->getUserById($reseau['Administrateur']);
			$reponse['error'] = false;
			$reponse['idReseau'] = $reseau['IdReseau'];
			$reponse['administrateur'] = $utilisateur['Prenom']." ".$utilisateur['Nom'];
			$reponse['nom'] = $reseau['Nom'];
			$reponse['coordonnees'] = $reseau['Coordonnees'];
			$reponse['public'] = $reseau['Public'];
			$reponse['motCle'] = $reseau['MotCle'];
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