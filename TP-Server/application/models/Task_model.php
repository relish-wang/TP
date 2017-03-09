<?php
defined('BASEPATH') OR exit('No direct script access allowed');
header("content-type:text/html;charset=utf-8");

class Task_model extends CI_Model
{
    public function __construct()
    {
        parent::__construct();
    }
    
    public function TaskAdd($task){
    	$this->db->query('INSERT INTO task VALUES(?,?,?,?,?,?,?)',$task);
    	$query = $this->db->affected_rows() > 0 ? true : false;
    	return $query;
    }

    public function TaskDelete($order_id)
    {
    	$this->db->query("DELETE FROM task WHERE id = '$order_id'");
    	$query = $this->db->affected_rows() > 0 ? true : false;
    	$this->db->close();
    	return $query;
    }

    public function GetCostById($order_id)
    {
        $query = $this->db->query("SELECT cost FROM task WHERE id = '$order_id'");
        $task = $query->num_rows() > 0 ? $query->row_array() : null;
        return $task;
    }
}
?>