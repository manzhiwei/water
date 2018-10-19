$(function() {
	var table = $('#meterListTable').dataTable();
	if (table) {
		table.fnDestroy();
	}
	findMktList();

})


/**
 * 请求仪表数据
 *
 * @param parameterObj
 * @param url
 */
function findMktList(parameter) {
	var columns=[ {
		"mData" : "num"
	}, {
		"mData" : "shortName"
	}, {
		"mData" : "meterSizeStr"
	}, {
		"mData" : "mversion"
	}, {
		"mData" : "gversion"
	}, {
		"mData" : "datanum"
	}, {
		"mData" : "package_"
	}, {
		"mData" : "positionNo"
	},{
        "mData" : null
    },{
        "mData" : null
    },{
        "mData" : function(parameter){
              return getMyDate(parameter.iTime)//<span style="color:#ff0000;">通过调用函数来格式化所获取的时间戳</span>
          }
      }
       ];

	$('#meterListTable')
			.DataTable(
					{
						"autoWidth" : false,
						"searching":false,
						"ajax" : {
							url : "/queryMachineInfo",
							type : "post",
							dataType : "json",
							data : parameter,
							dataSrc : "entity"
						},
						aoColumns : columns,
						aoColumnDefs : [
								{
									"defaultContent" : "-",
									"targets" : "_all"
								} ]
					});
}

 function getMyDate(time){

    var oDate = new Date(time),
        oYear = oDate.getFullYear(),
        oMonth = oDate.getMonth()+1,
        oDay = oDate.getDate(),
        oHour = oDate.getHours(),
        oMin = oDate.getMinutes(),
        oSen = oDate.getSeconds(),
        oTime = oYear +'-'+ getzf(oMonth) +'-'+ getzf(oDay) +' '+ getzf(oHour) +':'+ getzf(oMin) +':'+getzf(oSen);//最后拼接时间
    return oTime;
  };

//补0操作,当时间数据小于10的时候，给该数据前面加一个0
function getzf(num){
    if(parseInt(num) < 10){
        num = '0'+num;
    }
    return num;
}

//查询某个站点
function show(){
    var obj = document.getElementById("search-content1");
    var value = obj.value;
    console.log(value);
	$('#meterListTable').dataTable().fnDestroy();
    findMktList({"subUserName":value,"flag":"true"});
};
