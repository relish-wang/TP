<?php 
defined('BASEPATH') OR exit('No direct script access allowed');
header("content-type:text/html;charset=utf-8");

class Privilege_model extends CI_Model
{
	
	public function __construct()
	{
		parent::__construct();
	}

	public function IsLogin($phone,$password)
	{
		$query = $this->db->query('SELECT name,realName,address,logo,cost FROM user WHERE phone = ? AND password = ?',array($phone,$password));
		return $query->num_rows() > 0 ? $query->row_array() : NULL;
	}

	public function UidSelectByPhone($phone)
	{
		$query = $this->db->query("SELECT uid FROM session WHERE phone = '$phone'");
		return $query->num_rows() > 0 ? $query->row_array() : NULL;
	}

	public function SessionAdd($uid,$phone)
	{
		$this->db->query('INSERT INTO session VALUES(?,?)',array($uid,$phone));
		$query = $this->db->affected_rows() > 0 ? true : false;
		$this->db->close();
		return $query;
	}

	public function SessionDelByUid($uid)
	{
		$this->db->query("DELETE FROM session WHERE uid='$uid'");
		$query = $this->db->affected_rows() > 0 ? true : false;
		$this->db->close();
		return $query;
	}

	public function SessionDelByPhone($phone){
		$this->db->query("DELETE FROM session WHERE phone='$phone'");
		$query = $this->db->affected_rows() > 0 ? true : false;
		return $query;
	}
}
?>