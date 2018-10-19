$(document).ready(function(){

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
            function getNowFormatMonth () {
                var date = new Date();
                var separator = "-";
                var year = date.getFullYear();
                var month = date.getMonth() + 1;
                if (month <= 9) {
                   month = '0' + month;
                }
                var currentMonth = year + separator + month;
                return currentMonth;
            }
            function getNowFormatMonthNext () {
                var date = new Date();
                var separator = "-";
                var year = date.getFullYear();
                var nextMonth = date.getMonth() + 2;
                if (nextMonth <= 9) {
                   nextMonth = '0' + nextMonth;
                }
                if (nextMonth > 12) {
                    nextMonth = '01';
                    year+=1;
                }
                var currentMonthNext = year + separator + nextMonth;
                return currentMonthNext;
            }
            var today = getNowFormatDate();
            var nowMonth = getNowFormatMonth();
            var nextMonth = getNowFormatMonthNext();
            var todayM = today ;
//            var todayN = today + ' 23:59';//经客户要求将时间限定为及时，不再写死为23点
            var date = new Date();
            var todayN = today + " "+date.getHours()+":"+date.getMinutes();
            var nowMonthStart = nowMonth + '-01 ';
            var NextMonthStart = nextMonth + '-01 ';
            //$('.input-todayM').val(todayM);
            $('.input-todayN').val(todayN);
            $('.search-checks-day input').on('ifChecked',function () {
                $('.input-todayM').val(todayM);
                $('.input-todayN').val(todayN);
            });
            $('.search-checks-month input').on('ifChecked',function () {
                $('.input-todayM').val(nowMonthStart);
                $('.input-todayN').val(NextMonthStart);
            });
            $('.layer-date').click(function(){
                laydate({istime: true, format: 'YYYY-MM-DD', choose: function(){
                    $('.i-checks input').iCheck('uncheck');
                }})
            })
});

var myChart = null;
function showReport(){
	$('.report-modal').modal('show');
	if(myChart == null){
		myChart = echarts.init(document.getElementById('main'));
		$("#station").change(function(){
			getData();
		});
	}
	getData();
}

/**
 * 生成echart
 */
function echartShow(xData, pressData, flow, totalFlow){

	var titleText = {
			station : $("#station").val(),
			date : $("#startTime").val()
	}
	
	option = {
			title: {
				text: titleText.station,
				subtext: titleText.date,
				x:'right'
					
			},
		    tooltip : {
		        trigger: 'axis',
		        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
		            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
		        }
		    },
		    legend: {
		        data:['瞬时流量','累计流量','压力'],
		    	selectedMode:'single'
		    },
		    grid: {
		        left: '3%',
		        right: '4%',
		        bottom: '3%',
		        containLabel: true
		    },
		    color : ['#61a0a8', '#c23531','#749f83' , '#d48265', '#91c7ae', '#ca8622', '#bda29a','#6e7074', '#546570', '#c4ccd3'],
		    xAxis : [
		        {
		            type : 'category',
		            data : xData
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value'
		        }
		    ],
		    series : [
		        {
		            name:'瞬时流量',
		            type:'bar',
		            data:flow
		        },
		        {
		            name:'累计流量',
		            type:'bar',
		            data:totalFlow
		        },
		        {
		            name:'压力',
		            type:'bar',
		            data:pressData
		        }
		    ]
		};

	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option);

}

function getData(){
	var station = $("#station").val();
	var date = $("#startTime").val();
	
	$.ajax({
		url:"/getDayReport",
		method:"POST",
		dataType:"json",
		data:"station=" + station +"&date=" + date,
		success:function(data){
			var xData = new Array();
			var pressData = new Array();
			var flow = new Array();
			var totalFlow = new Array();
			for(var i=0;i<24;i++){
				//xData.name=data.result[i][0].substr(0,10);
				xData[i]=data.result[i][0].substr(11);
				pressData[i]=data.result[i][1];
				flow[i]=data.result[i][2];
				totalFlow[i]=data.result[i][3];
			}
			echartShow(xData, pressData, flow, totalFlow);
		}
	});
}