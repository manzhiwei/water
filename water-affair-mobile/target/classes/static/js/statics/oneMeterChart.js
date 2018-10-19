var myChart = echarts.init(document.getElementById('main'));

/**
 * 生成echart
 * @param timeData	时间数组
 * @param flows		流量，是个二维数组
 * @param press		压力，是个二维数组
 * @param chartType	line/bar
 */
function echartShow(data,seriesData,legendData,chartType){

	if(chartType == null || '' == chartType){
		chartType = 'line';
	}
	
  option = {
      title: {
          text: ''
      },
      tooltip: {
          trigger: 'axis'
      },
      legend: {
          data:['前日','昨日','今日']
      },
      xAxis:  {
          type: 'category',
          boundaryGap: false,
          data: ['00','01','02','03','04','05','06','07','08','09','10','11','12','13','14','15','16','17','18','19','20','21','22','23']
      },
      yAxis: {
          type: 'value',
          axisLabel: {
              formatter: '{value}'
          }
      },
      series: [
          {
              name:'前日',
              type:chartType,
              data:data.dbyesterday,
              smooth:true,  //让曲线平滑
              markPoint: {

              },
              markLine: {

              }
          },
          {
              name:'昨日',
              type:chartType,
              data:data.yesterday,
              smooth:true,  //让曲线平滑
              markPoint: {

              },
              markLine: {

              }
          },
          {
                        name:'今日',
                        type:chartType,
                        smooth:true,  //让曲线平滑
                        data:data.today,
                        markPoint: {

                        },
                        markLine: {

                        }
                    }
      ]
  };

	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option);


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
function showChart(mid,type) {
	var startTime = $('#startTime').val();
	var endTime = $('#endTime').val();
	var stations = $('#stations').val();

	var paremeter=new Object();
	paremeter.startTime="2017-03-05 00:00";
	paremeter.endTime="2017-03-05 23:59";
	paremeter.stations=["test03-M0-0号坑"];

	var chartType = 'line';
	if('totalflow' == type || 'ftotalflow' == type){
		chartType = 'bar';
	}
	
    console.log(stations);

	$.ajax({
    		type: "post",
    		url: "/queryOneMeterChart?mid="+mid+"&type="+type,
    		dataType:"json",
    		headers:{"Accept":"application/json",
    				"Content-Type":"application/json"},
    		data: JSON.stringify(paremeter),
    		success: function(data, textStatus){
                console.log(data);
                echartShow(data,null,'[a,c]',chartType);
    		},
    		error: function(){
    			//请求出错处理
    		}
        });
}
