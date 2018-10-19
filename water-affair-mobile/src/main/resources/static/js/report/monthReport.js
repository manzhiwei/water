$(document).ready(function(){

    $('.layer-date').click(function(){
        laydate({istime: true, format: 'YYYY-MM', choose: function(dates){
            console.log(dates);
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
		$("#type").change(function(){
			getData();
		});
	}
	getData();
}

/**
 * 生成echart
 */
function echartShow(xData, avgData, maxData, minData){

	var titleText = {
			station : $("#station").val(),
			date : $("option[value="+ $("#type").val()+"]").text() + " " + $("#startTime").val()
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
		        data:['平均值','最大值','最小值']
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
		            name:'平均值',
		            type:'bar',
		            data:avgData
		        },
		        {
		            name:'最大值',
		            type:'bar',
		            data:maxData
		        },
		        {
		            name:'最小值',
		            type:'bar',
		            data:minData
		        }
		    ]
		};

	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option);

}

function getData(){
	var station = $("#station").val();
	var date = $("#startTime").val();
	var index = $("#type").val();
	
	$.ajax({
		url:"/getMonthReport",
		method:"POST",
		dataType:"json",
		data:"station=" + station +"&date=" + date,
		success:function(data){
			var xData = new Array();
			var avgData = new Array();
			var maxData = new Array();
			var minData = new Array();
			for(var i=0;i<data.result.length-5;i++){
				xData[i]=data.result[i][0];
				avgData[i]=data.result[i][Number(index) + 1];
				maxData[i]=data.result[i][Number(index) + 2];
				minData[i]=data.result[i][Number(index) + 3];
			}
			echartShow(xData, avgData, maxData, minData);
		}
	});
}