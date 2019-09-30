<?php
	// Connect to database
	include_once("connection.php");
	
	// Checking to see if client is requesting all,
	// of the below parameters.
	if( isset($_REQUEST['txtForename'])
	&& isset($_REQUEST['txtSurname']) 
	&& isset($_REQUEST['txtDateOfBirth']) 
	&& isset($_REQUEST['txtLivingAddress']) 
	&& isset($_REQUEST['txtPostcode']) 
	&& isset($_REQUEST['txtPhoneNumber']) 
	&& isset($_REQUEST['txtEmailAddress']) 
	&& isset($_REQUEST['txtssnNumber']) 
	&& isset($_REQUEST['txtPassword'])){
		
		// Create variables that are bound to, 
		// what has been requested.
		$forename = $_REQUEST['txtForename'];
		$surname = $_REQUEST['txtSurname'];
		$dateofbirth = $_REQUEST['txtDateOfBirth'];
        $livingaddress = $_REQUEST['txtLivingAddress'];
		$postcode = $_REQUEST['txtPostcode'];
        $phonenumber = $_REQUEST['txtPhoneNumber'];
		$emailaddress = $_REQUEST['txtEmailAddress'];
		$ssnNumber = $_REQUEST['txtssnNumber'];
        $password = $_REQUEST['txtPassword'];
		
		// Hashing passwords, takes the password variable.
		// Puts it through the PASSWORD_BCRYPT PHP function.
		// Which scrambles it. 
		$hash = password_hash($password, PASSWORD_BCRYPT);
		
		// The first statement, this inserts the users details
		// into the client_details table in the database.
		// It also inserts the password hash that has been
		// generated above.
		$stmt = $conn->prepare("INSERT INTO client_details (CD_Forename, CD_Surname, CD_Date_Of_Birth,
		CD_Address, CD_Post_Code, CD_Phone_Number, CD_Email_Address, CD_SSN_Number, CD_Password)
		VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)");
		
		// Statement then takes the above bound parameters and binds them to the statement. 
		// The string "sssssssis" is telling the database was is being entered to avoid SQLInjection
		// So "sssssssis" means eight strings and one integer will be entered in the order shown.
		$stmt->bind_param("sssssssis", $forename, $surname, $dateofbirth, $livingaddress, $postcode,
		$phonenumber, $emailaddress, $ssnNumber, $hash);
		
		// I also bind result to a statement so it can be used in error checking. 
		$result = $stmt->execute();
		
		// Simple If statement to return 
	    if($result){
			echo "Register successful";		
			$stmt->close();
        }
        else{
            echo "Register Failed <br/>";
			$stmt->close();
        } 
	}
	
	// Tidying up, closing statements and connections. 
	$conn->close();
?>

<html>
<head><title>Register</title></head>
    <body>
        <form action="<?PHP $_PHP_SELF ?>" method="post">
			txtssnNumber <input type="text" name="txtssnNumber" value="" /><br/>
			forname <input type="text" name="txtForename" value="" /><br/>
            surname <input type="text" name="txtSurname" value="" /><br/>
            dateofbirth <input type="text" name="txtDateOfBirth" value="" /><br/>
			livingaddress <input type="text" name="txtLivingAddress" value="" /><br/>
            postcode <input type="text" name="txtPostcode" value="" /><br/>
            phonenumber <input type="text" name="txtPhoneNumber" value="" /><br/>
			emailaddress <input type="text" name="txtEmailAddress" value="" /><br/>
            password <input type="password" name="txtPassword" value="" /><br/>
            <input type="submit" name="btn" value="Register"/>
        </form>
    </body>
</html> 