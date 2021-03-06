var baseUrl = 'http://192.168.1.28:8080/tbar/';

var scripts = document.getElementsByTagName('script');
var currentScript = scripts[scripts.length - 1];
var scriptSrc = currentScript.src;
var msisdn = scriptSrc.substring(scriptSrc.indexOf("toolbar_hosting_msisdn=") + 23);

var head = document.getElementsByTagName('head')[0];

var script = document.createElement('script');
script.src = baseUrl + 'static/js/jquery.1.8.3.min.js';
script.type = 'text/javascript';
head.appendChild(script);

//var swipeScript = document.createElement('script');
//swipeScript.src = baseUrl + 'static/js/jquery.touchSwipe.js';
//swipeScript.type = 'text/javascript';
//head.appendChild(swipeScript);

//var pageScript = document.createElement('script');
//pageScript.src = baseUrl + 'static/js/toolbar_page.js';
//pageScript.type = 'text/javascript';
//head.appendChild(pageScript);

var requestUrl = window.location.href;
var linkUrl = window.location.protocol + "//" + window.location.host;
var phoneno = "60195041441";
//try{
//	if(toolbar_hosting_phoneno != null || toolbar_hosting_phoneno != undefined){
//		phoneno = toolbar_hosting_phoneno;
//	}
//}
//catch(e){
//}

var userAgent = navigator.userAgent;
var referrer = document.referrer;

document.ready = function() {
	var pageUrl = 'index.jsp';
	$.ajax({
		type: "get",
		async: false,
		url: baseUrl + 'portal/isToolbarAvalible',	
		data: {
			'mobileNo': phoneno,
			'userAgent' : userAgent
		},
		headers:{
			'msisdn':phoneno,
			'imei': '359253066609210',
			'sgsnip': '203.82.88.146',
			'msip': '10.172.0.98',
			'apn': 'celcomprepaid'
		},
		dataType: "jsonp",
		jsonp: "jsonpCallback",
		success : function(data) {
			var result = eval( "(" + data.result + ")" );
			var isFirstUseToolbar = result.body.isFirstUseToolbar;
			if(isFirstUseToolbar){
				$.ajax({
					url:baseUrl + 'portal/getPage',
					type:"post",
					dataType: "jsonp",
					jsonp: "jsonpCallback",
					data:{
						pageUrl: 'welcome.jsp?suid=' + result.body.suid+'&serviceType='+result.body.serviceType
					},
					success:function(content){
						$('body').append(content.result);
					}
				});
			}else{
				if('0'==result.body.retCode&&'0'==result.body.openToolbar){
					var tOperatorId=result.body.tOperatorId;
					var userId=result.body.userId;
					var timeZone=result.body.timeZone;
					var language=result.body.language;
					var countryNo=result.body.countryNo;
					var suid=result.body.suid;
					var serviceType=result.body.serviceType;
					var flowThreshold = result.body.flowThreshold;
					var isAlertOpen = result.body.isAlertOpen;
					pageUrl = 'toolbar/index.jsp';
					$.ajax({
						type: "get",
						async: false,
						url: baseUrl + 'portal/getBallContent',
						data: {
							'pageUrl': pageUrl,
							'requestUrl': requestUrl,
							'mobilephone': phoneno,
							'userAgent': userAgent,
							'referrer': referrer,
							'language': language,
							'isFirstUseToolbar' : isFirstUseToolbar,
							'suid' : suid,
							'serviceType':serviceType,
							'flowThreshold':flowThreshold,
							'isAlertOpen':isAlertOpen
						},
						dataType: "jsonp",
						jsonp: "jsonpCallback",
						success : function(data) {
							$("body").append(data.result);
						},
						error:function(){
							//alert('fail');
						}
					});
				}else{
					pageUrl = 'index.jsp';
				}
			}			
		},
		error:function(){
		}
	});

}

//显示引导页
function showIntro(basePath,suid,serviceType){
    $.ajax({
        url:basePath + 'portal/welHasShown',
        type:'get',
        data:{
            suid:suid
        },
        dataType:'jsonp',
        jsonp:'jsonpCallback',
        success:function(ret){
            if(ret == 'SUCCESS'){
                $("#popRemind").hide();
                $('#d_h').show();
                $('#d_h').html('<iframe id="toolbar_help" src="" style="width:100%; height:100%; z-index:2100000000;border:none;padding:0;margin:0; background:#fff;"></iframe>');
                var prefix="";
                if(serviceType=='Prepaid'){
                    prefix="pre";
                }else{
                    prefix="post";
                }
                $("#toolbar_help").attr("src",basePath + prefix+'Help.jsp');
            }else{
                alert('server occur error');
            }
        },
        error:function(){
            alert('server occur error!');
        }
    });
}