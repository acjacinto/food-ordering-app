<?php

require_once '../../db_functions.php';
$db = new DB_Functions();

if(isset($_POST['id']))
{

    $id = $_POST['id'];

    $result = $db->deleteCategory($id);
    if($result)
        echo json_encode("Delete Category success !");
    else
        echo json_encode("Error while write to database");

}
else{
    echo (json_encode("Required parameters (id, name , imgPath) is missing!"));
}

?>