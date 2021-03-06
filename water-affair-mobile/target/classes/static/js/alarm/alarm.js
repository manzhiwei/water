$(function() {
	var table = $('#meterChangeList').dataTable();
	if (table) {
		table.fnDestroy();
	}
	findMktList("/queryWarningByCondition",{});
	 $("#query1").bind("click", onsubmit1);
})


//阅读当前弹出的告警信息框
function closeAlarmMessage(){
   $.ajax({
           url: "/closeAlarmByAid?aid="+document.getElementById("aid").value,
           dataType: "json",
           async: false
       }).done(function(res, textStatus){
            var table = $('#meterChangeList').dataTable();
                if (table) {
                    table.fnDestroy();
                }
                findMktList("/queryWarningByCondition",{});
       }).fail(function(){
           console.log("出错啦！");
       });
}

//阅读当前弹出的告警信息框
function readAlarmMessage(){
   $.ajax({
           url: "/readAlarmByAid?aid="+document.getElementById("aid").value,
           dataType: "json",
           async: false
       }).done(function(res, textStatus){
        var table = $('#meterChangeList').dataTable();
            if (table) {
                table.fnDestroy();
            }
            findMktList("/queryWarningByCondition",{});
       }).fail(function(){
           console.log("出错啦！");
       });
}

//点击查看详情
$('#alertModel').on('show.bs.modal', function (event) {
    var detail = $(event.relatedTarget);

    //动态显示表头信息
    var aid=detail.data('aid');
    //通过mid查询名字
    console.log(aid);

    var meterName = '';
    $.ajax({
        url: "/queryAlarmByAid?aid="+aid,
        dataType: "json",
        async: false
    }).done(function(res, textStatus){
        //var content=res.split(",");
        console.log(res);
        document.getElementById("aid").value=res.id;
        document.getElementById("model_title").value="站点名称："+res.mname;
        document.getElementById("alarm_type").value="告警类型："+res.type;
        document.getElementById("alarm_content").value="告警内容："+res.content;

        var newDate = new Date();
        newDate.setTime(res.createTime.time);
        document.getElementById("alarm_time").value="告警时间："+newDate.toLocaleString();
    }).fail(function(){
        console.log("出错啦！");
    });


    //显示表格详情
    //createModalTable("/queryHourData4Pc?detail="+detail.data('mid'));
});


function onsubmit1(){
	var startTime=$('#startTime').val();
	var endTime=$('#endTime').val();
	var staions=$('#staions').val();
	if(staions!=null){
		staions=staions.join(",");
	}else if (staions == null) {
		alert("站点不能为空");
		return;
	}
	$('#meterChangeList').dataTable().fnDestroy(); 
	findMktList("/queryWarningByCondition",{'startTime':startTime,'endTime':endTime,'staions':staions});
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
					"mData" : null
				},{
					"mData" : "mname"
				}, {
					"mData" : "type"
				}, {
					"mData" : "createTime"
				}, {
					"mData" : "status"
				},{"mData" : "id"} ],
				aoColumnDefs : [ {
					targets : [0],
					mRender : function(a, b, c, d) {
						 // 显示行号  
	                    var startIndex = d.settings._iDisplayStart;  
	                    return startIndex + d.row + 1;  
					}
				},{
					targets : [3],
					mRender : function(a, b, c, d) {
						var html = new Date(a);
						return html.getFullYear()+'-'+(html.getMonth()+1)+'-'+html.getDate()+' '+html.getHours()+':'+html.getMinutes()+':'+html.getSeconds();
					}
				},{
					targets : [4],
					mRender : function(a, b, c, d) {
						if(a==1){
							return "未响应";
						}else if(a==2){
                            return "未处理";
                        }else{
							return "已处理";
						}
					}
				},
                {
                   targets : [ 5 ],
                   mRender : function(a, b, c,d) {
                       var html ='<span class="bold" data-toggle="modal" data-aid='+a+' data-target="#alertModel">处理</span>';
                       return html;
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
