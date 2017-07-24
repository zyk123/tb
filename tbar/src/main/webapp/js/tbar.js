var baseUrl = 'http://58.27.120.214:8080/tbar/';

var scripts = document.getElementsByTagName('script');
var currentScript = scripts[scripts.length - 1];
var scriptSrc = currentScript.src;
var msisdn = scriptSrc.substring(scriptSrc.indexOf("toolbar_hosting_msisdn=") + 23);

var head = document.getElementsByTagName('head')[0];

var script = document.createElement('script');
script.src = baseUrl + 'static/js/jquery.1.8.3.min.js';
script.type = 'text/javascript';
head.appendChild(script);

var requestUrl = window.location.href;
var linkUrl = window.location.protocol + "//" + window.location.host;

var userAgent = navigator.userAgent;
var referrer = document.referrer;
var initflag;
document.ready = function() {
	if(initflag == 1 )
	{
		return;
	}
    initflag = 1;
	var pageUrl = 'index.jsp';
	$.ajax({
		type: "get",
		async: false,
		url: baseUrl + 'portal/isToolbarAvalible',
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
					var language=result.body.language;
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