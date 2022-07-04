<?php
require_once '../../db_functions.php';
$db = new DB_Functions();

/*
 * Endpoint : http://<domain>/jellybeancafe/server/order/getorder.php
 * Method : POST
 * Params : userPhone,status
 * Result : JSON
 */

if(isset($_POST['status']))
{
    $status = $_POST['status'];

    $orders = $db->getOrderServerByStatus($status);
        
    echo json_encode($orders);
 }
else{
    $response = "Required parameter (status) is missing!";
    echo json_encode($response);
}
?>