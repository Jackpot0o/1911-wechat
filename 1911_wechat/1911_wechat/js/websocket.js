
	var websocket;

	window.ws = {
		init:function(param){
			initWebSocket(param);
		}
	}

	function initWebSocket(param){
		
		// 1判断浏览器是否支持WebSocket
		if(window.WebSocket){
			
			// 2.连接WebSocket服务器
			websocket = new WebSocket("ws://192.168.1.101:8084/");
			
			websocket.onopen = function(){ 
			
				param.onopen();
			};
			
			websocket.onclose = function(){
				
				param.onclose();
				
			};
			
			websocket.onmessage = function(resp){
				var data = resp.data;
				if(data == "heard"){
					console.info(data);
					clearTimeout(closeConnTime); // 清除定时关闭的连接
					closeConn();
					return ;
				}
				param.onmessage(data);
			};
			
		}else{
			alert("不支持WebSocket");
		}
	}

	// 5s发送一个心跳
	var sendHeardTime;
	function sendHeard(){
		sendHeardTime = setInterval(function(){
			var param = {"type":2}; // 心跳
			sendObj(param);
		},5000);
	}

	// 关闭连接
	var closeConnTime;
	function closeConn(){
		closeConnTime = setTimeout(function() {
			websocket.close();
		}, 10000);
	}

	// 重新连接
	function reConn(){
		console.info("重连。。");
		setTimeout(function(){
			init();
		},5000);
		
	}

	// 发送对象
	function sendObj(obj){
		sendStr(JSON.stringify(obj));
	}

	// 发送一个字符串
	function sendStr(str){
		websocket.send(str);
	}