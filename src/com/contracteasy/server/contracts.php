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
		
		$count_contracts = "SELECT COUNT(*) AS CNT FROM CONTRACTS WHERE USERID='$user' AND STATUS in (0,1)";
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
		
		$select_contracts = "SELECT * FROM CONTRACTS WHERE USERID='$user' AND STATUS in (0,1)";
		
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
				//clean up unused columns
			}
		}
		
		mysql_close($con);

		$rs['contracts'] = $contracts;
		return $rs;
	}
	
	function getNotices($user) {
		
		error_log("Getting notices", 0);
		
		$rs = array();
		$notices = array();
		
		$con = mysql_connect("localhost:3306","root","admin");
		
		mysql_select_db("contracteasy");
		
		$select_notices = "SELECT * FROM NOTICES, CONTRACTS WHERE CONTRACTID IN (SELECT ID FROM CONTRACTS WHERE USERID='$user') AND CONTRACTS.ID = NOTICES.CONTRACTID AND NOTICES.STATUS=1";
		
		error_log($select_notices, 0);
		
		$result = mysql_query($select_notices);
		
		if ($result == false) {
			echo "SQL query failed";
		} else {
			while($row = mysql_fetch_array($result)) {
				$notice = array();
				$notice['id'] = $row['id'];
				$notice['desc'] = $row['description'];
				$notice['status'] = $row['status'];
				$notice['reference'] = $row['reference'];
				$notice['datesent'] = $row['datesent'];
				$notice['content'] = $row['content'];
				$notice['counterparty'] = $row['counterparty'];				
				array_push($notices, $notice);
			}
		}
		
		mysql_close($con);
		
		$rs['notices'] = $notices;
		return $rs;
	}
	
	function getAlerts($user) {
		
	}
	
	function getContractDetails($id) {
		error_log("Getting contract details for contract $id", 0);
		
		$contract = array();
		
		$con = mysql_connect("localhost:3306","root","admin");
		
		mysql_select_db("contracteasy");
		
		$select_contract = "SELECT * FROM CONTRACTS WHERE ID='$id'";
		
		error_log($select_contract, 0);
		
		$result = mysql_query($select_contract);
		
		if ($result == false) {
			echo "SQL query failed";
		} else {
			if (mysql_num_rows($result) >= 1) {
				$row = mysql_fetch_array($result);
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
			}
		}
		
		$alerts = array();
		
		$select_alerts = "SELECT * FROM ALERTS WHERE CONTRACTID='$id' AND STATUS=1";
		
		error_log($select_alerts, 0);
		
		$result = mysql_query($select_alerts);
		
		if ($result == false) {
			echo "SQL query failed";
		} else {
			while($row = mysql_fetch_array($result)) {
				$alert = array();
				$alert['id'] = $row['id'];
				$alert['type'] = $row['type'];
				$alert['duedate'] = $row['duedate'];
				array_push($alerts, $alert);
			}
		}
		
		$contract['alerts'] = $alerts;
		
		mysql_close($con);
		
		return $contract;
	}
	
	function getNoticeDetails($id) {
		
	}
	
	function getAlertDetails($id) {
		
	}
	
	function getPackages() {
		error_log("Getting available packages");
		
		$packages = array();
		
		$con = mysql_connect("localhost:3306","root","admin");
		
		mysql_select_db("contracteasy");
		
		$select_package = "SELECT * FROM PACKAGES ORDER BY ID";
		
		error_log($select_package, 0);
		
		$result = mysql_query($select_package);
		
		if ($result == false) {
			echo "SQL query failed";
		} else {
			while($row = mysql_fetch_array($result)) {
				$package = array();
				$package['id'] = $row['id'];
 				$package['name'] = $row['name'];
				$package['max'] = $row['max'];
				array_push($packages, $package);
			}
		}

		mysql_close($con);

		$rs['pkg'] = $packages;
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
	
	error_log("Handling request for ".$data['request'], 0);
	
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
	} else if ($data['request'] === 'getDetails') {
		switch ($data['dataType']) {
			case 'co' : $response = getContractDetails($data['id']);
			break;
			case 'no' : $response = getNoticeDetails($data['id']);
			break;
			case 'al' : $response = getAlertDetails($data['id']);
			break;
		}
	} else if ($data['request'] === 'getPackages') {
		$response = getPackages();
	}
	
	header("Content-Type: application/json\r\n");
	$encoded = json_encode($response);
	exit($encoded);
?>