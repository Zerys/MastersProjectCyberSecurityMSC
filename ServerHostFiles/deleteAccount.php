<?php
	include_once("connection.php");
	if( isset($_POST['txtCustomerNumber'])){
		$CustomerNumber = $_POST['txtCustomerNumber'];
		
		 // Deleting the customer from "client_details"
		$stmt = $conn->prepare("DELETE FROM client_details
		WHERE CD_Customer_Number = ?");
		
		// Statement then takes the above bound parameters and binds them to the statement.
		$stmt->bind_param("i", $CustomerNumber);
		
		// We also bind result to a statement so it can be used in error checking. 
		$result = $stmt->execute();
		
		// Tidying up, closing statement.
		$stmt->close();
	}
	// Tidying up, closing connection. 
	$conn->close();
?>