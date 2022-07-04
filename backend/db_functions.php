<?php

class DB_Functions{
    private $conn;

    function __construct()
    {
        require_once 'db_connect.php';
        $db = new DB_Connect();
        $this->conn = $db->connect();
    }

    function __destruct()
    {
        // TODO: Implement __destruct() method.
    }
    
    /*
     *Check user exist
     *return true/false
     */

    function checkExistsUser($phone)
    {
        $stmt = $this->conn->prepare("SELECT * FROM User WHERE Phone=?");
        $stmt->bind_param("s",$phone);
        $stmt->execute();
        $stmt->store_result();

        if($stmt->num_rows > 0)
        {
            $stmt->close();
            return true;
        }
        else{
            $stmt->close();
            return false;
        }
    } 

    function checkExistsUserAccount($email,$password)
    {
        $stmt = $this->conn->prepare("SELECT * FROM User WHERE Email=? && Password=?");
        $stmt->bind_param("ss",$email,$password);
        $stmt->execute();
        $stmt->store_result();

        if($stmt->num_rows > 0)
        {
            $stmt->close();
            return true;
        }
        else{
            $stmt->close();
            return false;
        }
    } 

    /*
     *Register new user
     *return User object if user was created
     *return false and show error message if have exception
     */
    public function registerNewUser($phone,$name,$birthdate,$email,$password,$address,$barangay)
    {
        $stmt = $this->conn->prepare("INSERT INTO User(Phone,Name,Birthdate,Email,Password,Address,Barangay) VALUES (?,?,?,?,?,?,?)");
        $stmt->bind_param("sssssss",$phone,$name,$birthdate,$email,$password,$address,$barangay);
        $result=$stmt->execute();
        $stmt->close();

        if($result)
        {
            $stmt=$this->conn->prepare("SELECT * FROM User WHERE Phone=?");
            $stmt->bind_param("s",$phone);
            $stmt->execute();
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();
            return $user;
        }
        else
            return false;
    }

    /*
     *Get User Information
     *return User object if user exist
     *return NULL if user is not exists
     */
    public function getUserInformation($phone)
    {
        $stmt = $this->conn->prepare("SELECT * FROM User WHERE Phone=?");
        $stmt->bind_param("s",$phone);

        if($stmt->execute())
        {
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();

            return $user;
        }
        else
            return NULL;
    }

    /*
     *Get Banners
     *return List of Banners
     */
    public function getBanners()
    {
        //Select 5 newest banner
        $result = $this->conn->query("SELECT * FROM Banner ORDER BY ID LIMIT 5");

        $banners = array();

        while($item = $result->fetch_assoc())
            $banners[] = $item;
        return $banners;    
    }

    /*
     *Get Menu
     *return List of Menu
     */
    public function getMenu()
    {
        //Select 5 newest banner
        $result = $this->conn->query("SELECT * FROM Menu");

        $menu = array();

        while($item = $result->fetch_assoc())
            $menu[] = $item;
        return $menu;    
    }

    /*
     *Get Drink base Menu ID
     *return List of Drink
     */
    public function getDrinkByMenuID($menuId)
    {
        $query = "SELECT * FROM Drink WHERE MenuId='".$menuId."'";
        $result = $this->conn->query($query);

        $drinks = array();

        while($item = $result->fetch_assoc())
            $drinks[] = $item;
        return $drinks;    
    }

    /*
     *Update Avatar url
     *return TRUE OR FALSE
     */
    public function updateAvatar($phone,$fileName)
    {
        return $result = $this->conn->query("UPDATE user SET avatarUrl='$fileName' WHERE Phone='$phone'");
    }

    /*
     *GET ALL DRINKS
     *return List of Drinks or Empty
     */
    public function getAllDrinks()
    {
        $result = $this->conn->query("SELECT * FROM drink WHERE 1") or die($this->conn->error);

        $drinks = array();
        while($item = $result->fetch_assoc())
            $drinks[] = $item;
        return $drinks;    
    }

    /*
     *INSERT NEW ORDER
     *return TRUE or FALSE
     */
    public function insertNewOrder($orderPrice,$orderComment,$orderAddress,$orderDetail,$userPhone,$paymentMethod)
    {
          $stmt = $this->conn->prepare("INSERT INTO `order`(`OrderDate`,`OrderStatus`, `OrderPrice`, `OrderDetail`, `OrderComment`, `OrderAddress`, `UserPhone`,`PaymentMethod`) VALUES (NOW(),0,?,?,?,?,?,?)")
          or die($this->conn->error);
          $stmt->bind_param("ssssss",$orderPrice,$orderDetail,$orderComment,$orderAddress,$userPhone,$paymentMethod);
          $result = $stmt->execute();
          $stmt->close();

          if($result)
          {
              $stmt = $this->conn->prepare("SELECT * FROM `order` WHERE `UserPhone`=? ORDER BY OrderId DESC LIMIT 1")
              or die ($this->conn->error);
              $stmt->bind_param("s",$userPhone);
              $stmt->execute();
              $order = $stmt->get_result()->fetch_assoc();
              $stmt->close();
              return $order;
            }
          else  {
              return false;
            }  
    }


/*
 *Insert New Menu (Category)
 */
    public function insertNewCategory($name,$imgPath)
    {
          $stmt = $this->conn->prepare("INSERT INTO `menu`(`Name`, `Link`) VALUES (?,?)")
          or die($this->conn->error);
          $stmt->bind_param("ss",$name,$imgPath);
          $result = $stmt->execute();
          $stmt->close();

          if($result)
              return true;
          else    
              return false;
    }

    /*
     * UPDATE Category
     * Return TRUE or FALSE
     */
    public function updateCategory($id,$name,$imgPath)
    {
        $stmt = $this->conn->prepare("UPDATE `menu` SET `Link` = ?,`Name` = ? WHERE `ID` = ?");
        $stmt->bind_param("sss",$imgPath,$name,$id);
        $result = $stmt->execute();
        return $result;
    }

    /*
     * DELETE Category
     * Return TRUE or FALSE
     */
    public function deleteCategory($id)
    {
        $stmt = $this->conn->prepare("DELETE FROM `menu` WHERE `ID` = ?");
        $stmt->bind_param("s",$id);
        $result = $stmt->execute();
        return $result;
    }

    /*
     *Insert New Drink (Product)
     */
    public function insertNewDrink($name,$imgPath,$price,$menuId)
    {
        $stmt = $this->conn->prepare("INSERT INTO `drink`(`Name`, `Link`, `Price`, `MenuId`) VALUES (?,?,?,?)")
        or die($this->conn->error);
        $stmt->bind_param("ssss",$name,$imgPath,$price,$menuId);
        $result = $stmt->execute();
        $stmt->close();

        if($result)
            return true;
        else    
            return false;
    }

    /*
     * UPDATE Product
     * Return TRUE or FALSE
     */
    public function updateProduct($id,$name,$imgPath,$price,$menuId)
    {
        $stmt = $this->conn->prepare("UPDATE `drink` SET `Link` = ?,`Name` = ?,`Price` = ?,`MenuId` = ? WHERE `ID` = ?");
        $stmt->bind_param("sssss",$imgPath,$name,$price,$menuId,$id);
        $result = $stmt->execute();
        return $result;
    }

    /*
     * DELETE Product
     * Return TRUE or FALSE
     */
    public function deleteProduct($id)
    {
        $stmt = $this->conn->prepare("DELETE FROM `drink` WHERE `ID` = ?");
        $stmt->bind_param("s",$id);
        $result = $stmt->execute();
        return $result;
    }

    /*
     * GET ALL ORDERS BASE ON USER PHONE AND STATUS
     * Return LIST OR NULL
     */
    public function getOrderByStatus($userPhone,$status)
    {
        $query = "SELECT * FROM `order` WHERE `OrderStatus` = '" . $status . "' AND `UserPhone` = '" . $userPhone . "'";
        $result = $this->conn->query($query) or die($this->conn->error);

        $orders = array();
        while($order = $result->fetch_assoc())
            $orders[] = $order;
        return $orders;    
    }

    /*
     * GET ALL ORDERS BASE STATUS (SERVER)
     * Return LIST OR NULL
     */
    public function getOrderServerByStatus($status)
    {
        $query = "SELECT * FROM `order` WHERE `OrderStatus` = '" . $status . "'";
        $result = $this->conn->query($query) or die($this->conn->error);

        $orders = array();
        while($order = $result->fetch_assoc())
            $orders[] = $order;
        return $orders;    
    }

    /*
     * INSERT OR UPDATE TOKEN
     * Return Token object or FALSE
     */
    public function insertToken($phone,$token,$isServerToken)
    {
         $stmt = $this->conn->prepare("INSERT INTO token(phone,token,isServerToken) VALUES (?,?,?) ON DUPLICATE KEY UPDATE token=?,isServerToken=?")
             or die($this->conn->error);
         $stmt->bind_param("sssss",$phone,$token,$isServerToken,$token,$isServerToken);    
         $result = $stmt->execute();
         $stmt->close();

         //Check for sucessful store
         if($result)
         {
             $stmt=$this->conn->prepare("SELECT * FROM token where phone=?");
             $stmt->bind_param("s",$phone);
             $stmt->exeute();
             $user = $stmt->get_result()->fetch_assoc();
             $stmt->close();
             return $user;
         }else
         {
             return false;
         }
    }

    /*
     * GET NEARBY STORE
     * Return LIST stores or FALSE
     */

     public function getNearbyStore($lat,$lng)
     {
         $result = $this->conn->query("SELECT id,name,lat,lng, ROUND(111.045 * DEGREES(ACOS(COS(RADIANS($lat))"
         ."* COS(RADIANS(lat))"
            ."* COS(RADIANS(lng) - RADIANS($lng))"
            ."+ SIN(RADIANS($lat))"
            ."* SIN(RADIANS(lat)))),2)"
            ." AS distance_in_km FROM store"
            ." ORDER BY distance_in_km ASC") or die($this->conn->error);
        $stores = array();
        while($store = $result->fetch_assoc())
            $stores[] = $store;
        return $stores;    
     }

     /*
     * SERVER APP UPDATE ORDER
     * Return result or FALSE
     */

     public function updateOrderStatus($phone,$orderId,$status)
     {
         $stmt = $this->conn->prepare("UPDATE `order` SET `OrderStatus` = ? WHERE `UserPhone`= ? AND `OrderId` = ?")
            or die($this->conn->error);
         $stmt->bind_param("sss",$status,$phone,$orderId);  
         $result = $stmt->execute();
         return $result;
     }

     /*
     * GET TOKEN
     * Return result or FALSE
     */

    public function getToken($phone,$isServerToken)
    {
        $stmt = $this->conn->prepare("SELECT * FROM `token` WHERE phone=? AND isServerToken=?") or die($this->conn->error);
        $stmt->bind_param("ss",$phone,$isServerToken);  
        $result = $stmt->execute();
        $token = $stmt->get_result()->fetch_assoc();
        $stmt->close();
        return $token;
    }

}

?>