<?php
require 'dbconnect.php';

$chat = $_POST['chat'];
$email = $_POST['email'];
$datem = $_POST['datem'];
$sender = $_POST['sender'];
$senderr = "true";
$fetchemail = $_POST['fetchemail'];
$query = "INSERT INTO `$email` (msg,mdate,sender) VALUES ('$chat' , '$datem' , '$sender')";
$query1 = "INSERT INTO `$fetchemail` (msg,mdate,sender) VALUES ('$chat' , '$datem' , '$senderr')";
$result = mysql_query($query);
$result1 = mysql_query($query1);
if(($result)&&($result1))
{
	echo "success";
}
else
	echo "fail";

?>