
var timeData = [ '2009/6/12 2:00', '2009/6/12 3:00', '2009/6/12 4:00',
			'2009/6/12 5:00' ];
var myChart = echarts.init(document.getElementById('main'));
var symbolSize = 20;
var data = [[1484109045000, 10], [1484109145000, 10], [1484109245000, 20], [1484109345000, 30], [1484109545000, 40], [1484109645000, 40], [1484109745000, 11], [1484109845000, 40], [1484109945000, 40]];
var data2 = [[1484109045000, 11], [1484109145000, 5], [1484109245000, 15], [1484109345000,20], [1484109545000, 60], [1484109645000, 30], [1484109745000, 1], [1484109845000, 30], [1484109945000, 40]];

 $(document).ready(function(){

   	var today = getNowFormatDate();
   	var todayM = today + ' 00:00';
   	var todayN = today + ' 23:59';
   	$('.input-todayM').val(todayM);
   	$('.input-todayN').val(todayN);


   	timeData = timeData.map(function(str) {
   		return str.replace('2009/', '');
   	});

   	//echartShow(data,null,'[a,c]');


   	$("#queryFlowAndPress").bind("click", findMktList);
   	
   	
   	//默认显示前三个
//   	var opt = $("option");
//   	var length = opt.length;
//   	if(length > 0){
//   		setTimeout("findMktList()",10);
//   	}
   });


/**
 * 生成echart
 * @param timeData	时间数组
 * @param flows		流量，是个二维数组
 * @param press		压力，是个二维数组
 */
function echartShow(data,seriesData,legendData){

   option = {
           title: {
               text: ''
           },
           toolbox: {
               feature: {
                   dataZoom: {yAxisIndex: 'none'},
                   dataView : {show: false, readOnly: false}
               }
           },
           tooltip: {
               trigger: 'axis',
               formatter: function(params) {
                   var result = '';
                   params.forEach(function (item) {

                       var itemData=item.data;
                       if(itemData){

                           var value=item.data[1];
                           var unit=item.seriesName.indexOf("压")>0?"KPa":"m3/h";

                           result += '<span style="display:inline-block;margin-right:5px;border-radius:10px;width:9px;height:9px;background-color:' + item.color + '"></span>';
                           result += item.seriesName + " : " +value+ " " + unit + "<br/>"
                       }


                   });
                   return result;
               }
           },
           legend: {
               data:data.legend,
               left: 'center'
           },
           dataZoom: [
                   {
                       show: true,
                       realtime: true,
                       start: 80,
                       end: 100
                   },
                   {
                       type: 'inside',
                       realtime: true,
                       start: 65,
                       end: 85
                   },
                   {
                       type: 'slider',
                       yAxisIndex: [0,1],
                       filterMode: 'empty'
                   }
               ],
           xAxis:[{
               position:'bottom',
               type: 'time',
               //min:1484109045000,
               //max:1484194845000,
               axisLabel:{
                   formatter: function (value, index) {
                       // 格式化成月/日，只在第一个刻度显示年份
                       var date = new Date(value);
                       var texts = [(date.getMonth() + 1), date.getDate()];
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
                       if (index === 0) {
                           //texts.unshift(date.getYear());
                           //texts.join('/')+" "+
                       }
                       return hour+":"+minute;
                   }
               },
               axisLine: {onZero: false}
           }],
           yAxis: [
               {
                   name : '流量(m3/h)',
                   type: 'value',
                   nameLocation:'end',
                   max:data.maxFlow+data.deltaFlow*15,
                   min:data.minFlow-data.deltaFlow*5,
                   axisLabel:{
                      formatter: function (value, index) {
                          return value.toFixed(1);
                      }
                   },
                   //max:data.series[0].max4x,
                   splitLine:{show:false}
               },
               {
                   name : '压力(KPa)',
                   nameLocation: 'end',
                   max:data.maxPress+data.deltaPress*13,
                   min:data.minPress-data.deltaPress*3,
                   axisLabel:{
                     formatter: function (value, index) {
                         return value.toFixed(1);
                     }
                   },
                   //max:data.series[0].max4y,
                   type : 'value',
                   inverse: false,
                   splitLine:{show:false}
               }
           ],
           series:data.series,
    };

    console.log(new Date('2017/01/12 12:30:45').getTime());

    //setOption之前初始化
    var myChart1=echarts.init(document.getElementById('main'));
    
	// 使用刚指定的配置项和数据显示图表。
	myChart1.setOption(option);


}

function showTooltip(dataIndex) {
    myChart.dispatchAction({
        type: 'showTip',
        seriesIndex: 0,
        dataIndex: dataIndex
    });
}

function hideTooltip(dataIndex) {
    myChart.dispatchAction({
        type: 'hideTip'
    });
}

/*
            时间格式化工具
            把Long类型的yyyy-MM-dd日期还原yyyy-MM-dd格式日期
        */
        function dateFormatUtil(longTypeDate){
            var dateTypeDate = "";
            var date = new Date();
            date.setTime(longTypeDate);
            //dateTypeDate += date.getFullYear();   //年
            dateTypeDate +=  getMonth(date); //月
            dateTypeDate += "-" + getDay(date);   //日
            dateTypeDate += " " + getHour(date)+":"+getMinute(date);   //日
            return dateTypeDate;
        }

        //返回 01-12 的月份值
        function getMonth(date){
            var month = "";
            month = date.getMonth() + 1; //getMonth()得到的月份是0-11
            if(month<10){
                month = "0" + month;
            }
            return month;
        }

        //返回01-30的日期
        function getDay(date){
            var day = "";
            day = date.getDate();
            if(day<10){
                day = "0" + day;
            }
            return day;
        }

        //返回01-30的日期
        function getHour(date){
            var day = "";
            day = date.getHours();
            if(day<10){
                day = "0" + day;
            }
            return day;
        }

        //返回01-30的日期
                function getMinute(date){
                    var day = "";
                    day = date.getMinutes();
                    if(day<10){
                        day = "0" + day;
                    }
                    return day;
                }

/**
 * 
 * @returns
 */
function createSerieses(dataflag){
	var series=[];
	for(var i=0;i< dataflag.flows.length;i++){//流量
		series.push(createSeries(dataflag.flows[i],1,dataflag.flowNum[i],dataflag.station[i]));
	}
	for(var i=0;i< dataflag.presses.length;i++){//压力
		series.push(createSeries(dataflag.presses[i],0,dataflag.flowNum[i],dataflag.station[i]));
	}
	return series;
}

/**
 * 将从后台获得的数据，流量、压力进行格式化成series的数组对象
 * @returns
 */
function createSeries(data,flag,flowNum,station){
	var obj=new Object();
	obj.type='line';
//	obj.name=flag==1?"水表号:"+flowNum+"#站点:"+station+"#流量":"水表号："+flowNum+"#站点:"+station+"#压力";
	obj.name=flag==1?"流量":"压力";
	if(flag!=1){
		obj.xAxisIndex=1;
		obj.yAxisIndex=1;
	}
	obj.symbolSize=8;
	obj.hoverAnimation=false;
	obj.data=data;
	obj.markLine={
			data : [ {
		type : 'average',
		name : '平均值'
	} ]};
	return obj;
}

function getNowFormatDate () {
                var date = new Date();
                var separator = "-";
                var year = date.getFullYear();
                var month = date.getMonth() + 1;
                var strDate = date.getDate();
                if (month <= 9) {
                   month = '0' + month;
                }
                if (strDate <= 9) {
                    strDate = '0' + strDate;
                }
                var currentDate = year + separator + month + separator + strDate;
                return currentDate;
}

/**
 * 拆分数据
 * @param rawData
 * @returns
 */
function splitData(callbackData) {
	var rawData=callbackData.data;
    var times = [];
    var flows = [];
    var presses = [];
    var flowNum = [];//水表号
    var station = [];//站点
    for (var i = 0; i < rawData.length; i++) {
    	var line=rawData[i];//因为是个二维数组，所以第一次获得的还是个list集合
    	var time = [];
        var flow = [];
        var press = [];
		flowNum.push(line[0]==null?"":line[0].num);//水表号
		station.push(line[0]==null?"":line[0].station);//站点
    	for(var j=0;j<line.length;j++){
    		time.push(getNowFormatDate(line[j].time));//时间
            flow.push(line[j].flow);//流量
            press.push(line[j].press);//压力
    	}
    	times.push(time);
    	flows.push(flow);
    	presses.push(press);
    }
    return {
    	times: times,
        flows: flows,
        presses: presses,
        flowNum: flowNum,
        station: station
    };
}



/**
 * 请求仪表数据
 * 
 * @param parameterObj
 * @param url
 */
function findMktList() {
	var startTime = $('#startTime').val();
	var endTime = $('#endTime').val();
	var stations = $('#stations').val();
	if(startTime==null){
		alert("请选择开始时间!");
		return ;
	}
	if(endTime==null){
		alert("请选择结束时间!");
		return ;
	}
	if(stations==null){
		alert("请选择站点!");
		return ;
	}
	if(stations.length>5){
		alert("站点不得超过5个!");
		return ;
	}
	var paremeter=new Object();
	paremeter.startTime=startTime;
	paremeter.endTime=endTime;
	paremeter.stations=stations;
console.log(startTime);
console.log(endTime);
    console.log(stations);

	$.ajax({
    		type: "post",
    		url: "/queryFlowAndPress",
    		dataType:"json",
    		headers:{"Accept":"application/json",
    				"Content-Type":"application/json"},
    		data: JSON.stringify(paremeter),
    		success: function(data, textStatus){

    		    $.each(data,function(n,point) {
    		        console.log(n+"----"+point);
    		    });

                console.log(data);
                echartShow(data,null,'[a,c]');
    		    /*
    			var splitData1=splitData(data);//将二维集合拆分成时间，流量，压力
    			var seriesData1es=createSerieses(splitData1)//将拆分后的数据合并到seriesData
    			echartShow(splitData1.times[0],seriesData1es,'[a,b]');//传时间，和series数据过去
    			*/
    		},
    		error: function(){
    			//请求出错处理
    		}
        });
}
