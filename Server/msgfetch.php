<?php
require 'dbconnect.php';
$peremail = $_POST['peremail'];
//$peremail = "vidushi@comashish@com";
$query = "SELECT * FROM `$peremail` ORDER BY `id` DESC";
$result = mysql_query($query);
$noofrow = mysql_num_rows($result);
//$row=mysql_fetch_array($result);
$output="";
while($row=mysql_fetch_array($result))
{
	 $msg = $row['msg'];
	 $mdate = $row['mdate'];
	 $sender = $row['sender'];
	 $conc = $msg."+".$mdate."+".$sender;
	 $output=$conc."*".$output;	
}
echo $output;
?>