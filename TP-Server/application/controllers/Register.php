<?php 
defined('BASEPATH')OR exit('No direct script access allowed');
header("content-type:text/html;charset=utf-8");

class Register extends CI_Controller
{
	
	public function __construct()
	{
		parent::__construct();
		$this->load->model('User_model','user',TRUE);
	}

	public function Register()
	{
		$postjson = file_get_contents('php://input');
		$res = json_decode($postjson,TRUE);

		$this->data['phone'] = isset($res['phone']) ? $res['phone'] : null;
		$this->data['password'] = isset($res['password']) ? $res['password'] : null;
		$this->data['name'] = isset($res['name']) ? $res['name'] : null;
		$this->data['realName'] = isset($res['realName']) ? $res['realName'] : null;
		
		/**
		* 调试数据
		*/ 
		// $this->data['phone'] = '13777841010';
		// $this->data['password'] = 'sblz';
		// $this->data['name'] = 'sblz';
		// $this->data['realName'] = '李镇';

		$query = $this->user->UserAdd($this->data['phone'],$this->data['password'],$this->data['name'],$this->data['realName']);

		if($query){
			$arr = array('resultCode' => 0, 'resultMessage' => '成功注册新用户!');
		}else{
			$arr = array('resultCode' => 1, 'resultMessage' => '注册失败!');
		}
		echo json_encode($arr);
		unset($this->data);
	}
	
}
?>