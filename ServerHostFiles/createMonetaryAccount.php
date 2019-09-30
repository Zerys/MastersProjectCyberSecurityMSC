<?PHP 
	// Connection info.
	include_once("connection.php");
	// Making sure that what is sent from the client is checked here. 
	// And that it is valid to what should be sent. 
	if(isset($_REQUEST['txtCustomerNumber']) && isset($_REQUEST['txtAccountOwner'])
		&& isset($_REQUEST['txtAccountReference']) && isset($_REQUEST['txtAccountNumber'])	
		&& isset($_REQUEST['txtAccountSortCode']) && isset($_REQUEST['txtAccountName']) 
		&& isset($_REQUEST['txtAccountBalance'])){
			
		// Binding variables to the ones sent from the client	
		$customerNumber = $_REQUEST['txtCustomerNumber'];
		$AccountOwner = $_REQUEST['txtAccountOwner'];
		$AccountReference = $_REQUEST['txtAccountReference'];
		$AccountNumber = $_REQUEST['txtAccountNumber'];
		$AccountSortCode = $_REQUEST['txtAccountSortCode'];
		$AccountName = $_REQUEST['txtAccountName'];
		$AccountBalance = $_REQUEST['txtAccountBalance'];
		
		// Prepared statement for modifying the contact_accounts table. 
		$stmt = $conn->prepare("INSERT INTO contact_account
		(CD_Customer_Number, CA_Account_Owner,
		CA_Contact_Reference,  CF_Account_Number, CF_Sort_Code,
		CA_Account_Name, CA_Account_Balance) VALUES(?,?,?,?,?,?,?)");
		
		// Setting the expected fields first then binding the above variables to the  statement.
		$stmt->bind_param("ississi", $customerNumber, $AccountOwner, $AccountReference,
		$AccountNumber, $AccountSortCode, $AccountName, $AccountBalance);
		
		// I also bind result to a statement so it can be used in error checking. 
		$result = $stmt->execute();

		// Simple If statement to return 
	    if($result){
			echo "Account creation successful";		
        }
        else{
            echo "Account creation Failed";
        }
		// Tidying up, closing statement.
		$stmt->close();
	}
	// Tidying up, closing connection. 
	$conn->close();
?>