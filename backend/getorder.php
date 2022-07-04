<?php
require_once 'db_functions.php';
$db = new DB_Functions();

/*
 * Endpoint : http://<domain>/jellybeancafe/getorder.php
 * Method : POST
 * Params : userPhone,status
 * Result : JSON
 */

if(isset($_POST['userPhone']) && isset($_POST['status']))
{
    $userPhone = $_POST['userPhone'];
    $status = $_POST['status'];

    $orders = $db->getOrderByStatus($userPhone,$status);
        
    echo json_encode($orders);
 }
else{
    $response = "Required parameter (userPhone,status) is missing!";
    echo json_encode($response);
}
?>