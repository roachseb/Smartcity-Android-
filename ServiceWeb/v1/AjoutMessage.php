<?php

	require_once '../includes/DbOperations.php';

	$reponse = array();

	if($_SERVER['REQUEST_METHOD'] == 'POST'){
		if(isset($_POST['IdAuteur']) and isset($_POST['IdDestinataire']) and isset($_POST['Contenu']) and isset($_POST['Public'])){
			
			$db = new DbOperations();

			$resultat = $db->ajouterMessage($_POST['IdAuteur'], $_POST['IdDestinataire'], $_POST['Contenu'], $_POST['Public']);

			if($resultat == 1){
				
				$reponse['error'] = false;
				$reponse['message'] = "Message ajouté avec succès";
			}
			else if($resultat == 0){
				
				$reponse['error'] = true;
				$reponse['message'] = "Merci de ne pas spammer le réseau";
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