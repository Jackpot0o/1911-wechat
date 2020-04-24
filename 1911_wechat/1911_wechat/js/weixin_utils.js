window.ip={
	serverip:"http://192.168.1.101:8888/"
}

window.url={
	// 注册的url
	register_url:ip.serverip+"user/register",
	login_url:ip.serverip+"user/login",
	upload_head_url:ip.serverip+"res/uploadFile",
	getuserbyusername_url:ip.serverip+"user/getUserByUsername",
	friendapply_add_url:ip.serverip+"friendApply/addFriendApply",
	getMyFriendApplyList_url:ip.serverip+"friendApply/getMyFriendApplyList",
	updateFriendApplyStatus_url:ip.serverip+"friendApply/updateFriendApplyStatus",
	getFriendListByUid_url:ip.serverip+"friend/getFriendListByUid",
	findUserById_url:ip.serverip+"user/findUserById"
}

window.util={
	
	ajax:function(param){
		
		plus.nativeUI.showWaiting(); // 显示等待的圈
			
		mui.ajax(param.url,{
			data:param.data,
			dataType:'json',//服务器返回json格式数据
			type:'post',//HTTP请求类型
			success:function(data){
				plus.nativeUI.closeWaiting();
				param.success(data); // 调用用户传递过来的函数
			},
			error:function(xhr,type,errorThrown){
				plus.nativeUI.closeWaiting();
				//异常处理；
				plus.nativeUI.toast("服务端出现异常");
			}
		});
	},
	setUser:function(user){ // 保存用户到数据库
		plus.storage.setItem("login_user",JSON.stringify(user)); // value是字符串
	},
	getUser:function(){
		// 为了后期方操作,取出后直接转成JOSN对象
		return JSON.parse(plus.storage.getItem("login_user"));
	},
	setFriendList:function(fList){
		plus.storage.setItem("friendList",JSON.stringify(fList)); 
	},
	getFriendList:function(){
		return JSON.parse(plus.storage.getItem("friendList"));
	}
	
}