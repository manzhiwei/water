$(function() {
	var table = $('#meterChangeList').dataTable();
	if (table) {
		table.fnDestroy();
	}
	 $("#query1").bind("click", onsubmit1);
})

function onsubmit1(){
	var startTime=$('#startTime').val();
	var endTime=$('#endTime').val();
	var staions=$('#staions').val();
	if(staions!=null){
		staions=staions.join(",");
	}else{
		alert("请选择站点!");
		return ;
	}
	$('#meterChangeList').dataTable().fnDestroy(); 
	findMktList("/queryMeterChangeRecordByCondition",{'startTime':startTime,'endTime':endTime,'staions':staions});
}

/**
 * 请求仪表数据
 * 
 * @param parameterObj
 * @param url
 */
function findMktList(url,data) {
	$('#meterChangeList').DataTable(
			{
				"autoWidth": false,
				"searching":false,
				"ajax" : {
					url : url,
					dataType : "json",
					type : "post",
					data:data,
					dataSrc: "entity"
				},
				aoColumns : [ {
					"mData" : "id"
				}, {
					"mData" : "newShortName"
				}, {
					"mData" : "newNum"
				}, {
					"mData" : "oldNum"
				}, {
					"mData" : "createTime"
				}, {
					"mData" : "createUser"
				} ],
				aoColumnDefs : [ {
					targets : [2],
					mRender : function(a, b, c, d) {
						var html='<a href="#" onclick="onPost(\'/meterListDetail\',{num:\''+a+'\'})">'+
                                    a+
                                 '</a>';
						return html;
					}
				}, {
					targets : [3],
					mRender : function(a, b, c, d) {
						var html='<a href="#" onclick="onPost(\'/meterListDetail\',{num:\''+a+'\'})">'+
                                    a+
                                 '</a>';
						return html;
					}
				},{
					targets : [4],
					mRender : function(a, b, c, d) {
						var html = new Date(a);
						
						return html.getFullYear()+'-'+(html.getMonth()+1)+'-'+html.getDate();
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
