function ajaxDelete(msg,url,callback){
    		var issure = confirm(msg)
    		if(issure){

    			$.ajax({
    				   type: "POST",
    				   url:url ,
    				   success: function(ret){
    					   if (ret.state == "ok") {
        			
    						   location.reload();
    						}
    					   alert(ret.msg);
    				   },
    				   error:function (XMLHttpRequest, textStatus, errorThrown) {
    					    // 通常 textStatus 和 errorThrown 之中
    					    // 只有一个会包含信息
    					 //   this; // 调用本次AJAX请求时传递的options参数
    					   alert('网络异常');
    					}
    				});
        		}
    		else{
    		
        		}
        	}