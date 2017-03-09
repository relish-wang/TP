<?php 
defined('BASEPATH') OR exit('No direct script access allowed');
header("content-type:text/html;charset=utf-8");

class Session_Model extends CI_Model
{
    public function __construct()
    {
        parent::__construct();
    }

    public function SessionCheck($uid)
    {
    	$res = $this->db->query("SELECT phone FROM session WHERE uid='$uid'");
    	return $res->num_rows() > 0 ? $res->row_array() : NULL;
    }
}
?>