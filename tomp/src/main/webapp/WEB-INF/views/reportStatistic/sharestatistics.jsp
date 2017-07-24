<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Title</title>
<%@ include file="/WEB-INF/include.jsp"%>
<link rel="stylesheet"
	href="<%=path%>/bootstrap-3.3.5-dist/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=path%>/css/base.css">
<link rel="stylesheet" href="<%=path%>/css/base2.css">
<script src="<%=path%>/js/jquery.1.8.3.min.js"></script>
<script src="<%=path%>/js/base.js"></script>
</head>
<body class="nav_cont_set" style="background-color: #f3f7f8;">
	<script src="<%=path%>/js/echarts-all.js"></script>
	<!--<script src="<%=path%>/js/pie.js"></script>-->
	<input type="text" id="htmlIdentifier" value="whiteListNumInterval"
		style="display: none;" />
	<div class="table_search nav statement">
		<div class="table_search2">
			<form id="conditionForm">
				<ul class="clearfixa">
					<li>From <input class="num Wdate" name="beginDate"
						id="beginDate" type="text"
						onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')||\'%y-%M-%d\'}',readOnly:true,lang:'en'})">
					</li>
					<li>to <input class="num Wdate" name="endDate" id="endDate"
						type="text"
						onfocus="WdatePicker({minDate:'#F{$dp.$D(\'beginDate\')}',maxDate:'%y-%M-%d',readOnly:true,lang:'en'})">
					</li>
					<li><buttton class="btn_ov mid_btn search" id="research">Research</buttton>
					</li>
				</ul>
			</form>
			<!--         <div class="row2 clearfixa"> -->
			<!--             <ul> -->
			<!--                 <li><i class="bgcolor" style="background-color: #A4C639;"></i>Android</li> -->
			<!--                 <li><i class="bgcolor" style="background-color: #c0c1c5;"></i>IOS</li> -->
			<!--                 <li><i class="bgcolor" style="background-color: #007cc2;"></i>Windows</li> -->
			<!--                 <li><i class="bgcolor" style="background-color: #85bfce;"></i>other</li> -->
			<!--             </ul> -->
			<!--         </div> -->
		</div>
	</div>
	<div class="table statement clearfixa" style="position: relative;">
		<div class="pie_statement active" id="pie"
			style="height: 300px; width: 33%;"></div>
		<div class="pie_statement" id="pie2"
			style="height: 300px; width: 33%;"></div>
		<div class="pie_statement" id="pie3"
			style="height: 300px; width: 33%;"></div>
		<div class="line_statement" id="line"
			style="height: 300px; width: 100%;"></div>
	</div>


	<script>
    $(function(){
    function Format(now,mask)
    {
        var d = now;
        var zeroize = function (value, length)
        {
            if (!length) length = 2;
            value = String(value);
            for (var i = 0, zeros = ''; i < (length - value.length); i++)
            {
                zeros += '0';
            }
            return zeros + value;
        };
     
        return mask.replace(/"[^"]*"|'[^']*'|\b(?:d{1,4}|m{1,4}|yy(?:yy)?|([hHMstT])\1?|[lLZ])\b/g, function ($0)
        {
            switch ($0)
            {
                case 'd': return d.getDate();
                case 'dd': return zeroize(d.getDate());
                case 'ddd': return ['Sun', 'Mon', 'Tue', 'Wed', 'Thr', 'Fri', 'Sat'][d.getDay()];
                case 'dddd': return ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'][d.getDay()];
                case 'M': return d.getMonth() + 1;
                case 'MM': return zeroize(d.getMonth() + 1);
                case 'MMM': return ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'][d.getMonth()];
                case 'MMMM': return ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'][d.getMonth()];
                case 'yy': return String(d.getFullYear()).substr(2);
                case 'yyyy': return d.getFullYear();
                case 'h': return d.getHours() % 12 || 12;
                case 'hh': return zeroize(d.getHours() % 12 || 12);
                case 'H': return d.getHours();
                case 'HH': return zeroize(d.getHours());
                case 'm': return d.getMinutes();
                case 'mm': return zeroize(d.getMinutes());
                case 's': return d.getSeconds();
                case 'ss': return zeroize(d.getSeconds());
                case 'l': return zeroize(d.getMilliseconds(), 3);
                case 'L': var m = d.getMilliseconds();
                    if (m > 99) m = Math.round(m / 10);
                    return zeroize(m);
                case 'tt': return d.getHours() < 12 ? 'am' : 'pm';
                case 'TT': return d.getHours() < 12 ? 'AM' : 'PM';
                case 'Z': return d.toUTCString().match(/[A-Z]+$/);
                // Return quoted strings with the surrounding quotes removed
                default: return $0.substr(1, $0.length - 2);
            }
        });
    }
    $("#beginDate").val(Format(new Date(),"yyyy-MM-dd"));
    $("#endDate").val(Format(new Date(),"yyyy-MM-dd"));
    });
	
		$(function() {
			var myChart1 = echarts.init(document.getElementById('pie'));
			var myChart2 = echarts.init(document.getElementById('pie2'));
			var myChart3 = echarts.init(document.getElementById('pie3'));
			var myChart4 = echarts.init(document.getElementById('line'));

			$(".pie_statement").on("click", function() {
				$(".pie_statement").removeClass("active");
				$(this).addClass("active");
				var flag = $(this).attr("id");
				loadData4(flag);
			});

			var categories1 = [ 'Android', 'IOS', 'windows', 'other' ];
			var values1 = [ {
				value : 335,
				name : 'Android',
				itemStyle : {
					normal : {
						color : "#A4C639"
					}
				}
			}, {
				value : 310,
				name : 'IOS',
				itemStyle : {
					normal : {
						color : "#c0c1c5"
					}
				}
			}, {
				value : 234,
				name : 'windows',
				itemStyle : {
					normal : {
						color : "#007cc2"
					}
				}
			}, {
				value : 135,
				name : 'other',
				itemStyle : {
					normal : {
						color : "#85bfce"
					}
				}
			} ];
			var categories2 = [ 'Android', 'IOS', 'windows', 'other' ];
			var values2 = [ {
				value : 335,
				name : 'Android',
				itemStyle : {
					normal : {
						color : "#A4C639"
					}
				}
			}, {
				value : 310,
				name : 'IOS',
				itemStyle : {
					normal : {
						color : "#c0c1c5"
					}
				}
			}, {
				value : 234,
				name : 'windows',
				itemStyle : {
					normal : {
						color : "#007cc2"
					}
				}
			}, {
				value : 135,
				name : 'other',
				itemStyle : {
					normal : {
						color : "#85bfce"
					}
				}
			} ];
			var categories3 = [ 'Android', 'IOS', 'windows', 'other' ];
			var values3 = [ {
				value : 335,
				name : 'Android',
				itemStyle : {
					normal : {
						color : "#A4C639"
					}
				}
			}, {
				value : 310,
				name : 'IOS',
				itemStyle : {
					normal : {
						color : "#c0c1c5"
					}
				}
			}, {
				value : 234,
				name : 'windows',
				itemStyle : {
					normal : {
						color : "#007cc2"
					}
				}
			}, {
				value : 135,
				name : 'other',
				itemStyle : {
					normal : {
						color : "#85bfce"
					}
				}
			} ];
			var categories4 = [ '2017.1.23', '2017.1.24', '2017.1.25',
					'2017.1.26', '2017.1.27', '2017.1.28', '2017.1.29' ];
			var values4 = [ {
				name : 'Android',
				type : 'line',
				data : [ 50, 60, 55, 51, 49, 60, 62 ],
				itemStyle : {
					normal : {
						color : "#A4C639"
					}
				},
				markPoint : {
					data : [ {
						type : 'max',
						name : '最大值'
					}, {
						type : 'min',
						name : '最小值'
					} ],
					itemStyle : {
						normal : {
							color : "#A4C639"
						}
					}
				}
			}, {
				name : 'IOS',
				type : 'line',
				data : [ 32, 33, 40, 35, 42, 32, 28 ],
				itemStyle : {
					normal : {
						color : "#c0c1c5"
					}
				},
				markPoint : {
					data : [ {
						type : 'max',
						name : '最大值'
					}, {
						type : 'min',
						name : '最小值'
					} ],
					itemStyle : {
						normal : {
							color : "#c0c1c5"
						}
					}
				}
			}, {
				name : 'Windows',
				type : 'line',
				data : [ 3, 4, 10, 13, 5, 8, 21 ],
				itemStyle : {
					normal : {
						color : "#007cc2"
					}
				},
				markPoint : {
					data : [ {
						type : 'max',
						name : '最大值'
					}, {
						type : 'min',
						name : '最小值'
					} ],
					itemStyle : {
						normal : {
							color : "#007cc2"
						}
					}
				}
			}, {
				name : 'Other',
				type : 'line',
				data : [ 0, 10, 15, 20, 5, 13, 35 ],
				itemStyle : {
					normal : {
						color : "#85bfce"
					}
				},
				markPoint : {
					data : [ {
						type : 'max',
						name : '最大值'
					}, {
						type : 'min',
						name : '最小值'
					} ],
					itemStyle : {
						normal : {
							color : "#85bfce"
						}
					}
				}
			} ];

			option1 = {
				title : {
					text : '手机终端',
					subtext : '',
					x : 'center'
				},
				tooltip : {
					trigger : 'item',
					formatter : "{a} <br/>{b} : {c} ({d}%)"
				},
				legend : {
					orient : 'vertical',
					x : 'left',
					data : categories1
				},
				toolbox : {
					show : true,
					feature : {
						mark : {
							show : false
						},
						dataView : {
							show : false,
							readOnly : true
						},
						magicType : {
							show : false,
							type : [ 'pie', 'funnel' ],
							option : {
								funnel : {
									x : '25%',
									width : '40%',
									funnelAlign : 'left',
									max : 1548
								}
							}
						},
						restore : {
							show : true
						},
						saveAsImage : {
							show : false
						}
					}
				},
				calculable : true,
				series : [ {
					name : '访问来源',
					type : 'pie',
					radius : '55%',
					center : [ '50%', '60%' ],
					data : values1
				} ]
			};
			option2 = {
				title : {
					text : '操作OS',
					subtext : '',
					x : 'center'
				},
				tooltip : {
					trigger : 'item',
					formatter : "{a} <br/>{b} : {c} ({d}%)"
				},
				legend : {
					orient : 'vertical',
					x : 'left',
					data : categories2
				},
				toolbox : {
					show : true,
					feature : {
						mark : {
							show : false
						},
						dataView : {
							show : false,
							readOnly : true
						},
						magicType : {
							show : false,
							type : [ 'pie', 'funnel' ],
							option : {
								funnel : {
									x : '25%',
									width : '40%',
									funnelAlign : 'left',
									max : 1548
								}
							}
						},
						restore : {
							show : true
						},
						saveAsImage : {
							show : false
						}
					}
				},
				calculable : true,
				series : [ {
					name : '访问来源',
					type : 'pie',
					radius : '55%',
					center : [ '50%', '60%' ],
					data : values2
				} ]
			};
			option3 = {
				title : {
					text : '浏览器',
					subtext : '',
					x : 'center'
				},
				tooltip : {
					trigger : 'item',
					formatter : "{a} <br/>{b} : {c} ({d}%)"
				},
				legend : {
					orient : 'vertical',
					x : 'left',
					data : categories3
				},
				toolbox : {
					show : true,
					feature : {
						mark : {
							show : false
						},
						dataView : {
							show : false,
							readOnly : true
						},
						magicType : {
							show : false,
							type : [ 'pie', 'funnel' ],
							option : {
								funnel : {
									x : '25%',
									width : '40%',
									funnelAlign : 'left',
									max : 1548
								}
							}
						},
						restore : {
							show : true
						},
						saveAsImage : {
							show : false
						}
					}
				},
				calculable : true,
				series : [ {
					name : '访问来源',
					type : 'pie',
					radius : '55%',
					center : [ '50%', '60%' ],
					data : values3
				} ]
			};
			option4 = {
				title : {
					text : '手机终端',
					subtext : ''
				},
				tooltip : {
					trigger : 'axis'
				},
				toolbox : {
					show : true,
					feature : {
						mark : {
							show : false
						},
						dataView : {
							show : true,
							readOnly : true
						},
						magicType : {
							show : false,
							type : [ 'line', 'bar' ]
						},
						restore : {
							show : true
						},
						saveAsImage : {
							show : true
						}
					}
				},
				calculable : true,
				xAxis : [ {
					type : 'category',
					boundaryGap : false,
					data : categories4
				} ],
				yAxis : [ {
					type : 'value',
					axisLabel : {
						formatter : '{value} %'
					}
				} ],
				series : values4
			};

			myChart1.setOption(option1);
			myChart2.setOption(option2);
			myChart3.setOption(option3);
			myChart4.setOption(option4);

			function loadData1() {
				var start = $("#beginDate").val();
				var end = $("#endDate").val();
				if ('' == start) {
					alert("begin time can not be empty!");
					return false;
				}
				if ('' == end) {
					alert("end time can not be empty!");
					return false;
				}
				$.ajax({
					type : "POST",
					cache : false,
					url : "${ctx}/sharestatisticsController/loadData1",
					data : $("#conditionForm").serialize(),
					dataType : "json",
					success : function(data) {
						categories1 = data.categories;//eval( "(" + data.categories + ")" );
						values1 = data.values;//eval( "(" + data.values + ")" );
						myChart1.clear();
						myChart1.dispose();
						myChart1 = echarts.init(document.getElementById('pie'));
						myChart1.setOption({
							title : {
								text : '手机终端',
								subtext : '',
								x : 'center'
							},
							tooltip : {
								trigger : 'item',
								formatter : "{a} <br/>{b} : {c} ({d}%)"
							},
							legend : {
								orient : 'vertical',
								x : 'left',
								data : categories1
							},
							toolbox : {
								show : true,
								feature : {
									mark : {
										show : false
									},
									dataView : {
										show : false,
										readOnly : true
									},
									magicType : {
										show : false,
										type : [ 'pie', 'funnel' ],
										option : {
											funnel : {
												x : '25%',
												width : '40%',
												funnelAlign : 'left',
												max : 1548
											}
										}
									},
									restore : {
										show : true
									},
									saveAsImage : {
										show : false
									}
								}
							},
							calculable : true,
							series : [ {
								name : '访问来源',
								type : 'pie',
								radius : '55%',
								center : [ '50%', '60%' ],
								data : values1
							} ]
						});
					}
				});
			}
			function loadData2() {
				var start = $("#beginDate").val();
				var end = $("#endDate").val();
				if ('' == start) {
					alert("begin time can not be empty!");
					return false;
				}
				if ('' == end) {
					alert("end time can not be empty!");
					return false;
				}
				$.ajax({
					type : "POST",
					cache : false,
					url : "${ctx}/sharestatisticsController/loadData2",
					data : $("#conditionForm").serialize(),
					dataType : "json",
					success : function(data) {
						categories2 = data.categories;//eval( "(" + data.categories + ")" );
						values2 = data.values;//eval( "(" + data.values + ")" );
						myChart2.clear();
						myChart2.dispose();
						myChart2 = echarts.init(document.getElementById('pie2'));
						myChart2.setOption({
							title : {
								text : '操作OS',
								subtext : '',
								x : 'center'
							},
							tooltip : {
								trigger : 'item',
								formatter : "{a} <br/>{b} : {c} ({d}%)"
							},
							legend : {
								orient : 'vertical',
								x : 'left',
								data : categories2
							},
							toolbox : {
								show : true,
								feature : {
									mark : {
										show : false
									},
									dataView : {
										show : false,
										readOnly : true
									},
									magicType : {
										show : false,
										type : [ 'pie', 'funnel' ],
										option : {
											funnel : {
												x : '25%',
												width : '40%',
												funnelAlign : 'left',
												max : 1548
											}
										}
									},
									restore : {
										show : true
									},
									saveAsImage : {
										show : false
									}
								}
							},
							calculable : true,
							series : [ {
								name : '访问来源',
								type : 'pie',
								radius : '55%',
								center : [ '50%', '60%' ],
								data : values2
							} ]
						});
					}
				});
			}
			function loadData3() {
				var start = $("#beginDate").val();
				var end = $("#endDate").val();
				if ('' == start) {
					alert("begin time can not be empty!");
					return false;
				}
				if ('' == end) {
					alert("end time can not be empty!");
					return false;
				}
				$.ajax({
					type : "POST",
					cache : false,
					url : "${ctx}/sharestatisticsController/loadData3",
					data : $("#conditionForm").serialize(),
					dataType : "json",
					success : function(data) {
						categories3 = data.categories;//eval( "(" + data.categories + ")" );
						values3 = data.values;//eval( "(" + data.values + ")" );
						myChart3.clear();
						myChart3.dispose();
						myChart3 = echarts.init(document.getElementById('pie3'));
						myChart3.setOption({
							title : {
								text : '浏览器',
								subtext : '',
								x : 'center'
							},
							tooltip : {
								trigger : 'item',
								formatter : "{a} <br/>{b} : {c} ({d}%)"
							},
							legend : {
								orient : 'vertical',
								x : 'left',
								data : categories3
							},
							toolbox : {
								show : true,
								feature : {
									mark : {
										show : false
									},
									dataView : {
										show : false,
										readOnly : true
									},
									magicType : {
										show : false,
										type : [ 'pie', 'funnel' ],
										option : {
											funnel : {
												x : '25%',
												width : '40%',
												funnelAlign : 'left',
												max : 1548
											}
										}
									},
									restore : {
										show : true
									},
									saveAsImage : {
										show : false
									}
								}
							},
							calculable : true,
							series : [ {
								name : '访问来源',
								type : 'pie',
								radius : '55%',
								center : [ '50%', '60%' ],
								data : values3
							} ]
						});
					}
				});
			}
			function loadData4(flag) {
				var title;
				if ('pie' == flag) {
					title = '手机终端';
				} else if ('pie2' == flag) {
					title = '操作OS';
				} else if ('pie3' == flag) {
					title = '浏览器';
				} else {
					title = '手机终端';
				}
				var start = $("#beginDate").val();
				var end = $("#endDate").val();
				if ('' == start) {
					alert("begin time can not be empty!");
					return false;
				}
				if ('' == end) {
					alert("end time can not be empty!");
					return false;
				}
				$.ajax({
					type : "POST",
					cache : false,
					url : "${ctx}/sharestatisticsController/loadData4?flag="
							+ flag,
					data : $("#conditionForm").serialize(),
					dataType : "json",
					success : function(data) {
						categories4 = data.categories;//eval( "(" + data.categories + ")" );
						values4 = data.values;//eval( "(" + data.values + ")" );
						myChart4.clear();
						myChart4.setOption({
							title : {
								text : title,
								subtext : ''
							},
							tooltip : {
								trigger : 'axis'
							},
							toolbox : {
								show : true,
								feature : {
									mark : {
										show : false
									},
									dataView : {
										show : true,
										readOnly : true
									},
									magicType : {
										show : false,
										type : [ 'line', 'bar' ]
									},
									restore : {
										show : true
									},
									saveAsImage : {
										show : true
									}
								}
							},
							calculable : true,
							xAxis : [ {
								type : 'category',
								boundaryGap : false,
								data : categories4
							} ],
							yAxis : [ {
								type : 'value',
								axisLabel : {
									formatter : '{value} %'
								}
							} ],
							series : values4
						});
					}
				});
			}

			$("#research").on("click", function() {
				var start = $("#beginDate").val();
				var end = $("#endDate").val();
				if ('' == start) {
					alert("begin time can not be empty!");
					return false;
				}
				if ('' == end) {
					alert("end time can not be empty!");
					return false;
				}
				loadData1();
				loadData2();
				loadData3();
			});
			loadData1();
			loadData2();
		    loadData3();
			loadData4();
		});
	</script>
</body>
</html>