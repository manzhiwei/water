$(function() {
	var table = $('#meterChangeList').dataTable();
	if (table) {
		table.fnDestroy();
	}
//	findMktList("/findByConditionRecord",{});
	 $("#query1").bind("click", onsubmit1);
	 onsubmit1();
})

function onsubmit1(){
	var startTime=$('#startTime').val();
	var endTime=$('#endTime').val();
	var type=$('#type').val();
	var ides=$('#ides').val();
	$('#meterChangeList').dataTable().fnDestroy(); 
	if(ides!=null){
		ides=ides.join(",");
		findMktList("/findByConditionRecord",{'startTime':startTime,'endTime':endTime,'type':type,'ides':ides});
	}
}

/**
 * 请求数据
 * 
 * @param parameterObj
 * @param url
 */
function findMktList(url,data) {
	$('#meterChangeList').DataTable(
			{	
				"bPaginate":true,
				"searching":false,
				"bServerSide":true,
				"sScrollY": "300px",
				"ajax" : {
					url : url,
					dataType : "json",
					type : "post",
					data:data,
				},
				aoColumns : [ {
					"mData" : "id"
				},{
					"mData" : "userName"
				}, {
					"mData" : "eventName"
				}, {
					"mData" : "date"
				}, {
					"mData" : "eventName"
				}, {
					"mData" : "ipAddress"
				} ],
				aoColumnDefs : [ {
					targets : [3],
					mRender : function(a, b, c, d) {
						//var html = new Date(a);
						//return html.getFullYear()+'-'+(html.getMonth()+1)+'-'+html.getDate()+' '+html.getHours()+':'+html.getMinutes()+':'+html.getSeconds();
						return dateFormatUtil(a);
					}
				},{
					"defaultContent": "-",
				    "targets": "_all"
				} ]
			});
}


function onPost(url,data){
	var temp = document.createElement("form");        
    temp.action = url;        
    temp.method = "post";        
    temp.style.display = "none";        
    for (var x in data) {        
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

/*
时间格式化工具
把Long类型的yyyy-MM-dd日期还原yyyy-MM-dd格式日期
*/
function dateFormatUtil(longTypeDate){
	var dateTypeDate = "";
	var date = new Date();
	date.setTime(longTypeDate);
	dateTypeDate += date.getFullYear();   //年
	dateTypeDate += "-" + getMonth(date); //月
	dateTypeDate += "-" + getDay(date);   //日
	dateTypeDate += " " + getHour(date)+":"+getMinute(date)+":"+getSecond(date);   //日
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

//返回01-30的日期
function getSecond(date){
    var day = "";
    day = date.getSeconds();
    if(day<10){
        day = "0" + day;
    }
    return day;
}
