$(function() {
	var table = $('#meterChangeList').dataTable();
	if (table) {
		table.fnDestroy();
	}
//	findMktList("/queryWaterByHour", {});
	$("#query1").bind("click", onsubmit1);
})

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
			findMktList("/queryMicroFLowList", {
				'startTime' : startTime,
				'endTime' : endTime,
				'staions' : staions[s]
			},temp);
		}
		
	}

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

/**
 * 请求仪表数据
 * 
 * @param parameterObj
 * @param url
 */
function findMktList(url, data,i) {
	$('#meterChangeList'+i)
			.DataTable(
					{
						"bPaginate" : true,
						"searching":false,
						"bServerSide" : true,
						"sScrollY" : "500px",
						"ajax" : {
							url : url,
							dataType : "json",
							type : "post",
							data : data,
						},
						aoColumns : [ {
							"mData" : "shortName"
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
							"mData" : null
						} ],
						aoColumnDefs : [
								{
									targets : [ 1 ],
									mRender : function(a, b, c, d) {
										if (a == null) {
											return "";
										}
										var date = new Date(a);
										var year = date.getFullYear();
										var month = date.getMonth() + 1;
										var day = date.getDate();
										var hour = date.getHours();
										var minute = date.getMinutes();
										var second = date.getSeconds();
										return year + '/' + month + '/' + day+ ' ' + hour+':' + minute + ':' + second;
									}
								},
								{
									targets : [ 9 ],
									mRender : function(a, b, c, d) {
										var html = '<a href=" " class="btn btn-success btn-xs" data-toggle="modal" data-target=".watch-details"'
//												+ a.num
												+ '<span class="bold">查看详情</span>'
												+ '</a>';
										return html;
									}
								}, {
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
