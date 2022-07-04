<?php
require_once 'db_functions.php';
$db = new DB_Functions();

/*
 * Endpoint : http://<domain>/jellybeancafe/gettoken.php
 * Method : POST
 * Params : userPhone,isServerToken
 * Result : JSON
 */

if(isset($_POST['phone']) && isset($_POST['isServerToken']))
{
    $userPhone = $_POST['phone'];
    $isServerToken = $_POST['isServerToken'];

    $token = $db->getToken($userPhone,$isServerToken);
        
    echo json_encode($token);
 }
else{
    $response = "Required parameter (userPhone,isServerToken) is missing!";
    echo json_encode($response);
}
?>