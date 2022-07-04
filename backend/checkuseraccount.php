<?php
require_once 'db_functions.php';
$db = new DB_Functions();

/*
 * Endpoint : http://<domain>/jellybeancafe/checkuser.php
 * Method : POST
 * Params : phone
 * Result : JSON
 */
$response = array();
if(isset($_POST['email'])&&
   isset($_POST['password']))
{
    $email = $_POST['email'];
    $password = $_POST['password'];

    if($db->checkExistsUserAccount($email,$password))
    {
        $response["exists"] = TRUE;
        echo json_encode($response);
    }
    else
    {
        $response["exists"] = false;
        echo json_encode($response);
    }
}
else{
    $response["error_msg"] = "Required parameter (email,password) is missing!";
    echo json_encode($response);
}
?>