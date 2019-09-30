<?PHP
	// Connection info
	include_once("connection.php");
	// Checking client information sent.
	if( isset($_POST['txtSSN']) 
	&& isset($_POST['txtCustomerNumber']) 
	&& isset($_POST['txtPassword'])){
		
		// Assign variables
		$SSN = $_POST['txtSSN'];
        $CustomerNumber = $_POST['txtCustomerNumber'];
		$Password = $_POST['txtPassword'];
		// Get the time and date in the set format. 
		$datetime = date_create()->format('d-m-Y H:i:s');

		// #Statement 1 "Check login details"
		$stmt = $conn->prepare("SELECT CD_SSN_Number, CD_Customer_Number,
		CD_Password FROM client_details WHERE CD_SSN_Number = ? 
		AND CD_Customer_Number = ?");
		$stmt->bind_param("ii", $SSN, $CustomerNumber);
		$stmt->execute();
		$result = $stmt->get_result();

		// #Statement 2 "Set time-stamp for 'Current Login Time'"
		$stmt2 = $conn->prepare("INSERT INTO time_stamp (TS_Current_Logon_Time,
		CD_Customer_Number) VALUES(?,?)");
		$stmt2->bind_param("si", $datetime, $CustomerNumber);
		
		// #Statement 3 "Setup statement for 'Last Login Time'"
		$stmt3 = $conn->prepare("SELECT * FROM time_stamp 
		WHERE CD_Customer_Number = ?");
		$stmt3->bind_param("i", $CustomerNumber);
		$stmt3->execute();
		$result3 = $stmt3->get_result();
		
		// #Data gathering 1 "Getting the current login time value
		// from 'result3' so it can be used below. 
		$row = mysqli_fetch_assoc($result3);
		$post_numbers = $row['TS_Current_Logon_Time'];
		
		// #Statement 4 "Update Time-Stamps if they exist"
		$stmt4 = $conn->prepare("UPDATE time_stamp SET TS_Last_Logon = ?, 
		TS_Current_Logon_Time = ? WHERE CD_Customer_Number = ?");
		$stmt4->bind_param("ssi", $post_numbers, $datetime, $CustomerNumber);
		//$result4 = $stmt4->execute();
		
		// #Statement 5 "Time-Stamp for when login fails and an entry"
		// for the time-stamp does not already exist.
		$stmt5 = $conn->prepare("INSERT INTO time_stamp (TS_Last_Unsuccessful_Logon,
		CD_Customer_Number) VALUES(?,?)");
		$stmt5->bind_param("si", $datetime, $CustomerNumber);
		
		// #Statement 6 "Customer Number Check" this is to check
		// if the customer number exists. E.g. Customer 99 doesn't
		// exist in the table. Therefore a check must be done to make
		// sure that a time-stamp isn't inserted into it. 
		$stmt6 = $conn->prepare("SELECT * FROM client_details 
		WHERE CD_Customer_Number = ?");
		$stmt6->bind_param("i", $CustomerNumber);
		$stmt6->execute();
		$result6 = $stmt6->get_result();
		
		// #Statement 7 "Get the users account details" when
		// a user has successfully logged in, their account 
		// details will be returned as a result.
		$stmt7 = $conn->prepare("SELECT * FROM client_details 
		WHERE CD_Customer_Number = ? AND CD_SSN_Number = ?");
		$stmt7->bind_param("ii", $CustomerNumber, $SSN);
		$stmt7->execute();
		$result7 = $stmt7->get_result();
		
		// #Statement 8 "Get time-stamp details of the client"
		// that has successfully logged in, they can be later
		// used to be displayed in the clients profile. 
		$stmt8 = $conn->prepare("SELECT * FROM time_stamp
		WHERE CD_Customer_Number = ?");
		$stmt8->bind_param("i", $CustomerNumber);
		
		// #Statement 9 "Updates the last unsuccessful login". 
		$stmt9 = $conn->prepare("UPDATE time_stamp SET TS_Last_Unsuccessful_Logon = ?
		WHERE CD_Customer_Number = ?");
		$stmt9->bind_param("si", $datetime, $CustomerNumber);
		//$result9 = $stmt9->execute();
		
		// #Data gathering 2 "Getting the first statement and putting it
		// into an array to be used elsewhere." 
		$res = mysqli_fetch_array($result);
		$data_array = array();
		
		// #Data gathering 3 "Getting user's account details and putting
		// them into an array".
		$res2 = mysqli_fetch_array($result7);
		
        if($result->num_rows > 0){
			if (password_verify($Password, $res['CD_Password'])) {
				if($result3->num_rows > 0){
					$result4 = $stmt4->execute();
				}
				else{
					$result2 = $stmt2->execute();
				}
				$stmt8->execute();
				$result8 = $stmt8->get_result();
				
				// #Data gathering 4 "Getting a user's time-stamp details and
				// putting them into an array".
				$res3 = mysqli_fetch_array($result8);
				
				if(sizeof($res)>0){
					array_push($data_array,array("CD_Customer_Number"=>$res['CD_Customer_Number'],
					"CD_SSN_Number"=>$res['CD_SSN_Number'], "CD_Forename"=>$res2['CD_Forename'],
					"CD_Surname"=>$res2['CD_Surname'], "CD_Address"=>$res2['CD_Address'],
					"CD_Post_Code"=>$res2['CD_Post_Code'], "CD_Phone_Number"=>$res2['CD_Phone_Number'],
					"CD_Date_Of_Birth"=>$res2['CD_Date_Of_Birth'], "CD_Email_Address"=>$res2['CD_Email_Address'],
					"TS_Current_Logon_Time"=>$res3['TS_Current_Logon_Time'], "TS_Last_Logon"=>$res3['TS_Last_Logon'],
					"TS_Last_Unsuccessful_Logon"=>$res3['TS_Last_Unsuccessful_Logon']));
					echo json_encode(array("result"=>$data_array));
				}
				echo "Login Successful";
			} else {
				if($result6->num_rows > 0){
					if($result3->num_rows > 0){
						$stmt9->execute();
					}
					else{
						$stmt5->execute();
					}
				}
				echo "Login Failed";
			}
        }else{
			if($result6->num_rows > 0){
				if($result3->num_rows > 0){
					$stmt9->execute();
				}else{
					$stmt5->execute();
				}
			}
            echo "Login Failed";			
        }
		$stmt->close();
		$stmt2->close();
		$stmt3->close();
		$stmt4->close();
		$stmt5->close();
		$stmt6->close();
		$stmt7->close();
		$stmt8->close();
		$stmt9->close();
    }
	
	// Tidying up, closing connections. 
	$conn->close();
?>
<html>
<head><title>Login</title></head>
    <body>
        <form action="<?PHP $_PHP_SELF ?>" method="post">
            txtSSN <input type="text" name="txtSSN" value="" /><br/>
            txtCustomerNumber <input type="text" name="txtCustomerNumber" value="" /><br/>
			txtPassword <input type="text" name="txtPassword" value="" /><br/>
            <input type="submit" name="btnSubmit" value="Login"/>
        </form>
    </body>
</html>