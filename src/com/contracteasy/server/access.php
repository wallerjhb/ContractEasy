<?php
// Log users in and out
// Frills: maintain session, usage stats

	if (isset($_SERVER['HTTP_ORIGIN']))
	{
		header("Access-Control-Allow-Origin: {$_SERVER['HTTP_ORIGIN']}");
		header('Access-Control-Allow-Credentials: true');
		header('Access-Control-Max-Age: 86400');    // cache for 1 day
	}
	
	if ($_SERVER['REQUEST_METHOD'] == 'OPTIONS')
	{
	
		if (isset($_SERVER['HTTP_ACCESS_CONTROL_REQUEST_METHOD']))
			header("Access-Control-Allow-Methods: GET, POST, OPTIONS");
	
		if (isset($_SERVER['HTTP_ACCESS_CONTROL_REQUEST_HEADERS']))
			header("Access-Control-Allow-Headers:        {$_SERVER['HTTP_ACCESS_CONTROL_REQUEST_HEADERS']}");
	
		exit(0);
	}
	
	function login($user, $pass) {
		session_start();
		
		$login_array = array();
		
		$con = mysql_connect("localhost:3306","root","admin");
		
		if (mysql_errno()) {
			echo "conn error";
			$login_array['errorCode'] = 98;
			$login_array['errorMessage'] = 'Error code 1057: please contact ContractEasy admin.';
			$login_array['usid'] = 0;
			$login_array['status'] = 0;
		}
		
		mysql_select_db("contracteasy");
		$check_user = "SELECT * FROM USERS WHERE USERNAME='$user' AND PASSWORD='$pass'";
		$result = mysql_query($check_user);
		
		if ($result == false) {
			echo "SQL query failed";
		} else {
			if (mysql_num_rows($result) >= 1) {
				$row = mysql_fetch_array($result);
				$login_array['errorCode'] = 0;
				$login_array['errorMessage'] = '';
				$login_array['usid'] = $row['usid'];
				$login_array['status'] = $row['status'];
				$login_array['pkg'] = $row['package'];
				
				error_log("User: id=".$row['usid']." status=".$row['status'], 0);
			}
		}
		
		$update_lastlogin = "UPDATE USERS SET LASTLOGIN = NOW() WHERE USID='{$row['usid']}'";
		$result = mysql_query($update_lastlogin);
		
		mysql_close($con);
		return $login_array;
	}
	
	function logout($id) {
		session_destroy();
	}
	
	function signUp($user, $pass) {
		
		$signup_array = array();
		
		$con = mysql_connect("localhost:3306","root","admin");
		
		if (mysql_errno()) {
			echo "conn error";
			$signup_array['errorCode'] = 99;
			$signup_array['errorMessage'] = 'Error code 1056: please contact ContractEasy admin.';
		}
		
		mysql_select_db("contracteasy");
		$check_user = "SELECT COUNT(*) AS COUNT FROM USERS WHERE USERNAME='$user'";
		$result = mysql_query($check_user);
		
		if ($result == false) {
			echo "SQL query failed";
		} else {
			if (mysql_num_rows($result) == 1) {
				$row = mysql_fetch_array($result);
				if ($row['COUNT'] > 0) {
					$signup_array['errorCode'] = 1;
					$signup_array['errorMessage'] = 'Username already in use. Please select a different username.';
				} else {
					$insert_user = "INSERT INTO USERS (USERNAME, PASSWORD, STATUS, LASTLOGIN, PACKAGE) VALUES ('$user','$pass',1,'2000-01-01',0)";
					$result = mysql_query($insert_user);
					if ($result == false) {
						$signup_array['errorCode'] = 2;
						$signup_array['errorMessage'] = 'Error code 974: please contact ContractEasy admin.';
					} else {
						$signup_array['errorCode'] = 0;
						$signup_array['errorMessage'] = '';
					}
				}
			} else echo "Check the SQL, something went wrong.";
		}
		
		mysql_close($con);
		return $signup_array;
	}
	
	function uploadDetails($user, $companyName, $contactName, $email, $address) {
		$details_array = array();
		
		$con = mysql_connect("localhost:3306","root","admin");
		
		if (mysql_errno()) {
			echo "conn error";
			$details_array['errorCode'] = 99998;
		}
		
		mysql_select_db("contracteasy");
		$sql = "INSERT INTO COMPANYDET (USER, NAME, CONTACT, EMAIL, ADDRESS) VALUES ('$user','$companyName','$contactName','$email','$address')";
		$result = mysql_query($sql);
		
		if ($result == false) {
			echo "SQL query failed";
		} else {
			$details_array['errorCode'] = 0;
		}
		
		mysql_close($con);
		return $details_array;
	}
	
	$body = file_get_contents("php://input");
	$decoded = json_decode(stripslashes($body), true);
	$data = array();
	$response = array();
	if($decoded){
		foreach($decoded as $key => $value) {
			$data[$key] = $value;
		}
	}

	if ($data['request'] === 'signUp') {
		error_log("Signing up user...", 0);
		$response = signUp($data['username'], $data['password']);
		
	} else if ($data['request'] === 'login') {
		error_log("Logging in user...", 0);
		$response = login($data['username'], $data['password']);
	
	} else if ($data['request'] === 'logout') {
		error_log("Logging out user...", 0);
		$response = logout($data['id']);
		
	} else if ($data['request'] === 'submitCompanyDetails') {
		error_log("Uploading company details", 0);
		$response = uploadDetails($data['user'], $data['companyName'], $data['contactName'], $data['email'], $data['physicalAddress']);
		
	}
	
	header("Content-Type: application/json\r\n");
	$encoded = json_encode($response);
	exit($encoded);
?>