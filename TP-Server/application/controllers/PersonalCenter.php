<?php
defined('BASEPATH') OR exit('No direct script access allowed');
header("content-type:text/html;charset=utf-8");

class PersonalCenter extends CI_Controller
{
    public function __construct()
    {
        parent::__construct();
        $this->load->model('Session_model','sessionSql',TRUE);
        $this->load->model('User_model','user',TRUE);
    }

    public function UserData()
    {
    	$postjson = file_get_contents('php://input');
    	$res = json_decode($postjson,TRUE);

    	$this->data['uid'] = isset($res['uid']) ? $res['uid'] : null;

    	// $this->data['uid'] = 'aaaaaaa';
    	$sess = $this->sessionSql->SessionCheck($this->data['uid']);

    	if($sess != null){
    		$user = $this->user->UserShow($sess['phone']);
    		if($user != null){
    			// echo var_dump($user);
    			$arr = array('resultCode' => 0, 
    						 'resultMessage' => '查询成功',
    						 'data' => $user);
    		}else {
    			$arr = array('resultCode' => 1, 'resultMessage' => '该用户不存在！');
    		}
    	}else{
    		$arr = array('resultCode' => -1, 'resultMessage' => '用户未登录或被占用');
    	}
    	echo json_encode($arr);
    	unset($data);
    }

    public function PasswordUpdate()
    {
    	$postjson = file_get_contents('php://input');
    	$res = json_decode($postjson,TRUE);

    	$this->data['uid'] = isset($res['uid']) ? $res['uid'] : null;
    	$this->data['password'] = isset($res['password']) ? $res['password'] : null;

    	// $sess['phone'] = '13777841037';
    	// $this->data['password'] = 'www';
    	$sess = $this->sessionSql->SessionCheck($this->data['uid']);

    	if ($sess != null) {
    		$query = $this->user->PasswordUpdate($this->data['password'],$sess['phone']);
	    	if($query){
	    		$arr = array('resultCode' => 0, 'resultMessage' => '密码修改成功!' );
	    	}else {
	    		$arr = array('resultCode' => 1, 'resultMessage' => '密码修改失败!');
	    	}
    	}else {
    		$arr = array('resultCode' => -1, 'resultMessage' => '登陆超时');
    	}
    	
		echo json_encode($arr);
    	unset($this->data);
    }   

    public function NameUpdate()
    {
    	$postjson = file_get_contents('php://input');
    	$res = json_decode($postjson,TRUE);

    	$this->data['uid'] = isset($res['uid']) ? $res['uid'] : null;
    	$this->data['name'] = isset($res['name']) ? $res['name'] : null;

    	// $sess['phone'] = '13777841037';
    	// $this->data['name'] = '王绿原';
    	$sess = $this->sessionSql->SessionCheck($this->data['uid']);

    	if ($sess != null) {
    		$query = $this->user->NameUpdate($this->data['name'],$sess['phone']);
	    	if($query){
	    		$arr = array('resultCode' => 0, 'resultMessage' => '昵称修改成功!');
	    	}else {
	    		$arr = array('resultCode' => 1, 'resultMessage' => '昵称修改失败!');
	    	}
    	}else {
    		$arr = array('resultCode' => -1, 'resultMessage' => '登陆超时');
    	}
    	
		echo json_encode($arr);
    	unset($this->data);
    }

    public function RealNameUpdate()
    {
    	$postjson = file_get_contents('php://input');
    	$res = json_decode($postjson,TRUE);

    	$this->data['uid'] = isset($res['uid']) ? $res['uid'] : null;
    	$this->data['realName'] = isset($res['realName']) ? $res['realName'] : null;

    	// $sess['phone'] = '13777841037';
    	// $this->data['realName'] = '我叫王绿原';
    	$sess = $this->sessionSql->SessionCheck($this->data['uid']);

    	if ($sess != null) {
    		$query = $this->user->RealNameUpdate($this->data['realName'],$sess['phone']);
	    	if($query){
	    		$arr = array('resultCode' => 0, 'resultMessage' => '真实姓名修改成功!');
	    	}else {
	    		$arr = array('resultCode' => 1, 'resultMessage' => '真实姓名修改失败!');
	    	}
    	}else {
    		$arr = array('resultCode' => -1, 'resultMessage' => '登陆超时');
    	}
    	
    	echo json_encode($arr);
    	unset($this->data);
    }

    public function AddressUpdate()
    {
    	$postjson = file_get_contents('php://input');
    	$res = json_decode($postjson,TRUE);

    	$this->data['uid'] = isset($res['uid']) ? $res['uid'] : null;
    	$this->data['address'] = isset($res['address']) ? $res['address'] : null;

    	// $sess['phone'] = '13777841037';
    	// $this->data['address'] = '浙江传媒学院-20#227';
    	$sess = $this->sessionSql->SessionCheck($this->data['uid']);

    	if ($sess != null) {
    		$query = $this->user->AddressUpdate($this->data['address'],$sess['phone']);
	    	if($query){
	    		$arr = array('resultCode' => 0, 'resultMessage' => '地址修改成功!');
	    	}else {
	    		$arr = array('resultCode' => 1, 'resultMessage' => '地址修改失败!');
	    	}
    	}else{
    		$arr = array('resultCode' => -1, 'resultMessage' => '登陆超时');
    	}
    	
		echo json_encode($arr);
    	unset($this->data);
    }

    public function LogoUpdate()
    {
    	$postjson = file_get_contents('php://input');
    	$res = json_decode($postjson,TRUE);

    	$this->data['uid'] = isset($res['uid']) ? $res['uid'] : null;
    	$this->data['logo'] = isset($res['logo']) ? $res['logo'] : null;

    	// $sess['phone'] = '13777841037';
    	// $this->data['logo'] = 'qwerqwerqwerqwerqwerqwerqwer';
    	$sess  = $this->sessionSql->SessionCheck($this->data['uid']);

    	if ($sess != null) {
    		$query = $this->user->LogoUpdate($this->data['logo'],$sess['phone']);
	    	if($query){
	    		$arr = array('resultCode' => 0, 'resultMessage' => '头像修改成功!');
	    	}else{
	    		$arr = array('resultCode' => 1, 'resultMessage' => '头像修改失败!');
	    	}
    	}else{
    		$arr = array('resultCode' => -1, 'resultMessage' => '登陆超时');
    	}
    	
    	echo json_encode($arr);		
    	unset($this->data);
    }
}
?>