<?php
require 'dbconnect.php';



$name = $_POST['name'];
$email = $_POST['email'];
$id = $_POST['id'];
$dob = $_POST['dob'];
$status = $_POST['status'];
$myemail = $_POST['myemail'];
$mname = $_POST['mname'];
$mid = $_POST['mid'];
$mdob = $_POST['mdob'];
$mstatus = $_POST['mstatus'];

$query = "INSERT INTO `$myemail` (name,email,userid,dob,status) VALUES ('$name' , '$email' , '$id' , '$dob' , '$status')";
$result = mysql_query($query);
$query = "INSERT INTO `$email` (name,email,userid,dob,status) VALUES ('$mname' , '$myemail' , '$mid' , '$mdob' , '$mstatus')";
$result1 = mysql_query($query);

$newemail=$myemail.$email;

$sqltablecreatedforfrndlist = "CREATE TABLE `$newemail`(
id Integer NOT NULL PRIMARY KEY AUTO_INCREMENT,
msg VARCHAR(100),
mdate VARCHAR(50),
sender VARCHAR(10)

)";
$result2 = mysql_query($sqltablecreatedforfrndlist);


$newemail=$email.$myemail;

$sqltablecreatedforfrndlist = "CREATE TABLE `$newemail`(
id Integer NOT NULL PRIMARY KEY AUTO_INCREMENT,
msg VARCHAR(100),
mdate VARCHAR(50),
sender VARCHAR(10)

)";
$result3 = mysql_query($sqltablecreatedforfrndlist);


if(($result)&&($result1)&&($result2)&&($result3))
{
 echo "success";
}
else
	echo "fail";


?>

