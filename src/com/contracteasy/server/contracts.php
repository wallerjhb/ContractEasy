<?php

	error_log("---------Contracts Server-----------", 0);

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
	
	function getDataCount($user) {
		
		error_log("Starting data count", 0);
		
		$data_count = array();
		
		$con = mysql_connect("localhost:3306","root","admin");
		
		mysql_select_db("contracteasy");
		
		$count_contracts = "SELECT COUNT(*) AS CNT FROM CONTRACTS WHERE USERID='$user' AND STATUS=1";
		$result = mysql_query($count_contracts);
		
		if ($result == false) {
			echo "SQL query failed";
		} else {
			$row = mysql_fetch_array($result);
			$data_count['contracts'] = $row['CNT'];
		}
		
		$data_count['alerts'] = 3;
		
		$data_count['notices'] = 4;
		
		mysql_close($con);
		
		return $data_count;
	}
	
	function getContracts($user) {
		
		error_log("Getting contracts", 0);
		
		$rs = array();
		$contracts = array();
		
		$con = mysql_connect("localhost:3306","root","admin");
		
		mysql_select_db("contracteasy");
		
		$select_contracts = "SELECT * FROM CONTRACTS WHERE USERID='$user' AND STATUS=1";
		
		error_log($select_contracts, 0);
		
		$result = mysql_query($select_contracts);
		
		if ($result == false) {
			echo "SQL query failed";
		} else {
			while($row = mysql_fetch_array($result)) {
				$contract = array();
				$contract['id'] = $row['id'];
				$contract['desc'] = $row['description'];
				$contract['status'] = $row['status'];
				$contract['reference'] = $row['reference'];
				$contract['clientref'] = $row['clientref'];
				$contract['type'] = $row['type'];
				$contract['counterparty'] = $row['counterparty'];
				$contract['start'] = $row['start'];
				$contract['termination'] = $row['termination'];
				$contract['escalation'] = $row['escalation'];
				$contract['renewal'] = $row['renewal'];
				$contract['noticeperioddays'] = $row['noticeperioddays'];
				array_push($contracts, $contract);
			}
		}
		
		mysql_close($con);

		$rs['contracts'] = $contracts;
		return $rs;
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
		$response = getDataCount($data['userId']);
	} else if ($data['request'] === 'getData') {
		switch ($data['dataType']) {
			case 'co' : $response = getContracts($data['userId']);
			break;
			case 'no' : $response = getNotices($data['userId']);
			break;
			case 'al' : $response = getAlerts($data['userId']);
			break;
		}
	}
	
	header("Content-Type: application/json\r\n");
	$encoded = json_encode($response);
	exit($encoded);
?>