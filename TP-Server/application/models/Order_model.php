<?php
defined('BASEPATH') OR exit('No direct script access allowed');
header("content-type:text/html;charset=utf-8");

class Order_model extends CI_Model
{
    public function __construct()
    {
        parent::__construct();
    }
    
    public function OrderAdd($order)
    {
    	$this->db->query('INSERT INTO orders(id,userA) VALUES(?,?)',$order);
    	$query = $this->db->affected_rows() > 0 ? true : false;
    	$this->db->close();
    	return $query;
    }

    public function OrderListForIndex($currentPage,$pageSize)
    {
        $query  =  $this->db->query("SELECT orders.id,state,name,source,timeGet,destination,timeSend,user.logo,task.cost FROM orders,task,user WHERE orders.id = task.id AND orders.userA = user.phone AND state = 0 LIMIT ?,?",array($currentPage,$pageSize));
        $orders = $query->num_rows() > 0 ? $query->result_array() : null;
        $this->db->close();
        return $orders;
    }

    public function OrderListForReceived($args)
    {
        $query = $this->db->query("SELECT orders.id,state,name,source,timeGet,destination,timeSend,user.logo,task.cost FROM orders,task,user WHERE orders.id = task.id AND orders.userA = user.phone AND state = 1 AND orders.userB=? LIMIT ?,?", $args);
        $orders = $query->num_rows() > 0 ? $query->result_array() : null;
        $this->db->close();
        return $orders;
    }

    public function OrderListForSent($args)
    {
        $query = $this->db->query("SELECT orders.id,state,name,source,timeGet,destination,timeSend,user.logo,task.cost FROM orders,task,user WHERE orders.id = task.id AND orders.userA = user.phone AND orders.userA=? LIMIT ?,?", $args);
        $orders = $query->num_rows() > 0 ? $query->result_array() : null;
        $this->db->close();
        return $orders;
    }

    public function OrderDetail($order_id)
    {
        $query = $this->db->query("SELECT orders.id,userA,userB,state,source,timeGet,destination,timeSend,content,cost FROM orders,task WHERE orders.id = ? AND task.id = ?",array($order_id,$order_id));
        $detail = $query->num_rows() > 0 ? $query->row_array() : null;
        $this->db->close();
        return $detail;
    }

    public function OrderDelete($order_id)
    {
        $this->db->query("DELETE FROM orders WHERE id = '$order_id'");
        $query = $this->db->affected_rows() > 0 ? true : false;
        return $query;
    }

    public function StateUpdate1($phone,$order_id)
    {
        $this->db->query("UPDATE orders SET state=1,userB=? WHERE id=?",array($phone,$order_id));
        $query = $this->db->affected_rows() > 0 ? true : false;
        $this->db->close();
        return $query;
    }

    public function StateUpdate2($order_id)
    {
        $this->db->query("UPDATE orders SET state=2 WHERE id='$order_id'");
        $query = $this->db->affected_rows() > 0 ? true : false;
        $this->db->close();
        return $query;
    }
}
?>