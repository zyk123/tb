var userAgent = navigator.userAgent;
var referrer = document.referrer;
var requestUrl = window.location.href;

function recordVist(suid) {
	//记录页面访问事件
	$.ajax({
		type: "get",
		async: true,
		url: baseUrl + "portal/recordVisitAction",
		data: {
			'userAgent': userAgent,
			'referer': referrer,
			'curUrl': requestUrl,
			'browseUrl': browseUrl,
			'suid': suid
		},
		dataType: "jsonp",
		jsonp: "jsonpCallback",
		success : function(data) {
			//console.log(data.result);
			//var result = eval( "(" + data.result + ")" );
		},
		error:function(){
		}
	});
}

function recordClickEvent(suid, clickName) {
	//记录页面点击事件
	$.ajax({
		type: "get",
		async: true,
		url: baseUrl + "portal/recordClickEventAction",
		data: {
			'userAgent': userAgent,
			'referer': referrer,
			'curUrl': requestUrl,
			'clickName': clickName,
			'suid': suid
		},
		dataType: "jsonp",
		jsonp: "jsonpCallback",
		success : function(data) {
			//console.log(data.result);
			//var result = eval( "(" + data.result + ")" );
		},
		error:function(){
		}
	});
}