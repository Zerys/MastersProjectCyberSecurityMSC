<?php
	// Connection Info
	include_once("connection.php");
	// Check request from client is valid.
	if( isset($_REQUEST['txtCustomerNumber'])){
		// Bind variables
		$CustomerNumber = $_REQUEST['txtCustomerNumber'];

		// Prepare the statement and check the customer number
		$stmt = $conn->prepare("SELECT * FROM contact_account 
		WHERE CD_Customer_Number = ?");
		
		// Bind the previous variable to the customer number. Use it here.
		$stmt->bind_param("i", $CustomerNumber);
		$stmt->execute();
		$result = $stmt->get_result();
		
		// Take the result of the query and bind it to a MySQLi assoc array. 
		$resultArray = $result->fetch_all(MYSQLI_ASSOC);
		
		// Simple If-statement to return 
	    if($result->num_rows > 0){
			// Send the result back the client
			echo json_encode(array("result" => array($resultArray[0])));		
        }
        else{
            // No need to load anything. 
			// Display the create account button on the application.
			echo json_encode(array("No Account Found"));
        }
		// Tidying up, closing statement. 
		$stmt->close();
	}
	// Tidying up, closing connection. 
	$conn->close();
?>