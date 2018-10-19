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
	var stations = $('#stations').val();
	if (stations != null) {
		$('#meterChangeList').dataTable().fnDestroy(); 
		stations=stations.join(",");
		findMktList("/findReplaceMeterRecord",{'startTime':startTime,'endTime':endTime,'stations':stations});
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
					"mData" : "checkPeople"
				}, {
					"mData" : "num"
				}, {
					"mData" : "shortName"
				}, {
					"mData" : "checkTime"
				} ],
				aoColumnDefs : [ {
					targets : [4],
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
