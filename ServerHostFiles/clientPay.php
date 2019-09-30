<?php
include_once("connection.php");
if(isset($_REQUEST['txtCustomerNumber_personPaying']) 
	&& isset($_REQUEST['txtCustomerNumber_payee']) 
	&& isset($_REQUEST['txtAccountNumber']) 
	&& isset($_REQUEST['txtReferenceNumber'])
	&& isset($_REQUEST['txtSortCode'])
	&& isset($_REQUEST['txtAmountPaid'])){
		
		$CustomerNumber_Paying = $_REQUEST['txtCustomerNumber_personPaying'];
		$CustomerNumber_Payee = $_REQUEST['txtCustomerNumber_payee'];
		$AccountNumber = $_REQUEST['txtAccountNumber'];
		$ReferenceNumber = $_REQUEST['txtReferenceNumber'];
		$SortCode = $_REQUEST['txtSortCode'];
		$AmountPaid = $_REQUEST['txtAmountPaid'];
		
		// Check if the person paying has an account
		$stmt = $conn->prepare("SELECT * from contact_account
		WHERE CD_Customer_Number = ?");
		$stmt->bind_param("i", $CustomerNumber_Paying);
		$stmt->execute();
		$result = $stmt->get_result();
		
		// Check if the payee exists
		$stmt2 = $conn->prepare("SELECT CD_Customer_Number from client_details
		WHERE CD_Customer_Number = ?");
		$stmt2->bind_param("i", $CustomerNumber_Payee);
		$stmt2->execute();
		$result2 = $stmt2->get_result();
		
		// Check if the payee has an account
		$stmt3 = $conn->prepare("SELECT * from contact_account
		WHERE CD_Customer_Number = ? AND CF_Account_Number = ? 
		AND CA_Contact_Reference = ? AND CF_Sort_Code = ?");
		$stmt3->bind_param("iiss", $CustomerNumber_Payee, 
		$AccountNumber, $ReferenceNumber, $SortCode);

		// Pay the payee and update their balance
		$stmt4 = $conn->prepare("UPDATE contact_account 
		SET CA_Account_Balance = CA_Account_Balance + ?
		WHERE CD_Customer_Number = ?");
		$stmt4->bind_param("di", $AmountPaid, $CustomerNumber_Payee);

		
		// Update the persons paying balance
		$stmt5 = $conn->prepare("UPDATE contact_account 
		SET CA_Account_Balance = CA_Account_Balance - ?
		WHERE CD_Customer_Number = ?");
		$stmt5->bind_param("di", $AmountPaid, $CustomerNumber_Paying);
		
		$res = mysqli_fetch_array($result);
		
		if($result->num_rows > 0){
			if ($res['CA_Account_Balance'] > $AmountPaid){
				if($result2->num_rows > 0){
					$stmt3->execute();
					$result3 = $stmt3->get_result();
					if($result3->num_rows > 0){
						$stmt4->execute();
						$stmt5->execute();
						echo "Payment Successful"; 
					}
					else{
						echo "Payee account doesn't exist or their information is incorrect";
					}
				}
				else{
					echo "Payee Doesn't Exist";
				}
			}else{
				echo "Not Enough Balance";
			}
		}
		else{
			echo "You don't have a money account, make one!";
		}
	}
?>