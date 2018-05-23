<?php

	require_once '../includes/DbOperations.php';

	$reponse = array();

	if($_SERVER['REQUEST_METHOD'] == 'POST'){
		if(isset($_POST['IdAbonne']) and isset($_POST['IdAbonnement'])){
			
			$db = new DbOperations();

			$resultat = $db->desabonnement($_POST['IdAbonne'], $_POST['IdAbonne']);

			if($resultat == 1){
				
				$reponse['error'] = false;
				$reponse['message'] = "Desabonnement réussi";
			}
			else if($resultat == 0){
				
				$reponse['error'] = true;
				$reponse['message'] = "Vous ne pouvez pas vous désabonner d'un réseau auquel vous n'êtes pas abonné(e)";
			}
			else if($resultat == 2){
				
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
		$reponse['message'] = "Requête invalide"; 
	}

	echo json_encode($reponse);
?>