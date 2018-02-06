<?php

	if (isset($_SERVER['HTTP_ORIGIN']))	{
		header("Access-Control-Allow-Origin: {$_SERVER['HTTP_ORIGIN']}");
		header('Access-Control-Allow-Credentials: true');
		header('Access-Control-Max-Age: 86400');    // cache for 1 day
	}
	
	if ($_SERVER['REQUEST_METHOD'] == 'OPTIONS') {
	
		if (isset($_SERVER['HTTP_ACCESS_CONTROL_REQUEST_METHOD']))
			header("Access-Control-Allow-Methods: GET, POST, OPTIONS");
	
		if (isset($_SERVER['HTTP_ACCESS_CONTROL_REQUEST_HEADERS']))
			header("Access-Control-Allow-Headers:        {$_SERVER['HTTP_ACCESS_CONTROL_REQUEST_HEADERS']}");
	
		exit(0);
	}
	
	function getNumberOfContracts($user) {
		
		$num_contracts = 0;
		
		$con = mysql_connect("localhost:3306","root","admin");
		
		mysql_select_db("contracteasy");
		$count_contracts = "SELECT COUNT(*) AS CNT FROM CONTRACTS WHERE OWNER='$user' AND STATUS=1";
		$result = mysql_query($count_contracts);
		
		if ($result == false) {
			echo "SQL query failed";
		} else {
			$row = mysql_fetch_array($result);
			$num_contracts = $row['CNT'];
		}
		
		mysql_close($con);
		
		return $num_contracts;
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
	
	if ($data['request'] === 'numActive') {
		$response = getNumberOfContracts($data['userid']);
	}
?>