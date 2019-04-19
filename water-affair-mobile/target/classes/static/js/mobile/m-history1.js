$(function(){

    var tempTypeName='';
    var testType='';

	var id = location.search.split("&")[0] || "";
    var type = location.search.split("&")[1] || "";
    testType=type.split("=")[1];
    var meterid=id.split("=")[1];


	console.log(id+'-------'+$(".para-list").attr("data-type"));
	console.log(type+'-------');

    //详情
	$(".header-para").on("tap",function(){
		window.location.href = "/detail?id="+meterid+"&&type="+testType+"&&date="+$("#sheet-date").html();
	});

	$(".nav-list").on("tap",function(){
		var _type = $(this).attr("data-type");
		var _num=$("#num").val();
		if($(this).hasClass("active")) return false;
		$(this).addClass("active").siblings().removeClass("active");
		$(".history-"+_type).fadeIn().siblings().hide();
//		$(".lists-history-"+_type).fadeIn().siblings().hide();//1月15号
		$(".lists-history-"+_type).removeClass("history-left-p").siblings().addClass("history-left-p");
		// _type == "sheet" ? $("canvas").css({"display":"block","width":"375px"}) : '';
	});
	$(".main").on("tap",function(e){
		if ($(this).hasClass("fixed-nav-list") || $(this).hasClass("para-list")) return false;
		$(".fixed-nav-list").attr("data-show") == "true" ? $(".fixed-nav-list").attr("data-show","false").children(".para-lists").hide() : "";
		e.preventDefault();
		return false;
	});
	$(".timer-type").on("tap",function(){
		$(this).hasClass("active") ? $(this).removeClass("active") : $(this).addClass("active");
	});

	function getTodyDate(){
		var _tody = new Date();
		return _tody.getFullYear()+"-"+((_tody.getMonth()+1) < 10 ? "0"+(_tody.getMonth()+1) : (_tody.getMonth()+1))+"-"+(_tody.getDate() < 10 ? "0"+_tody.getDate() : _tody.getDate());
	}

	//$("#sheet-date").html(getTodyDate());
	//$("#sheet-date1").html(getTodyDate());
	// console.log(getTodyDate());
	// $(".select-next").attr("data-show","loading").css("background-color","#ccc");
	if (isTody()) {
	    bgColor($(".select-next"),true);
	}

	/**
     * 监测概况折线图
     */
    var xAxisdata = [];
    //判断时间是否是今天
    function isTody(){
        //console.log('------------'+$("#sheet-date").html());
        //console.log('------------'+getTodyDate());

        var d1=$("#sheet-date").html();
        var d2=getTodyDate();

        if(d1==d2){
            return true;
        }else{
            return false;
        }

    	/*var _currtime = $("#sheet-date").html().split("-");
    	var _tody = [];
    	_tody.push((new Date).getFullYear());
    	_tody.push();
    	_tody.push((new Date).getDate());
    	for(var i = 0; i<_tody.length;i++){
    		if (_tody[i] != parseInt(_currtime[i])) {
    			return false;
    		}
    	}

    	return true;*/
    }
    // 时间设置 每隔五分钟
    var nullarr = [],daraarr = [],lineX = [];
    //nullarr为横坐标 这里全部设置为空
    function dateT(count,date,callback){
    	var hour = 0, minute = 0;
    	console.log(count);
    	xAxisdata = [];
    	lineX = [];
    	for(var i = 0; i <= count; i++){
    		//从 00：00开始的数据
    		if (minute == 60) {
    			hour++;
    			minute = 0;
    		}
    		// (minute == 60) ? (hour++ && minute = 0) : '';
    		xAxisdata.push(date+" "+(hour < 10 ? ("0"+hour) : hour)+ ":" + (minute < 10 ? ("0"+minute) : minute));
    		i == 0 ? lineX.push((hour < 10 ? ("0"+hour) : hour)+ ":" + (minute < 10 ? ("0"+minute) : minute)) : (i == count ? lineX.push((hour < 10 ? ("0"+hour) : hour)+ ":" + (minute < 10 ? ("0"+minute) : minute)) : lineX.push(''));
    		//伪造的时间表 过多设为空  实际得通过ajax调取
    		// nullarr.push('');
    		//伪造的数据  实际得通过ajax调取
    		// daraarr.push(minute);
    		minute += 5;
    	}
    	// console.log(daraarr);
    	//console.log(lineX);
    	callback&&callback(xAxisdata);
    	// console.log(xAxisdata);
    }

    //ajax 获取图表的数据的通道
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
            //console.log(msg);
            callback && callback(msg);
        }
    })

    }

    //ajax 获取历史报表数据的通道
        function ajaxDate2(timer,type,callback,num){
        	var mydata = {
        		timer: timer || '',
        		type: type || '',
        		num: num || ''
        	}
    	$.ajax({
            type:'POST',
            url:"/queryMeterFlowByMinute3",
            data:mydata,
            dataType:"json",
            success: function(msg){
            	//格式化数据，将msg格式化成数组
                //console.log(msg);
                callback && callback(msg);
            }
        })

        }
        function ajaxDate3(timer,type,callback,num){
            var mydata = {
                    timer: timer || '',
                    type: type || '',
                    num: num || ''
                }
            $.ajax({
                        type:'POST',
                        url:"/queryMeterFlowByMinute4",
                        data:mydata,
                        dataType:"json",
                        success: function(msg){
                        	//格式化数据，将msg格式化成数组
                            //console.log(msg);
                            callback && callback(msg);
                        }
                    })
        }
         function ajaxDate4(timer,type,callback,num){
            var mydata = {
                    timer: timer || '',
                    type: type || '',
                    num: num || ''
                }
            $.ajax({
                        type:'POST',
                        url:"/queryMeterFlowByMinute5",
                        data:mydata,
                        dataType:"json",
                        success: function(msg){
                            //格式化数据，将msg格式化成数组
                            //console.log(msg);
                            callback && callback(msg);
                        }
                    })
        }
    //html template  历史报表
    function htmlTemplate(date,arr,callback){
    	var html = '';
        var id=$("#num").val();
        var type=$(".para-list.active").attr("data-type");
    	
    	$(".lists-history-data").html("")
    	console.log("--------"+arr.length);
        var interval = 1;//默认间隔1小时
    	if(arr.length > 1){
            var date1 = new Date(arr[0][0]);
            var date2 = new Date(arr[1][0]);
            interval = date2.getHours() - date1.getHours();
            interval < 1 ? interval = 1 : interval;
		}
    	for(var i = 0; i<arr.length;i++){
    		var point = arr[i];
            try{
                var date = new Date(point[0]);

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
               	var _url = "/hishour?id=" + id +"&type="+type+"&time="+date.getHours()+"&date="+$("#sheet-date").html()+"&interval="+interval;
               	console.log(_url);

                html = '<li class="content-list" data-url="'+_url+'"><span class="content-list-t">'+hour+":"+minute+'</span><span class="content-list-t">'+point[1]+'</span></li>';
                //历史报表数据 默认为当天的流量 type =  flux  这里写死的2.4  可以通过$.ajax()请求成功之后再进行渲染
                $(".lists-history-data").append(html);
    		}catch(err){
    		}
    	}
    	callback&&callback();
    	// 每次渲染模板 都要重新绑定点击事件
		$(".content-list").on("tap", function() {
			var _url = $(this).attr("data-url");
			// 在这里跳转的时候 后期需要根据id值进行跳转
			window.location.href = _url;
		})
    }
    //判断 时间 是否是 今天然后加载数据
    function getAxisdata(callback){
    	var totalMinT;
    	if (isTody()) {
    		var _currt = new Date();
    		var _year = _currt.getFullYear(),_mon = (_currt.getMonth()<10 ? "0"+(_currt.getMonth()+1) : _currt.getMonth()),_day = (_currt.getDate()<10 ? "0"+(_currt.getDate()) : _currt.getDate()),_hour = _currt.getHours(),_min = _currt.getMinutes();
    		
    		//计算时间 5分钟一次
    		//总的分钟数次数
    		totalMinT = parseInt((_hour*60 + _min)/5);
    		callback&&callback(totalMinT,_mon+"-"+_day,$(".para-list.active").attr("data-type"),$("#num").val());

    	}else {
    		var _currtime = $("#sheet-date").html().split("-");
    		totalMinT = parseInt(24*60/5);
    		callback&&callback(totalMinT,_currtime[1]+"-"+_currtime[2],$(".para-list.active").attr("data-type"),$("#num").val());
    		// dateT(totalMinT,_currtime[1]+"-"+_currtime[2],function(data,callback){
    		// 	htmlTemplate(data,callback);
    		// });
    	}
    }
    getAxisdata(function(count,date,type,num){
    	//判断加载时间价格次数 类型之后 开始获取时间列表 和数据列表
    	var _type = type || '';
    	var _num = num || '';

    	 if(_type ==='increaseTotalflow' || _type==='increaseTotalflowMonth'){
    	    dateT(count,date,function(datalist){
                    ajaxDate3($("#sheet-date").html(),_type,function(data){
                        //htmlTemplate(datalist,data);//渲染数据
                        //渲染图表
                        historySheet(data);
                    },_num)

                    ajaxDate4($("#sheet-date").html(),_type,function(data){
                                                        htmlTemplate(datalist,data);//渲染数据
                                                        //渲染图表
                                                        //historySheet(data);
                                                    },_num)
             })
    	 }else{
    	  dateT(count,date,function(datalist){
             	 	ajaxDate($("#sheet-date").html(),_type,function(data){
             	 		//htmlTemplate(datalist,data);//渲染数据
             	 		//渲染图表
             	 		historySheet(data);
             	 	},_num)

             	 	ajaxDate2($("#sheet-date").html(),_type,function(data){
                         	 		htmlTemplate(datalist,data);//渲染数据
                         	 		//渲染图表
                         	 		//historySheet(data);
                         	 	},_num)
             	 })

    	 }

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
		        data:typeName
		        //data:_data.legendata
		    },
		    animation:false,
		    toolbox: {
		        show: true,
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

		    dataZoom: [
               {
                   show: true,
                   realtime: true,
               }
            ],
            grid: { // 控制图的大小，调整下面这些值就可以，
                    x: 66// y2可以控制 X轴跟Zoom控件之间的间隔，避免以为倾斜后造成 label重叠到zoom上
                 },
		    xAxis:  {
		        type: 'time',
		        splitNumber:2,
		        axisLabel:{
		            formatter: function (value, index) {
                        // 格式化成月/日，只在第一个刻度显示年份
		            	//数据库是unix时间戳-》普通时间
                      	var date = new Date(value);
		            	//var date =new Date(value*1000)
						//console.log(date);
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
		        name: tempTypeName,
		        axisLabel: {
		            formatter: '{value}'
		        }
		    },
		    series: [
		        {
		            //name:_data.maxname,
		            type:'line',
		            data:_data,
		            symbolSize:1,
		            markPoint: {
		                symbol:'pin',
		                symbolSize:'50',
		                label:{
		                    normal:{
		                        formatter:function (value,index) {
		                            var maxValue=''+value.data.coord[1];
		                            console.log(maxValue.length);
		                            if(maxValue.length>4){
		                                 console.log('>4');
		                                 maxValue=maxValue.substring(0,4);
		                            }else{
	                                     maxValue;
   		                            }

   		                            console.log(maxValue);
   		                            return maxValue;
		                        }
		                    }
		                },
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

        tempTypeName=$("#typeName").html();

        if(tempTypeName==='瞬时流量'){
            tempTypeName='瞬时流量(m³/h)';
        }else if(tempTypeName==='今日累计'){
            tempTypeName='今日累计(m³)';
        }else if(tempTypeName==='本月累计'){
            tempTypeName='本月累计(m³)';
        }



		var _data = {
	    	legendata: ['111','***'],
	    	xAxisdata: ['00:00','24:00'],
	    	maxname: '111',
	    	maxdata: daraarr,//这里的数据是伪造的  这里的取数据

	    };
		lineImg(daraarr,function(option){
			chartData.setOption(option,true);
		});
    }
    //请求数据

    //点击上一天 和下一天时候 刷新数据
    function isLeapYear(year) {  
	    return (year % 4 == 0) && (year % 100 != 0 || year % 400 == 0); 
	 }
	 function playData(flag){
	 	//flag = true 时候重新渲染数据  否则只是加载数据
	 	var _flag = flag || '';
	 	if (_flag) {
	 		$("#main").remove();
			$(".content-sheet").append('<div id="main" style="width: 100%;height:300px;"></div>');
	 	}
	 	getAxisdata(function(count,date,type,num){//增加  num
	    	//判断加载时间价格次数 类型之后 开始获取时间列表 和数据列表
	    	var _type = type || '';
	    	var _num = num || "";
	    	 dateT(count,date,function(datalist){
//	    	 	console.log(datalist);
	    	 	//datalist 时间列表
	    	 	ajaxDate($("#sheet-date").html(),_type,function(data){
	    	 		data.length = datalist.length;
	    	 		//htmlTemplate(datalist,data);//渲染数据
	    	 		//渲染图表
	    	 		historySheet(data);
	    	 	},_num)

	    	 	ajaxDate2($("#sheet-date").html(),_type,function(data){
                                	 		htmlTemplate(datalist,data);//渲染数据
                                	 		//渲染图表
                                	 		//historySheet(data);
                                	 	},_num)
	    	 })
	    });//历史报表数据
	 }

	 // 显示下一天  按钮的状态
	 function bgColor(obj,type){
	 	if (obj) {
	 		obj.map(function(){
	 			type == true ? $(this).attr("data-show","loading").css("background-color","#ccc") : $(this).attr("data-show","").css("background-color","#3388FF");
	 		})
	 	}
	 }
    $(".select-date-l").on("tap",function(){
		var _type = $(this).attr("data-type"),_show = $(this).attr("data-show");
		if (_show == "loading") return false;
		// $(this).attr("data-show","loading").css("background-color","#ccc");
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
			$("#sheet-date1").html(_year+"-"+_mon+"-"+_day);
		}else if (_type == "next") {
			
			if(isTody()) return false;
			_mon = _curTime[1],_day = (parseInt(_curTime[2])+1) < 10 ? "0"+(parseInt(_curTime[2]) + 1) : (parseInt(_curTime[2]) + 1);
			console.log(_day);
			//判断二月的天数
			if (/^(0?[2])$/.test(_mon)) {
				_day == 29 ? (isLeapYear(parseInt(_year)) == true ? _day= 29 : _day=32) : (_day == 30 ? _day = 32 : '');
			}else{
				//判断 一个月是否是三十一天 
				if (_day == 31) {
					/^(0?[1|3|5|7|9|]|1[0|2])$/.test(_mon) == true ? _day = 31 : _day=32;
				}
			}
			if (_day == 32) {
				_day = "01";
				_mon = (parseInt(_curTime[1]) + 1) < 10 ? "0"+(parseInt(_curTime[1]) + 1) : (parseInt(_curTime[1]) + 1);
				if (parseInt(_mon) == 13) {
					_mon = "01";
					_year = parseInt(_curTime[0])+1;
				}
				console.log(_mon);
			}
			$("#sheet-date1").html(_year+"-"+_mon+"-"+_day);
			$("#sheet-date").html(_year+"-"+_mon+"-"+_day);
		}
		//重置数据
		bgColor($(".select-next"),isTody());
		playData(true);
//		$("#main").remove();
//		$(".content-sheet").append('<div id="main" style="width: 100%;height:300px;"></div>');
//		 getAxisdata(function(count,date,type,num){
//	    	//判断加载时间价格次数 类型之后 开始获取时间列表 和数据列表
//	    	var _type = type || '';
//	    	var _num = num || '';
//	    	 dateT(count,date,function(datalist){
//	    	 	ajaxDate($("#sheet-date").html(),_type,function(data){
//	    	 		htmlTemplate(datalist,data);//渲染数据
//	    	 		//渲染图表
//	    	 		historySheet(data);
//	    	 	},_num)
//	    	 })
//	    });
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
		$(this).addClass("active").siblings().removeClass("active");
		$(".history-list-value").html($(this).html());
		playData(true);
		// if ($(".nav-list.active").attr("data-type") == "data") return false;
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
    	 	    testType=_type;
    	 		//htmlTemplate(datalist,data);//渲染数据
    	 		//渲染图表
    	 		historySheet(data);
    	 	},_num)

    	 	ajaxDate2($("#sheet-date").html(),_type,function(data){
                            	 		htmlTemplate(datalist,data);//渲染数据
                            	 		//渲染图表
                            	 		//historySheet(data);
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