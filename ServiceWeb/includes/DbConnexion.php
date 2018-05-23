<?php

	class DbConnexion{

		private $con;

		function __construct(){

		}

		function connexion(){
			include_once dirname(__FILE__).'/Constantes.php';
			$this->con = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);

			if(mysqli_connect_errno()){
				echo "Echec lors de la connexion à MySQL : (" . $this->$con->connect_errno . ") " . $this->$con->connect_error;
			}

			return $this->con;
		}
	}
?>
