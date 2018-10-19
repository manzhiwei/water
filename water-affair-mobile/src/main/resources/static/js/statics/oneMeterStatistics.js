$(function() {
    var table = $('#meterListTable').dataTable();
    if (table) {
        table.fnDestroy();
    }
    showTable(current_mid,"flow");
    showChart(current_mid,"flow");

    //瞬时流量
    $('.checkflow input').on('ifChecked',function () {
        showTable(current_mid,"flow");
        showChart(current_mid,"flow");
    });

     //压力
    $('.checkpress input').on('ifChecked',function () {
        showTable(current_mid,"press");
        showChart(current_mid,"press");
    });

    //正向累计
    $('.checktotalflow input').on('ifChecked',function () {
        showTable(current_mid,"totalflow");
        showChart(current_mid,"totalflow");
    });

    //反向累计
    $('.checkftotalflow input').on('ifChecked',function () {
        showTable(current_mid,"ftotalflow");
        showChart(current_mid,"ftotalflow");
    });

    //工作电压
    $('.checkvoltage input').on('ifChecked',function () {
        showTable(current_mid,"currentV");
        showChart(current_mid,"currentV");
    });

    //工作电流
    $('.checkcurrent input').on('ifChecked',function () {
        showTable(current_mid,"currentI");
        showChart(current_mid,"currentI");
    });

    //信号
    $('.checksignal input').on('ifChecked',function () {
        showTable(current_mid,"Signal_strength");
        showChart(current_mid,"Signal_strength");
    });
})

/**
 * 提交form表单
 * 
 * @returns
 */
function submitMeter() {
	var meterTypeId=$("#meterTypeId").val();
	var outputSignalTypeId=$("#outputSignalTypeId").val();
	var powerTypeId=$("#powerTypeId").val();
	if(meterTypeId!=""){
		$("#metertype").val($("#meterTypeId").find("option:selected").text());
	}
	if(outputSignalTypeId!=""){
		$("#outputSignalType").val($("#outputSignalTypeId").find("option:selected").text());
	}
	if(powerTypeId!=""){
		$("#powerType").val($("#powerTypeId").find("option:selected").text());
	}
	$("#commentForm").submit();
}

/**
 * 请求仪表数据
 * 
 * @param parameterObj
 * @param url
 */
function showTable(mid,type) {
    var table = $('#meterListTable').dataTable();
    if (table) {
        table.fnDestroy();
    }

	$('#meterListTable')
			.DataTable(
					{
						"autoWidth" : false,
						"searching":false,
						"aLengthMenu":[[12],["12条"]],
						"ajax" : {
							url : "/queryoneSignMsg?mid="+mid+"&type="+type,
							type : "post",
							dataSrc : "entity"
						},
						aoColumns : [ {
							"mData" : "hour"
						}, {
							"mData" : "today"
						}, {
							"mData" : "yestoday"
						}, {
							"mData" : "theDayBeforeYesterday"
						} ],
						aoColumnDefs : [
								 {
									"defaultContent" : "-",
									"targets" : "_all"
								} ]
					});
}

function onPost(url, data) {
	var temp = document.createElement("form");
	temp.action = url;
	temp.method = "post";
	temp.style.display = "none";
	for ( var x in data) {
		var opt = document.createElement("textarea");
		opt.name = x;
		opt.value = data[x];
		// alert(opt.name)
		temp.appendChild(opt);
	}
	document.body.appendChild(temp);
	temp.submit();
	return temp;
}
