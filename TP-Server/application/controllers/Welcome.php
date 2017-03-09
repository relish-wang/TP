<?php
defined('BASEPATH') OR exit('No direct script access allowed');
header("content-type:text/html;charset=utf-8");

class Welcome extends CI_Controller 
{
	private $data = array();

	public function __construct()
	{
		parent::__construct();
		$this->load->model('Privilege_model','privilege',TRUE);

		$postjson = file_get_contents('php://input');
		$res = json_decode($postjson,TRUE);

		$this->data['phone'] = isset($res['phone']) ? $res['phone'] : NULL;
		$this->data['password'] = isset($res['password']) ? $res['password'] : NULL;

		/**
		* 调试数据
		*/
		$this->data['phone'] = "13777841010";
		$this->data['password'] = "sblz";
	}

	public function index()
	{

		$query = $this->privilege->UidSelectByPhone($this->data['phone']);
		if($query != NULL) {
			$query = $this->privilege->SessionDelByPhone($this->data['phone']);
			if ($query) {
				$this->Login();
			}
		}else {
			$this->Login();
		}
	}

	public function Login()
	{
		$query = $this->privilege->IsLogin($this->data['phone'],$this->data['password']);
		if ($query != NULL) {
			
			$this->data['name'] = $query['name'];
			$this->data['realName'] = $query['realName'];
			$this->data['address'] = $query['address'];
			$this->data['cost'] = $query['cost'];
			$this->data['logo'] = $query['logo'];
			
			$this->data['uid'] = md5(uniqid());
			$sess = $this->privilege->SessionAdd($this->data['uid'],$this->data['phone']);
			if ($sess) {
				$arr = array('resultCode' => 0 , 
					 	 'resultMessage' => '登陆成功',
					 	 'data' => array('uid' => $this->data['uid'],
					 	 				 'phone' => $this->data['phone'],
					 	 				 'name' => $this->data['name'],
					 	 				 'realName' => $this->data['realName'],
					 	 				 'address' => $this->data['address'],
					 	 				 'cost' => $this->data['cost'],
					 	 				 'logo' => $this->data['logo'])
					 	);
			}
			unset($this->data);
		}else{
			$arr = array('resultCode' => 1 , 
					 	 'resultMessage' => '登陆失败');
			unset($this->data);
		}
		echo json_encode($arr);
		unset($this->data);
	}

	public function Logout()
	{
		$postjson = file_get_contents('php://input');
		$res = json_decode($postjson,TRUE);

		// $res['uid'] = 'a3f23a5bea383e62f832c30bc9aa0068';
		$query = $this->privilege->SessionDelByUid($res['uid']);
		if ($query) {
			$arr = array('resultCode' => 0, 'resultMessage' => '已登出!');
		}else {
			$arr = array('resultCode' => 1, 'resultMessage' => '登出失败或未在线!');
		}
		echo json_encode($arr);
	}
}
?>