<?php
require_once '../../db_functions.php';
$db = new DB_Functions();

if(isset($_POST['status']) && isset($_POST['phone']) && isset($_POST['order_id']))
{
    $status = $_POST['status'];
    $phone = $_POST['phone'];
    $order_id = $_POST['order_id'];

    $result = $db->updateOrderStatus($phone,$order_id,$status);
    echo json_encode($result);
}
else
{
    echo json_encode("Missing status,phone,order_id field");
}
?>