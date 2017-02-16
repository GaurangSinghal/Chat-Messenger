<?php
require 'dbconnect.php';


//if(isset($_POST['name'])&&isset($_POST['email'])&&isset($_POST['password']))
//{
$name = $_POST['name'];
$email = $_POST['email'];
$password = $_POST['password'];
$dob = $_POST['dob'];
$status = $_POST['status'];
//if(!empty($password)&&!empty($name)&&!empty($email))
//{
$query = "INSERT INTO register (name,email,password,dob,status) VALUES ('$name' , '$email' , '$password' , '$dob' , '$status')";
$result = mysql_query($query);

$sqltablecreatedforfrndlist = "CREATE TABLE `$email`(
id Integer NOT NULL PRIMARY KEY AUTO_INCREMENT,
userid Integer NOT NULL,
name VARCHAR(25) NOT NULL,
email VARCHAR(50) NOT NULL,
dob VARCHAR(15) NOT NULL,
status VARCHAR(50) NOT NULL
)";
$result1 = mysql_query($sqltablecreatedforfrndlist);


/*$friend = 'friend'.$email;

$sqltablecreated2 = "CREATE TABLE `$friend` (
frndid INT(25),
frndname VARCHAR(50),
frndemail VARCHAR(50)


)";
$result2 = mysql_query($sqltablecreated2);*/



if(($result)&&($result1))
{
 /*$structure = 'F:/xampp/htdocs';
 mkdir($structure.'/'.$email, 0777, true);
 mkdir($structure.'/'.$email.'/photo', 0777, true);
 header('Location: chatbookindex.html');*/
 echo "success";
}
else
	echo "fail";

//}
//}

?>

