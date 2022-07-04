<?php
require_once 'db_functions.php';
$db = new DB_Functions();

/*
 * Endpoint : http://<domain>/jellybeancafe/register.php
 * Method : POST
 * Params : phone,name,birthdate,address
 * Result : JSON
 */
$response = array();
if(isset($_POST['menuid']))
{
    $menuid = $_POST['menuid'];

    $drinks = $db->getDrinkByMenuID($menuid);
        
    echo json_encode($drinks);
 }
else{
    $response["error_msg"] = "Required parameter (menuid) is missing!";
    echo json_encode($response);
}
?>