<?php 
defined('BASEPATH') OR exit('No direct script access allowed');
header("content-type:text/html;charset=utf-8");

class Order extends CI_Controller
{
    public function __construct()
    {
    	parent::__construct();
        $this->load->model('Order_model','order',TRUE);
        $this->load->model('Session_model','sessionSql',TRUE);
        $this->load->model('User_model','user',TRUE);
        $this->load->model('Task_model','task',TRUE);
    }

    public function OrderAdd()
    {
    	$postjson = file_get_contents('php://input');
    	$res = json_decode($postjson,TRUE);

    	$this->data['uid'] = isset($res['uid']) ? $res['uid'] : null;
    	$this->data['source'] = isset($res['source']) ? $res['source'] : null;
    	$this->data['timeGet'] = isset($res['timeGet']) ? $res['timeGet'] : null;
    	$this->data['destination'] = isset($res['destination']) ? $res['destination'] : null;
    	$this->data['timeSend'] = isset($res['timeSend']) ? $res['timeSend'] : null;
    	$this->data['content'] = isset($res['content']) ? $res['content'] : null;
    	$this->data['cost'] = isset($res['cost']) ? $res['cost'] : null;

  		// $this->data['uid'] = 'c1a23dd4b37b3038dc5c244751cc09a4';
    // 	$this->data['source'] = 'aaaa';
    // 	$this->data['timeGet'] = 'bbbb';
    // 	$this->data['destination'] = 'cccc';
    // 	$this->data['timeSend'] = 'dddd';
    // 	$this->data['content'] = 'aaabbbcccddd';
    // 	$this->data['cost'] = '20';

    	$sess = $this->sessionSql->SessionCheck($this->data['uid']);
    	if ($sess != null) {
    		$this->data['phone'] = $sess['phone'];
    		date_default_timezone_set('PRC');
    		//order/task id (same)
    		$this->data['id'] = date("YmdHis").'_'.$this->data['phone'];

    		$task = array(  $this->data['id'],
                            $this->data['source'],
                            $this->data['timeGet'],
                            $this->data['destination'],
                            $this->data['timeSend'],
                            $this->data['content'],
                            $this->data['cost'] );
			
			$taskQuery = $this->task->TaskAdd($task);
			if($taskQuery){
				$order = array($this->data['id'], $this->data['phone']);
				$orderQuery = $this->order->OrderAdd($order);
				if ($orderQuery) {
					$arr = array('resultCode' => 0, 'resutlMessage' => '发单成功!');
				}else{
					$arr = array('resultCode' => 1, 'resutlMessage' => '发单失败!');
				}
			}else {
				$arr = array('resultCode' => 1, 'resutlMessage' => '发单失败!');
			}
    	}else {
    		$arr = array('resultCode' => -1, 'resutlMessage' => '登陆超时' );
    	}

    	echo json_encode($arr);
    	unset($this->data);
    }

    public function OrderListForIndex()
    {
        $postjson = file_get_contents('php://input');
        $res = json_decode($postjson,TRUE);

        $this->data['uid'] = isset($res['uid']) ? $res['uid'] : null;
        $this->data['currentPage'] = isset($res['currentPage']) ? $res['currentPage'] : null;
        $this->data['pageSize'] = isset($res['pageSize']) ? $res['pageSize'] : null;

        // $this->data['uid'] = 'c1a23dd4b37b3038dc5c244751cc09a4';
        // $this->data['currentPage'] = 0;
        // $this->data['pageSize'] = 10;

        $sess = $this->sessionSql->SessionCheck($this->data['uid']);
        if ($sess != null) {
            $orderList = $this->order->OrderListForIndex($this->data['currentPage'],$this->data['pageSize']);
            if($orderList != null){
                $arr = array('resultCode' => 0, 
                             'resutlMessage' => '查询成功',
                             'data' => $orderList);
            }else {
                $arr = array('resultCode' => 1, 'resutlMessage' => '查询失败');
            }
        }else {
            $arr = array('resultCode' => -1, 'resutlMessage' => '登陆超时');
        }

        echo json_encode($arr);
        unset($this->data);
    }

    public function OrderListForReceived()
    {
        $postjson = file_get_contents('php://input');
        $res = json_decode($postjson,TRUE);

        $this->data['uid'] = isset($res['uid']) ? $res['uid'] : null;
        $this->data['currentPage'] = isset($res['currentPage']) ? $res['currentPage'] : null;
        $this->data['pageSize'] = isset($res['pageSize']) ? $res['pageSize'] : null;

        // $this->data['uid'] = 'd49f5dec2decb056be16ecceea5aa657';
        // $this->data['currentPage'] = 0;
        // $this->data['pageSize'] = 10;

        $sess = $this->sessionSql->SessionCheck($this->data['uid']);
        if ($sess != null) {
            $args = array($sess['phone'],$this->data['currentPage'],$this->data['pageSize']);
            $orderList = $this->order->OrderListForReceived($args);
            if ($orderList != null) {
                $arr = array('resultCode' => 0, 
                             'resutlMessage' => '查询成功',
                             'data' => $orderList);
            }else{
                $arr = array('resultCode' => 1,
                             'resutlMessage' => '查询失败');
            }
        }else {
            $arr = array('resultCode' => -1, 'resutlMessage' => '登陆超时');
        }

        echo json_encode($arr);
        unset($this->data);
    }

    public function OrderListForSent()
    {
        $postjson = file_get_contents('php://input');
        $res = json_decode($postjson,TRUE);

        $this->data['uid'] = isset($res['uid']) ? $res['uid'] : null;
        $this->data['currentPage'] = isset($res['currentPage']) ? $res['currentPage'] : null;
        $this->data['pageSize'] = isset($res['pageSize']) ? $res['pageSize'] : null;

        // $this->data['uid'] = 'c1a23dd4b37b3038dc5c244751cc09a4';
        // $this->data['currentPage'] = 0;
        // $this->data['pageSize'] = 10;

        $sess = $this->sessionSql->SessionCheck($this->data['uid']);
        if ($sess != null) {
            $args = array($sess['phone'],$this->data['currentPage'],$this->data['pageSize']);
            $orderList = $this->order->OrderListForSent($args);
            if ($orderList != null) {
                $arr = array('resultCode' => 0, 
                             'resutlMessage' => '查询成功',
                             'data' => $orderList);
            }else{
                $arr = array('resultCode' => 1,
                             'resutlMessage' => '查询失败');
            }
        }else {
            $arr = array('resultCode' => -1, 'resutlMessage' => '登陆超时');
        }

        echo json_encode($arr);
        unset($this->data);
    }

    public function OrderDetail()
    {
        $postjson = file_get_contents('php://input');
        $res = json_decode($postjson,TRUE);

        $this->data['uid'] = isset($res['uid']) ? $res['uid'] : null;
        $this->data['order_id'] = isset($res['id']) ? $res['id'] : null;

        // $this->data['uid'] = "c1a23dd4b37b3038dc5c244751cc09a4";
        // $this->data['order_id'] = "20160406232717_13588015024";

        $sess = $this->sessionSql->SessionCheck($this->data['uid']);
        if ($sess != null) {
            $detail = $this->order->OrderDetail($this->data['order_id']);
            if ($detail != null) {
                $arr = array('resultCode' => 0, 
                             'resutlMessage' => '查询成功',
                             'data' => $detail);
            }else {
                $arr = array('resultCode' => 1, 'resutlMessage' => '查询失败');
            }
        }else {
            $arr = array('resultCode' => -1, 'resutlMessage' => '登陆超时');
        }

        echo json_encode($arr);
        unset($this->data);
    }

    public function OrderDelete()
    {
        $postjson = file_get_contents('php://input');
        $res = json_decode($postjson,TRUE);

        $this->data['uid'] = isset($res['uid']) ? $res['uid'] : null;
        $this->data['order_id'] = isset($res['id']) ? $res['id'] : null;

        // $this->data['uid'] = 'c1a23dd4b37b3038dc5c244751cc09a4';
        // $this->data['order_id'] = '20160406232951_13588015024';

        $sess = $this->sessionSql->SessionCheck($this->data['uid']);
        if ($sess != null) {
            $queryOrder = $this->order->OrderDelete($this->data['order_id']);
            $queryTask = $this->task->TaskDelete($this->data['order_id']);

            if($queryOrder && $queryTask){
                $arr = array('resultCode' => 0, 'resutlMessage' => '删除成功');
            }else {
                $arr = array('resultCode' => 1, 'resutlMessage' => '删除失败');
            }
        }else{
            $arr = array('resultCode' => -1, 'resutlMessage' => '登陆超时');
        }

        echo json_encode($arr);
        unset($this->data);
    }

    public function StateUpdate1()
    {
        $postjson = file_get_contents('php://input');
        $res = json_decode($postjson,TRUE);

        $this->data['uid'] = isset($res['uid']) ? $res['uid'] : null;
        $this->data['order_id']  = isset($res['id']) ? $res['id'] : null;

        // $this->data['uid'] = "62a565ac4dd11c3fb4f9425ae1af79d4";
        // $this->data['order_id'] = "20160406232948_13588015024";

        $sess = $this->sessionSql->SessionCheck($this->data['uid']);
        if($sess != null){
            $state1 = $this->order->StateUpdate1($sess['phone'],$this->data['order_id']);
            if ($state1) {
                $arr = array('resultCode' => 0, 'resutlMessage' => '接单成功');
            }else{
                $arr = array('resultCode' => 1, 'resutlMessage' => '接单失败');
            }
        }else {
            $arr = array('resultCode' => -1, 'resutlMessage' => '登陆超时');
        }

        echo json_encode($arr);
        unset($this->data);
    }

    public function StateUpdate2()
    {
        $postjson = file_get_contents('php://input');
        $res = json_decode($postjson,TRUE);

        $this->data['uid'] = isset($res['uid']) ? $res['uid'] : null;
        $this->data['order_id']  = isset($res['id']) ? $res['id'] : null;

        $this->data['uid'] = "dc9a1e241e1c80e2eba721fb028e2a62";
        $this->data['order_id'] = "20160406232717_13588015024";

        $sess = $this->sessionSql->SessionCheck($this->data['uid']);
        if($sess != null){
            $state2 = $this->order->StateUpdate2($this->data['order_id']);
            $task = $this->task->GetCostById($this->data['order_id']);
            if ($task != null && $state2 ) {
                $pay = $this->user->CostUpdate($task['cost'],$this->data['order_id']);
            }
            
            if ($state2 && $pay) {
                $arr = array('resultCode' => 0, 'resutlMessage' => '确认收货成功');
            }else{
                $arr = array('resultCode' => 1, 'resutlMessage' => '确认收货失败');
            }
        }else {
            $arr = array('resultCode' => -1, 'resutlMessage' => '登陆超时');
        }

        echo json_encode($arr);
        unset($this->data);
    }

}
?>