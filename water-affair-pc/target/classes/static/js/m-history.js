$(function(){
	var idType = location.search.split("=")[1] || "";
	$(".header-para").on("tap",function(){
		window.location.href = "/detail"+location.search;
	});
	$(".nav-list").on("tap",function(){
		var _type = $(this).attr("data-type");
		var _num=$("#num").val();
		if($(this).hasClass("active")) return false;
		$(this).addClass("active").siblings().removeClass("active");
		$(".history-"+_type).fadeIn().siblings().hide();
		$(".lists-history-"+_type).fadeIn().siblings().hide();
	});
	$(".timer-type").on("tap",function(){
		$(this).hasClass("active") ? $(this).removeClass("active") : $(this).addClass("active");
	});
	/**
     * 监测概况折线图
     */
    var xAxisdata = [];
    //判断时间是否是今天
    function isTody(){
    	var _currtime = $("#sheet-date").html().split("-");
    	var _tody = [];
    	_tody.push((new Date).getFullYear());
    	_tody.push((new Date).getMonth()+1);
    	_tody.push((new Date).getDate());
    	for(var i = 0; i<_tody.length;i++){
    		if (_tody[i] != parseInt(_currtime[i])) {
    			return false;
    		}
    	}
    	return true;
    }
    // 时间设置 每隔五分钟
    var nullarr = [],daraarr = [];
    //nullarr为横坐标 这里全部设置为空
    function dateT(count,date,callback){
    	var hour = 0, minute = 0;
    	for(var i = 0; i < count; i++){
    		//从 00：00开始的数据
    		if (minute == 60) {
    			hour++;
    			minute = 0;
    		}
    		// (minute == 60) ? (hour++ && minute = 0) : '';
    		xAxisdata.push(date+" "+(hour < 10 ? ("0"+hour) : hour)+ ":" + (minute < 10 ? ("0"+minute) : minute));
    		//伪造的时间表 过多设为空  实际得通过ajax调取
    		// nullarr.push('');
    		//伪造的数据  实际得通过ajax调取
    		// daraarr.push(minute);
    		minute += 5;
    	}
    	// console.log(daraarr);
    	callback&&callback(xAxisdata);
    	// console.log(xAxisdata);
    }

    //ajax 获取数据的通道
    function ajaxDate(timer,type,callback,num){
    	var mydata = {
    		timer: timer || '',
    		type: type || '',
    		num: num || ''
    	}
	$.ajax({
        type:'POST',
        url:"/queryMeterFlowByMinute2",
        data:mydata,
        dataType:"json",
        success: function(msg){
        	//格式化数据，将msg格式化成数组
        	/*
        	var responseData=[];
        	for(var o in msg){
        		if(typeof(msg[o][type]) == "undefined"){
            		responseData.push('无');
        		}else{
        			responseData.push(msg[o][type]);
        		}
        	}
        	*/
            console.log(msg);


            callback && callback(msg);

        }
    })
    //本地测试通过
     // $.getJSON("/xiaopang/witwit/doc/text.json",function(data){
     // 	callback && callback(data);
     // })
    }

    //html template
    function htmlTemplate(date,arr,callback){
    	var html = '';
    	$(".lists-history-data").html("")
    	for(var i = 0; i<arr.length;i++){
    		html = '<li class="content-list"><span class="content-list-t">'+date[i]+'</span><span class="content-list-t">'+arr[i]+'</span></li>';
    		//历史报表数据 默认为当天的流量 type =  flux  这里写死的2.4  可以通过$.ajax()请求成功之后再进行渲染
    		$(".lists-history-data").append(html);
    	}
    	callback&&callback();
    }
    //判断 时间 是否是 今天然后加载数据
    function getAxisdata(callback){
    	var totalMinT;
    	if (isTody()) {
    		var _currt = new Date();
    		var _year = _currt.getFullYear(),_mon = (_currt.getMonth()<10 ? "0"+(_currt.getMonth()+1) : _currt.getMonth()),_day = (_currt.getDate()<10 ? "0"+(_currt.getDate()) : _currt.getDate()),_hour = _currt.getHours(),_min = _currt.getMinutes();
    		// console.log(_hour)
    		// console.log(_year+_mon+_day+_hour+_min);
    		//计算时间 5分钟一次
    		//总的分钟数次数
    		totalMinT = parseInt((_hour*60 + _min)/5);
    		callback&&callback(totalMinT,_mon+"-"+_day,$(".para-list.active").attr("data-type"),$("#num").val());
    		// dateT(totalMinT,_mon+"-"+_day,function(data,callback){
    		// 	htmlTemplate(data,callback);
    		// });
    		// console.log(totalMin,_mon+"-"+_day);
    	}else {
    		var _currtime = $("#sheet-date").html().split("-");
    		totalMinT = parseInt(24*60/5);
    		callback&&callback(totalMinT,_currtime[1]+"-"+_currtime[2],$(".para-list.active").attr("data-type"),$("#num").val());
    		// dateT(totalMinT,_currtime[1]+"-"+_currtime[2],function(data,callback){
    		// 	htmlTemplate(data,callback);
    		// });
    	}

    	// callback&&callback(totalMinT,_mon+"-"+_day,$(".para-list.active").attr("data-type"));
    }
    getAxisdata(function(count,date,type,num){
    	//判断加载时间价格次数 类型之后 开始获取时间列表 和数据列表
    	var _type = type || '';
    	var _num = num || '';
    	 dateT(count,date,function(datalist){
    	 	ajaxDate($("#sheet-date").html(),_type,function(data){
    	 		htmlTemplate(datalist,data);//渲染数据
    	 		//渲染图表
    	 		historySheet(data);
    	 	},_num)
    	 })
    });//历史报表数据

    //加载页面时候 加载一次表格 这个是通过首页点击进来时候 我们这里默认选择一个参数  后面可以自己切换参数
	function lineImg(_data,callback){
		var data = {
		    title: {
		        text: '',
		        subtext: ''
		    },
		    tooltip: {
		        trigger: 'axis'
		    },
		    legend: {
		        //data:_data.legendata
		    },
		    toolbox: {
		        show: false,
		        feature: {
		            dataZoom: {
		                yAxisIndex: 'none'
		            },
		            dataView: {readOnly: false},
		            magicType: {type: ['line', 'bar']},
		            restore: {},
		            saveAsImage: {}
		        }
		    },
		    xAxis:  {
		        type: 'time',
		        splitNumber:2,
		        axisLabel:{
		            formatter: function (value, index) {
                        // 格式化成月/日，只在第一个刻度显示年份
                        var date = new Date(value);

                        var hour,minute;
                        if(date.getHours()<10){
                            hour='0'+date.getHours();
                        }else{
                            hour=date.getHours();
                        }

                        if(date.getMinutes()<10){
                            minute='0'+date.getMinutes();
                        }else{
                            minute=date.getMinutes();
                        }

                        var texts = [hour,minute];
                        return texts.join(':');
                    }
		        },
		        boundaryGap: false
		    },
		    yAxis: {
		        type: 'value',
		        axisLabel: {
		            formatter: '{value}'
		        }
		    },
		    series: [
		        {
		            //name:_data.maxname,
		            type:'line',
		            data:_data,
		            markPoint: {
		                data: [
		                    {type: 'max', name: '最大值'}
		                ]
		            },
		            markLine: {
		                data: [
		                    {type: 'average', name: '平均值'}
		                ]
		            }
		        }
		    ]
	        };
	    callback&&callback(data);
    }

    //渲染 图标 函数
    function historySheet(daraarr){
    	var myChart = echarts.init(document.getElementById("main"));
		var chartData = echarts.init(main);
		var app = {};
		//请求的数据 _data
		var typeName=$("#typeName").html();//瞬时流量、正向、反向、净流量，压力之类
		var _data = {
	    	legendata: [typeName,'***'],
	    	xAxisdata: ['00:00','24:00'],
	    	maxname: typeName,
	    	maxdata: daraarr,//这里的数据是伪造的  这里的取数据

	    };
		lineImg(daraarr,function(option){
			chartData.setOption(option,true);
		});
    }
    //请求数据

    //点击上一天 和下一天时候 刷新数据

    $(".select-date-l").on("tap",function(){
		var _type = $(this).attr("data-type");
		var _curTime = $("#sheet-date").html().split("-");
		var _mon = _curTime[1],_day = _curTime[2],_year = _curTime[0];
		if (_type == "pre") {
			_mon = _curTime[1],_day = (parseInt(_curTime[2])-1) < 10 ? "0"+(parseInt(_curTime[2]) - 1) : (parseInt(_curTime[2]) - 1);
			// console.log(_day);
			if (_day == 0) {
				_mon = (parseInt(_curTime[1]) - 1) < 10 ? "0"+(parseInt(_curTime[1]) - 1) : (parseInt(_curTime[1]) - 1);
				if (parseInt(_mon) == 0) {
					_year = parseInt(_curTime[0])-1;
					_mon = 12;
				}
				// console.log(_mon);
				if (/^[01|03|05|07|08|10|12]/.test(_mon)) {
					_day = 31;
				}else {
					_day = 30;
				}
				// console.log(_day);
			}
			$("#sheet-date").html(_year+"-"+_mon+"-"+_day);
		}else if (_type == "next") {
			console.log(isTody());
			if(isTody()) return false;
			_mon = _curTime[1],_day = (parseInt(_curTime[2])+1) < 10 ? "0"+(parseInt(_curTime[2]) + 1) : (parseInt(_curTime[2]) + 1);
			console.log(_day);
			if (_day == 32) {
				_day = "01";
				_mon = (parseInt(_curTime[1]) + 1) < 10 ? "0"+(parseInt(_curTime[1]) + 1) : (parseInt(_curTime[1]) + 1);
				if (parseInt(_mon) == 13) {
					_mon = "01";
					_year = parseInt(_curTime[0])+1;
				}
				console.log(_mon);
			}
			$("#sheet-date").html(_year+"-"+_mon+"-"+_day);
		}
		//重置数据
		// nullarr = [];
		// daraarr = [];
		$("#main").remove();
		$(".content-sheet").append('<div id="main" style="width: 100%;height:300px;"></div>');
		 getAxisdata(function(count,date,type,num){
	    	//判断加载时间价格次数 类型之后 开始获取时间列表 和数据列表
	    	var _type = type || '';
	    	var _num = num || '';
	    	 dateT(count,date,function(datalist){
	    	 	ajaxDate($("#sheet-date").html(),_type,function(data){
	    	 		htmlTemplate(datalist,data);//渲染数据
	    	 		//渲染图表
	    	 		historySheet(data);
	    	 	},_num)
	    	 })
	    });
		// getAxisdata(function(){
		// 	console.log('www');
		// });
		// $("#main").remove();
		// $(".content-sheet").append('<div id="main" style="width: 100%;height:300px;"></div>');

		// var myChart = echarts.init(document.getElementById("main"));
		// var chartData = echarts.init(main);
		// var app = {};
		// //请求的数据 _data
		// var _data = {
	 //    	legendata: ['瞬时流量','***'],
	 //    	xAxisdata: nullarr,
	 //    	maxname: "瞬时流量",
	 //    	maxdata: daraarr,
	 //    	minname: "***",
	 //    	mindata: [1, 2, 2, 5, 3, 2, 0,1, 2, 2, 5, 3]
	 //    };
		// lineImg(_data,function(option){
		// 	chartData.setOption(option,true);
		// });
	})

	//点击参数 重置表格
	// var _oncep = true;
	$(".fixed-nav-list").on("tap",function(){
		var _type = $(this).attr("data-type");
		if($(this).hasClass("active") && _type != "para") return false;
		$(this).addClass("active").siblings().removeClass("active");
		switch (_type) {
			case "para":
				var _show = $(this).attr("data-show");
				_show == "false" ? $(this).attr("data-show","true").children(".para-lists").fadeIn() : $(this).attr("data-show","false").children(".para-lists").hide();
				break;
			case "current":
				$(this).siblings().attr("data-show","false").children(".para-lists").hide();
				break;
			default:
				// statements_def
				break;
		}
	})

	$(".para-list").on("tap",function(){
		var _type = $(this).attr("data-type");
		$(this).parents(".fixed-nav-list").attr("data-show","false");
		$(this).parent().hide();
		if ($(this).hasClass("active")) return false;
		// console.log(_type);
		if ($(".nav-list.active").attr("data-type") == "data") return false;
		//重置数据
		// nullarr = [];
		// daraarr = [];
		$("#main").remove();
		$(".content-sheet").append('<div id="main" style="width: 100%;height:300px;"></div>');
		 getAxisdata(function(count,date,type,num){
    	//判断加载时间价格次数 类型之后 开始获取时间列表 和数据列表
    	var _type = type || '';
    	var _num = num || '';
    	 dateT(count,date,function(datalist){
    	 	ajaxDate($("#sheet-date").html(),_type,function(data){
    	 		htmlTemplate(datalist,data);//渲染数据
    	 		//渲染图表
    	 		historySheet(data);
    	 	},_num)
    	 })
    });
		return false;
	});
    $(".main").on("tap",function(e){
		if ($(this).hasClass("fixed-nav-list") || $(this).hasClass("para-list")) return false;
		$(".fixed-nav-list").attr("data-show") == "true" ? $(".fixed-nav-list").attr("data-show","false").children(".para-lists").hide() : "";
		e.preventDefault();
		return false;
	});
})