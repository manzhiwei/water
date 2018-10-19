$(function(){
	
	$(".timer-type").on("tap",function(){
		$(this).hasClass("active") ? $(this).removeClass("active") : $(this).addClass("active");
	});

	//header-s-btn
	$(".header-s-btn").on("tap",function(){

	});

	//模拟数据
	var data = {
		"0": [
			{"北京": {"flux": 2.1,"plus":100,"fplus": 200,"rplus": -100,"pa": 1.2,"strth": 66}},
			{"上海": {"flux": 2.4,"plus":130,"fplus": 500,"rplus": -200,"pa": 1.6,"strth": 46}},
			{"广州": {"flux": 1.9,"plus":160,"fplus": 350,"rplus": -150,"pa": 1.9,"strth": 86}}
		],
		"1": {"flux": 2.1,"plus":100,"fplus": 200,"rplus": -100,"pa": 1.2,"strth": 66},
		"2": {"flux": 2.4,"plus":130,"fplus": 500,"rplus": -200,"pa": 1.6,"strth": 46},
		"3": {"flux": 1.9,"plus":160,"fplus": 350,"rplus": -150,"pa": 1.9,"strth": 86}
	};
	function navTstemp(){

	};
	$(".nav-list").on("tap",function(){
		var _type = $(this).attr("data-type");
		var _show = $(".nav-ts-"+_type).attr("data-show");
		//实时数据 首页nav-list 切换
		_show == "false" ? $(".nav-ts-"+_type).attr("data-show","true").fadeIn().parent().siblings().children(".nav-ts").attr("data-show","false").hide() :
		$(".nav-ts-"+_type).attr("data-show","false").hide();
		if($(this).hasClass("active")) return false;
		$(this).addClass("active").siblings().removeClass("active");
	});

	$(".ts-list").on("tap",function(){
		var _txt = $(this).html(),_type = $(this).parents(".nav-list").attr("data-type"),
			_otxt = $(this).parent().siblings().html();
		//当选择数值的时候  和当前的进行对比 如果发生变动则 加载对应的数据
		//这里地区 可以设置一个id  类型可以设置一个 type
		//这里 我们列举 北京 id=1  上海 id = 2 广州 id = 3 全部地区 id = 0
		//瞬时流量 = flux  净累计 = plus  正向累积总量 = fplus  反向累积总量 = rplus 
		//瞬时压力 = pa  信号强度 = strth  
		$(this).parent().hide().siblings().html(_txt);
		if (_txt == _otxt) return false;
		if (_type == "spara") {
			//开始渲染数据
			var _data = data[$(".nav-ts-sarea").siblings().attr("data-id")];
			console.log(_data);
			//dosomething
		}
		return false;
	});


	$(".select-date-l").on("tap",function(){
		var _type = $(this).attr("data-type");
		var _curTime = $("#sheet-date").html().split("-");
		if (_type == "pre") {
			$("#sheet-date").html(_curTime[0]+"-"+_curTime[1]+"-"+(parseInt(_curTime[2])-1));
		}else if (_type == "next") {
			$("#sheet-date").html(_curTime[0]+"-"+_curTime[1]+"-"+(parseInt(_curTime[2])+1));
		}
	})

	$(".select-timer").on("tap",function(){
		var _time = new Date();
		_time = _time.getFullYear()+"-"+ ((_time.getMonth()+1) <10 ? "0"+(_time.getMonth()+1) : (_time.getMonth()+1)) +"-"+(_time.getDate() < 10 ? ("0"+_time.getDate()) : _time.getDate());
		$(this).html(_time);
		laydate({
            elem: "#sheet-date"
        });
	});
	$(".fixed-nav-list").on("tap",function(){
		var _type = $(this).attr("data-type");
		if($(this).hasClass("active") && _type != "para") return false;
		$(this).addClass("active").siblings().removeClass("active");
		switch (_type) {
			case "para":
				$(".para-lists").fadeIn();
				break;
			case "current":
				$(".para-lists").hide();
				break;
			default:
				// statements_def
				break;
		}
	})

	var _once = true;
	$(".header-para").on("tap",function(){
		$(this).siblings(".hpara-lists").fadeIn(function(){
			if (_once) {
				$(".hpara-list").on("tap",function(){
					var _html = $(this).html();
				})
			}
		});
	})
	/**
     * 监测概况折线图
     */
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
		        data:''
		    },
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
		        },
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
	    	xAxisdata: ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'],
	    	maxname: "瞬时流量",
	    	maxdata: [11, 11, 15, 13, 12, 13, 10,11, 11, 15, 13, 12],
	    	minname: "***",
	    	mindata: [1, 2, 2, 5, 3, 2, 0,1, 2, 2, 5, 3]
	    };
		lineImg(_data,function(option){
			chartData.setOption(option,true);
		});
    }catch(e){

    }
    
});