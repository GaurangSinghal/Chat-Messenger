<?php
require 'dbconnect.php';
$email = $_POST['email'];
$query = "SELECT * FROM register WHERE email='$email'";
$result = mysql_query($query);
$noofrow = mysql_num_rows($result);
$row=mysql_fetch_array($result);
if($noofrow!=0)
{
	$id = $row['userid'];
	$name = $row['name'];
	$email = $row['email'];
	$dob = $row['dob'];
	$status = $row['status'];
	
	echo $id."+".$name."+".$email."+".$dob."+".$status;

}
else
	echo "false";


?>