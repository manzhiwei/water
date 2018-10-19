$(function() {
	var table = $('#meterListTable').dataTable();
	if (table) {
		table.fnDestroy();
	}
	var num=$('#num').val();
	findMktList(num);
	 $("#addArchi").bind("click", openForm);
	 $("#changeVip").bind("click", changeVip);
})

/**
 * 请求仪表数据
 * 
 * @param parameterObj
 * @param url
 */
function findMktList(num) {
	$('#meterListTable').DataTable(
			{
				"autoWidth": false,
				"searching":false,
				"ajax" : {
					url : "/queryArchivesRecord",
					type : "post",
					data:{"num":num},
					dataSrc: "entity"
				},
				aoColumns : [ {
					"mData" : "id"
				}, {
					"mData" : "checkPeople"
				}, {
					"mData" : "checkTime"
				}, {
					"mData" : null
				}, {
					"mData" : "checkType"
				}, {
					"mData" : null
				} ],
				aoColumnDefs : [ {
					targets : [3],
					mRender : function(a, b, c, d) {
						if(a.resourceImgId!=null){
							var html='<a href="/download/'+a.resourceImgId+'" target="_blank">'+a.fileName+
	                                 '</a>';
							return html;
						}else{
							return "";
						}
					}
				},{
					targets : [5],
					mRender : function(a, b, c, d) {
						var html='<a href="#" onclick="clickWindow({id:\''+a.id+'\',checkPeople:\''+a.checkPeople+'\',checkTime:\''+a.checkTime+'\',checkType:\''+a.checkType+'\',checkTypeValue:\''+a.checkTypeValue+'\'})" class="btn btn-success btn-xs">'+
                                    '<span class="bold">编辑</span>'+
                                 '</a>';
						return html;
					}
				},{
					"defaultContent": "-",
				    "targets": "_all"
				} ]
			});
}

function clean(){
	$('#id').attr("value",'');
	$('#checkPeople').attr("value",'');
	$('#checkTime').attr("value",'');
	$('#checkTypeValue').attr("value",'');
}

function initValue(data){
	$('#id').attr("value",data.id);
	$('#checkPeople').attr("value",data.checkPeople);
	$('#checkTime').attr("value",data.checkTime);
	$('#checkTypeValue').val(data.checkTypeValue);
	$("#checkTypeValue option[value='"+data.checkTypeValue+"']").attr("selected", true);
}

function openForm(data){
	clean();
}

function clickWindow(data){
	$("#addArchi").click();
	initValue(data);
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

function changeVip(){
	var vip=$("#changeVip").is(":checked");
	var num=$("#num").val();
	var str="";
	if(vip){
		str="确定修改为VIP?";
	}else{
		str="确定取消VIP?";
	}
	if(confirm(str)){
		var paremeter=new Object();
		paremeter.num=num;
		paremeter.vip=vip;
		$.ajax({
			type: "post",
			url: "/updateMachineVip",
			dataType:"json",
			headers:{"Accept":"application/json",
					"Content-Type":"application/json"},
			data: JSON.stringify(paremeter),
			success: function(data, textStatus){
				alert("更新成功！");
			},
			error: function(){
				//请求出错处理
			}
		});
	}
}


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