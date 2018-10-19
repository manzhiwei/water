$(function() {
/*
	var table = $('#meterChangeList').dataTable();
	if (table) {
		table.fnDestroy();
	}
	*/
//	findMktList("/queryWaterByHour", {});
	$("#query1").bind("click", onsubmit1);

})

$(document).ready(
	function(){
		//默认显示前三个
		var opt = $("option");
		var length = opt.length;
		if(length > 0){
			setTimeout("onsubmit1()",10);
		}
	}
)


//点击查看详情
$('#myModal5').on('show.bs.modal', function (event) {
    var detail = $(event.relatedTarget);
    cleanDetailInfo();
    createDetailInfo();

    //动态显示表头信息
    var title=detail.data('mid').split(",");
    //通过mid查询名字
    var meterName = '';
    $.ajax({
        url: "/queryMeterNameById?mid="+title[0],
        async: false
    }).done(function(res, textStatus){
        meterName = res;
    }).fail(function(){
        console.log("出错啦！");
    });

    document.getElementById("model_title").value="站点："+meterName+"  时间：20"+title[1]+"时";
    //显示表格详情
    createModalTable("/queryHourData4Pc?detail="+detail.data('mid'));
});

function onsubmit1() {
	var startTime = $('#startTime').val();
	var endTime = $('#endTime').val();
	var staions = $('#staions').val();
	if (staions == null) {
		alert("站点不能为空");
		return;
	}else{
		cleanTab();
		for (s in staions) {
			var temp=Number(s)+1;
			createTab(temp,staions[s]);
			createTabContent(temp);
			findMktList("/queryWaterByHour", {
				'startTime' : startTime,
				'endTime' : endTime,
				'staions' : staions[s]
			},temp);
		}
	}
    $('#tab-2').tab('show');
}

function cleanTab(){
	$("#tabes").children().remove();
	$(".tab-content").children().remove();
}

function createTab(i,station){
	var temp=i;
	var li='<li><a data-toggle="tab" href="#tab-'+temp+'">'+station+'</a></li>';
	if(temp==1){
		li='<li class="active"><a data-toggle="tab" href="#tab-'+temp+'">'+station+'</a></li>';
	}
	selectedTabName=station;

	$("#tabes").append(li);//追加tab
}

function createTabContent(i){
	var temp=i+1;
	var content='<div id="tab-'+i+'" class="tab-pane active">'+
		        '<div class="panel-body">'+
			        '<div class="table-responsive table-relative">'+
				        '<table id="meterChangeList'+i+'" class="table table-striped dataTables-meter" >'+
				        	'<thead>'+
				            '<tr>'+
				            '    <th>站点名称</th>'+
				            '    <th>公司名称</th>'+
				            '    <th>抄表时间</th>'+
				            '    <th>瞬时流量</th>'+
				            '    <th>正向累积总量</th>'+
				            '    <th>反向累积总量</th>'+
				            '    <th>净累计</th>'+
				            '    <th>瞬时压力</th>'+
				            '    <th>同比</th>'+
				            '    <th>环比</th>'+
				            '    <th>详情</th>'+
				            '</tr>'+
				            '</thead>'+
				            '<tbody>'+
				            '</tbody>'+
				        '</table>'+
			    '</div>'+
			'</div>'+
			'</div>';
	$(".tab-content").append(content);//追加content
}

function cleanDetailInfo(){
	$(".detail-content").children().remove();
};

function createDetailInfo(){
	var content='<div id="hour_list" class="tab-pane active">'+
		        '<div class="panel-body">'+
			        '<div class="table-responsive table-relative">'+
				        '<table id="hour_record_list" class="table table-striped" >'+
				        	'<thead>'+
				            '<tr>'+
				            '    <th>抄表时间</th>'+
				            '    <th>瞬时压力</th>'+
				            '    <th>瞬时流量</th>'+
				            '    <th>正向累积总量</th>'+
				            '    <th>反向累积总量</th>'+
				            '    <th>净累计</th>'+
				            '</tr>'+
				            '</thead>'+
				            '<tbody>'+
				            '</tbody>'+
				        '</table>'+
			    '</div>'+
			'</div>'+
			'</div>';
	$(".detail-content").append(content);//追加content
}


/**
 * 请求仪表数据
 * 
 * @param parameterObj
 * @param url
 */
function findMktList(url, data,i) {
	$('#meterChangeList'+i).DataTable(
    {
        "bPaginate" : true,
        "searching":false,
        "bServerSide" : true,
        "sScrollY" : "500px",
        "ajax" : {
            url : url,
            dataType : "json",
            type : "post",
            async: false,
            data : data,
        },
        aoColumns : [ {
            "mData" : "shortName"
        }, {
            "mData" : "companyName"
        }, {
            "mData" : "iTime"
        }, {
            "mData" : "flow"
        }, {
            "mData" : "totalflow"
        }, {
            "mData" : "ftotalflow"
        }, {
            "mData" : "ntotalflow"
        }, {
            "mData" : "press"
        }, {
            "mData" : "yearByYear"
        }, {
            "mData" : "mothByMonth"
        }, {
            "mData" : "detail"
        } ],
        aoColumnDefs : [
                {
                    targets : [ 2 ],
                    mRender : function(a, b, c,d) {
                        if (a == null) {
                            return "";
                        }
                        var date = new Date(a);
                        var year = date.getFullYear();
                        var month = date.getMonth() + 1;
                        (month <10) ? month="0" +month: month=month;
                        var day = date.getDate();
                        (date.getDate() <10) ? day="0" +date.getDate(): day=date.getDate();
                        var hour = date.getHours();
                        (date.getHours() <10) ? hour="0" +date.getHours(): hour=date.getHours();
                        var minute = date.getMinutes();
                        (date.getMinutes() <10) ? minute="0" +date.getMinutes(): minute=date.getMinutes();
                        var second;
                        (date.getSeconds() <10) ? second="0" +date.getSeconds(): second=date.getSeconds();

                        return year + '/' + month + '/' + day+ ' ' + hour+':' + minute + ':' + second;

                        //var date = new Date(a* 1000);
                        //return date.toLocaleString();
                    }
                },
                {
                    targets : [ 3 ],
                    mRender : function(a, b, c,d) {
                        var html =returnFloat(a);
                        return html;
                    }
                },
                {
                    targets : [ 4 ],
                    mRender : function(a, b, c,d) {
                        var html =returnFloat(a);
                        return html;
                    }
                },
                {
                    targets : [ 5 ],
                    mRender : function(a, b, c,d) {
                        var html =returnFloat(a);
                        return html;
                    }
                },
                {
                    targets : [ 6 ],
                    mRender : function(a, b, c,d) {
                        var html =returnFloat(a);
                        return html;
                    }
                },
                {
                    targets : [ 7 ],
                    mRender : function(a, b, c,d) {
                        var html =returnFloat(a);
                        return html;
                    }
                },
                {
                    targets : [ 8 ],
                    mRender : function(a, b, c,d) {
                        var html =returnFloat(a);
                        return html;
                    }
                },
                {
                    targets : [ 9 ],
                    mRender : function(a, b, c,d) {
                        var html =returnFloat(a);
                        return html;
                    }
                },
                {
                    targets : [ 10 ],
                    mRender : function(a, b, c,d) {
                        var html ='<span class="bold" data-toggle="modal" data-mid="' + a + '" data-target="#myModal5">查看</span>';
                        return html;
                    }
                }, {
                    "defaultContent" : "-",
                    "targets" : "_all"
                } ]
    });
}


function createModalTable(url) {
	$('#hour_record_list').DataTable(
    {
        "bAutoWidth":false,
        "bPaginate" : false,
        "searching":false,
        "bServerSide" : true,
        "lengthMenu": [ 20, 30, 40, 50, 100 ],
        "ajax" : {
            url : url,
            dataType : "json",
            type : "post",
            async: false
        },
        "columns": [
            { "searchable": false,
             "name": "name",
             "title": "My Name",
             "width": "80px"},
            null,
            null,
            null,
            null,
            null
          ],
        aoColumns : [ {
            "mData" : "time"
        }, {
            "mData" : "press"
        }, {
            "mData" : "flow"
        }, {
            "mData" : "totalflow"
        }, {
            "mData" : "ftotalflow"
        }, {
            "mData" : "ntotalflow"
        }],
        aoColumnDefs : [
                {
                    targets : [0],
                     mRender : function(a, b, c,d) {
                        if (a == null) {
                            return "";
                        }
                        var date = new Date(a* 1000);
                        return date.toLocaleString();
                    }
                 },
                {
                    "defaultContent" : "-",
                    "targets" : "_all"
                } ]
    });
}