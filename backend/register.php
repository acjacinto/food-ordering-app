<?php
require_once 'db_functions.php';
$db = new DB_Functions();

/*
 * Endpoint : http://<domain>/jellybeancafe/register.php
 * Method : POST
 * Params : phone,name,birthdate,email,password,address,barangay
 * Result : JSON
 */
$response = array();
if(isset($_POST['phone']) &&
    isset($_POST['name']) &&
    isset($_POST['birthdate']) &&
    isset($_POST['email']) &&
    isset($_POST['password']) &&
    isset($_POST['address']) &&
    isset($_POST['barangay'])) 
{
    $phone = $_POST['phone'];
    $name = $_POST['name'];
    $birthdate = $_POST['birthdate'];
    $email = $_POST['email'];
    $password = $_POST['password'];
    $address = $_POST['address'];
    $barangay = $_POST['barangay'];

    if($db->checkExistsUser($phone))
    {
        $response["error_msg"] = "User already existed with ".$phone;
        echo json_encode($response);
    }
    else
    {
        //Create new user
        $user = $db->registerNewUser($phone,$name,$birthdate,$email,$password,$address,$barangay);
        if($user)
        {
            $response["phone"] = $user["Phone"];
            $response["name"] = $user["Name"];
            $response["birthdate"] = $user["Birthdate"];
            $response["email"] = $user["email"];
            $response["password"] = $user["password"];
            $response["address"] = $user["Address"];
            $response["barangay"] = $user["barangay"];
            echo json_encode($response);
        }
        else
        {
            $response["error_msg"] = "Unknow error occurred in registration!";
            echo json_encode($response);
        }
    }
}
else{
    $response["error_msg"] = "Required parameter (phone,name,birthdate,email,password,address,barangay) is missing!";
    echo json_encode($response);
}
?>