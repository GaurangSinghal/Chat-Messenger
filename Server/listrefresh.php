<?php
require 'dbconnect.php';
$email = $_POST['email'];
//$email = "ashish@com";
$query = "SELECT * FROM `$email`";
$result = mysql_query($query);
$noofrow = mysql_num_rows($result);
$output = " ";
while($row=mysql_fetch_array($result))
{
	
	$name = $row['name'];
	$status = $row['status'];
	$email = $row['email'];
	$dob = $row['dob'];
	$new = $name."+".$status."+".$email."+".$dob;
	$output = $new."*".$output;

	
	}
echo $output;

?>