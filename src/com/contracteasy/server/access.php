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
		$con = mysql_connect("localhost:3306","root","admin");
	}
	
	function logout($id) {
		
	}
	
	function signUp($user, $pass, $company) {
		
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
		
		if (mysql_num_rows($result) == 1) {
			$row = mysql_fetch_array($result);
			if ($row['COUNT'] > 0) {
				$signup_array['errorCode'] = 1;
				$signup_array['errorMessage'] = 'Username already in use. Please select a different username.';
			} else {
				$signup_array['errorCode'] = 0;
				$signup_array['errorMessage'] = '';				
			}
		} else echo "Check the SQL, something went wrong.";
		
		return $signup_array;
	}
	
	$body = file_get_contents("php://input");
	$decoded = json_decode(stripslashes($body), TRUE);
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
	}
	
	header("Content-Type: application/json\r\n");
	$encoded = json_encode($response);
	exit($encoded);
?>