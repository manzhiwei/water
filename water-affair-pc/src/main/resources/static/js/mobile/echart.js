$(document).ready(function(){


    $(function(){
    			$(".nav-list").on("tap",function(){
    				var _type = $(this).attr("data-type");
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
    		     * ,
    	        {
    	            name:_data.minname,
    	            type:'line',
    	            data:_data.mindata,
    	            markLine: {
    	                data: [
    	                    {type: 'average', name: '平均值'},
    	                    [{
    	                        symbol: 'none',
    	                        x: '90%',
    	                        yAxis: 'max'
    	                    }, {
    	                        symbol: 'circle',
    	                        label: {
    	                            normal: {
    	                                position: 'start',
    	                                formatter: '最大值'
    	                            }
    	                        },
    	                        type: 'max',
    	                        name: '最高点'
    	                    }]
    	                ]
    	            }
    	        }
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
    		    		nullarr.push('');
    		    		//伪造的数据  实际得通过ajax调取
    		    		daraarr.push(minute);
    		    		minute += 5;
    		    	}
    		    	console.log(daraarr);
    		    	callback&&callback(xAxisdata);
    		    	// console.log(xAxisdata);
    		    }
    		    //html template
    		    function htmlTemplate(arr,callback){
    		    	var html = '';
    		    	$(".lists-history-data").html("")
    		    	for(var i = 0; i<arr.length;i++){
    		    		html = '<li class="content-list"><span class="content-list-t">'+arr[i]+'</span><span class="content-list-t">2.4</span>';
    		    		$(".lists-history-data").append(html);
    		    	}
    		    	callback&&callback();
    		    }
    		    function getAxisdata(callback){
    		    	if (isTody()) {
    		    		var _currt = new Date();
    		    		var _year = _currt.getFullYear(),_mon = (_currt.getMonth()<10 ? "0"+(_currt.getMonth()+1) : _currt.getMonth()),_day = (_currt.getDate()<10 ? "0"+(_currt.getDate()) : _currt.getDate()),_hour = _currt.getHours(),_min = _currt.getMinutes();
    		    		console.log(_hour)
    		    		// console.log(_year+_mon+_day+_hour+_min);
    		    		//计算时间 5分钟一次
    		    		//总的分钟数次数
    		    		var totalMinT = parseInt((_hour*60 + _min)/5);
    		    		dateT(totalMinT,_mon+"-"+_day,function(data,callback){
    		    			htmlTemplate(data,callback);
    		    		});
    		    		// console.log(totalMin,_mon+"-"+_day);
    		    	}else {
    		    		var _currtime = $("#sheet-date").html().split("-");
    		    		var totalMinT = parseInt(24*60/5);
    		    		dateT(totalMinT,_currtime[1]+"-"+_currtime[2],function(data,callback){
    		    			htmlTemplate(data,callback);
    		    		});
    		    	}
    		    }
    		    getAxisdata();
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
    				        data:_data.legendata
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
    				        type: 'category',
    				        boundaryGap: false,
    				        data: _data.xAxisdata
    				    },
    				    yAxis: {
    				        type: 'value',
    				        axisLabel: {
    				            formatter: '{value}'
    				        }
    				    },
    				    series: [
    				        {
    				            name:_data.maxname,
    				            type:'line',
    				            data:_data.maxdata,
    				            markPoint: {
    				                data: [
    				                    {type: 'max', name: '最大值'},
    				                    {type: 'min', name: '最小值'}
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
    		    try{
    		    	var myChart = echarts.init(document.getElementById("main"));
    				var chartData = echarts.init(main);
    				var app = {};
    				//请求的数据 _data
    				var _data = {
    			    	legendata: ['瞬时流量','***'],
    			    	xAxisdata: nullarr,
    			    	maxname: "瞬时流量",
    			    	maxdata: daraarr,
    			    	minname: "***",
    			    	mindata: [1, 2, 2, 5, 3, 2, 0,1, 2, 2, 5, 3]
    			    };
    				lineImg(_data,function(option){
    					chartData.setOption(option,true);
    				});
    		    }catch(e){

    		    }
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
    				nullarr = [];
    				daraarr = [];
    				getAxisdata(function(){
    					console.log('www');
    				});
    				$("#main").remove();
    				$(".content-sheet").append('<div id="main" style="width: 100%;height:300px;"></div>');

    				var myChart = echarts.init(document.getElementById("main"));
    				var chartData = echarts.init(main);
    				var app = {};
    				//请求的数据 _data
    				var _data = {
    			    	legendata: ['瞬时流量','***'],
    			    	xAxisdata: nullarr,
    			    	maxname: "瞬时流量",
    			    	maxdata: daraarr,
    			    	minname: "***",
    			    	mindata: [1, 2, 2, 5, 3, 2, 0,1, 2, 2, 5, 3]
    			    };
    				lineImg(_data,function(option){
    					chartData.setOption(option,true);
    				});
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

    				console.log(_type);
    				if ($(".nav-list.active").attr("data-type") == "data") {
    					return false;
    				}
    				//重置数据
    				nullarr = [];
    				daraarr = [];
    				getAxisdata(function(){
    					console.log('www');
    				});
    				$("#main").remove();
    				$(".content-sheet").append('<div id="main" style="width: 100%;height:300px;"></div>');

    				var myChart = echarts.init(document.getElementById("main"));
    				var chartData = echarts.init(main);
    				var app = {};
    				//请求的数据 _data
    				var _data = {
    			    	legendata: ['瞬时流量','***'],
    			    	xAxisdata: nullarr,
    			    	maxname: "瞬时流量",
    			    	maxdata: daraarr,
    			    	minname: "***",
    			    	mindata: [1, 2, 2, 5, 3, 2, 0,1, 2, 2, 5, 3]
    			    };
    				lineImg(_data,function(option){
    					chartData.setOption(option,true);
    				});
    				return false;
    			});
    		})


});