<?php
defined('BASEPATH') OR exit('No direct script access allowed');
header("content-type:text/html;charset=utf-8");

class User_model extends CI_Model
{
    public function __construct()
    {
        parent::__construct();
    }

    public function UserAdd($phone,$password,$name,$realName)
    {
    	$this->db->query('INSERT INTO user(phone,password,name,realName) VALUES(?,?,?,?)', array($phone,$password,$name,$realName));
        $affect = $this->db->affected_rows() > 0 ? true : false;
        $this->db->close();
    	return $affect;
    }

    public function UserShow($phone)
    {
        $res = $this->db->query("SELECT * FROM user WHERE phone='$phone'");
        $this->db->close();
        return $res->num_rows() > 0 ? $res->row_array() : NULL;
    }

    public function PasswordUpdate($password,$phone)
    {
        $this->db->query("UPDATE user SET password='$password' WHERE phone='$phone'");
        $res = $this->db->affected_rows() > 0 ? true : false;
        $this->db->close();
        return $res;
    }

    public function NameUpdate($name,$phone)
    {
        $this->db->query("UPDATE user SET name = '$name' WHERE phone = '$phone'");
        $res = $this->db->affected_rows() > 0 ? true : false;
        $this->db->close();
        return $res;
    }

    public function RealNameUpdate($realName,$phone)
    {
        $this->db->query("UPDATE user SET realName = '$realName' WHERE phone = '$phone'");
        $res = $this->db->affected_rows() > 0 ? true : false;
        $this->db->close();
        return $res;
    }

    public function AddressUpdate($address,$phone)
    {
        $this->db->query("UPDATE user SET address = '$address' WHERE phone = '$phone'");
        $res = $this->db->affected_rows() > 0 ? true : false;
        $this->db->close();
        return $res;
    }

    public function LogoUpdate($logo,$phone)
    {
        $this->db->query("UPDATE user SET logo = '$logo' WHERE phone = '$phone'");
        $res = $this->db->affected_rows() > 0 ? true : false;
        $this->db->close();
        return $res;
    }

    public function CostUpdate($taskCost,$order_id)
    {
        $this->db->query("UPDATE user SET cost = (cost-'$taskCost') WHERE phone = (SELECT userA FROM orders WHERE id = '$order_id')");
        $userA = $this->db->affected_rows() > 0 ? true : false;
        $this->db->query("UPDATE user SET cost = (cost+'$taskCost') WHERE phone = (SELECT userB FROM
            orders WHERE id = '$order_id')");
        $userB = $this->db->affected_rows() > 0 ? true : false;
        $this->db->close();
        return $userA&&$userB ? true : false;
    }
}
?>