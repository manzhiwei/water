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
					"mData" : "createTime"
				}, {
					"mData" : "common"
				}, {
					"mData" : "ip"
				} ],
				aoColumnDefs : [ {
					targets : [3],
					mRender : function(a, b, c, d) {
						var html = new Date(a);
						return html.getFullYear()+'-'+(html.getMonth()+1)+'-'+html.getDate()+' '+html.getHours()+':'+html.getMinutes()+':'+html.getSeconds();
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
