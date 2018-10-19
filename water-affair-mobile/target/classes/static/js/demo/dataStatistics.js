$(function() {
	var table = $('#meterChangeList').dataTable();
//	if (table) {
//		table.fnDestroy();
//	}
//	findMktList("/queryWaterByConditions",{});
	 $("#query1").bind("click", onsubmit1);
	 
	//显示隐藏列
     $('.toggle-vis').on('change', function (e) {
         e.preventDefault();
         console.log($(this).attr('data-column'));
         var iCol=$(this).attr('data-column');
         fnShowHide(iCol);
     });
     $('.showcol').click(function () {
         $('.showul').toggle();

     })
})
/**
 * 点击按钮进行隐藏
 * @param iCol
 */
function fnShowHide( iCol)
{
	var table = $('#meterChangeList').dataTable();
    var bVis = table.fnSettings().aoColumns[iCol].bVisible;
    table.fnSetColumnVis( iCol, bVis ? false : true );
}
/**
 * 查询数据后自动隐藏
 */
function datatableShowHide(iCol){
	var bool=false;
	$('.toggle-vis').each(function(a,b){
		console.log($(b).attr('data-column'));
		console.log($(b).prop('checked'));
		var index=$(b).attr('data-column');
		if(index==iCol){
			var isHide=$(b).prop('checked');
			if(isHide){
				bool = false;
			}else{
				bool = true;
			}
			return false;
		}
	});
	return bool;
}

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
	findMktList("/queryWaterByConditions",{'startTime':startTime,'endTime':endTime,'staions':staions});
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
					"mData" : "shortName"
				}, {
					"mData" : null
				}, {
					"mData" : null,"bVisible": datatableShowHide(2)
				}, {
					"mData" : null,"bVisible": datatableShowHide(3)
				}, {
					"mData" : "totalflow","bVisible": datatableShowHide(4)
				}, {
					"mData" : "fTotalflow","bVisible": datatableShowHide(5)
				}, {
					"mData" : "avgFlow","bVisible": datatableShowHide(6)
				}, {
					"mData" : "ntotalflow","bVisible": datatableShowHide(7)
				}, {
					"mData" : null,"bVisible": datatableShowHide(8)
				}, {
					"mData" : null,"bVisible": datatableShowHide(9)
				}, {
					"mData" : "avgPress","bVisible": datatableShowHide(10)
				}  ],
				aoColumnDefs : [ {
					targets : [1],
					mRender : function(a, b, c, d) {
						var html='<a href=" " data-toggle="modal" data-target=".chart-trend">'+
                            '<i class="fa fa-bar-chart-o"></i>'+
                            '</a>';
						return html;
					}
				}, {
					targets : [2],
					mRender : function(a, b, c, d) {
						if(a.maxFlow.length==0){
							return "";
						}
						var html = new Date(a.maxFlowTime);
						var time= html.getFullYear()+'-'+(html.getMonth()+1)+'-'+html.getDate()+' '+html.getHours()+':'+html.getMinutes()+':'+html.getSeconds();
						var html=a.maxFlow+'<a href=" " class="td-icon popover-parent popover-time">'+
							'<i class="fa fa-clock-o"></i>'+
							'<div class="popover top">'+
							'<div class="arrow" style="left: 50%;"></div>'+
							'<h3 class="popover-title" style="display: none;"></h3>'+
							'<div class="popover-content">'+
							'    <span>发生时间：</span>'+
							'    <span>'+time+'</span>'+
							'</div>'+
							'</div>'+
							'</a >';
						return html;
					}
				},{
					targets : [3],
					mRender : function(a, b, c, d) {
						if(a.minFlow.length==0){
							return "";
						}
						var html = new Date(a.minFlowTime);
						var time= html.getFullYear()+'-'+(html.getMonth()+1)+'-'+html.getDate()+' '+html.getHours()+':'+html.getMinutes()+':'+html.getSeconds();
						var html=a.minFlow+'<a href=" " class="td-icon popover-parent popover-time">'+
							'<i class="fa fa-clock-o"></i>'+
							'<div class="popover top">'+
							'<div class="arrow" style="left: 50%;"></div>'+
							'<h3 class="popover-title" style="display: none;"></h3>'+
							'<div class="popover-content">'+
							'    <span>发生时间：</span>'+
							'    <span>'+time+'</span>'+
							'</div>'+
							'</div>'+
							'</a >';
						return html;
					}
				},{
					targets : [8],
					mRender : function(a, b, c, d) {
						if(a.maxPress.length==0){
							return "";
						}
						var html = new Date(a.maxPressTime);
						var time= html.getFullYear()+'-'+(html.getMonth()+1)+'-'+html.getDate()+' '+html.getHours()+':'+html.getMinutes()+':'+html.getSeconds();
						var html=a.maxPress+'<a href=" " class="td-icon popover-parent popover-time">'+
							'<i class="fa fa-clock-o"></i>'+
							'<div class="popover top">'+
							'<div class="arrow" style="left: 50%;"></div>'+
							'<h3 class="popover-title" style="display: none;"></h3>'+
							'<div class="popover-content">'+
							'    <span>发生时间：</span>'+
							'    <span>'+time+'</span>'+
							'</div>'+
							'</div>'+
							'</a >';
						return html;
					}
				},{
					targets : [9],
					mRender : function(a, b, c, d) {
						if(a.minPress.length==0){
							return "";
						}
						var html = new Date(a.minPressTime);
						var time= html.getFullYear()+'-'+(html.getMonth()+1)+'-'+html.getDate()+' '+html.getHours()+':'+html.getMinutes()+':'+html.getSeconds();
						var html=a.minPress+'<a href=" " class="td-icon popover-parent popover-time">'+
							'<i class="fa fa-clock-o"></i>'+
							'<div class="popover top">'+
							'<div class="arrow" style="left: 50%;"></div>'+
							'<h3 class="popover-title" style="display: none;"></h3>'+
							'<div class="popover-content">'+
							'    <span>发生时间：</span>'+
							'    <span>'+time+'</span>'+
							'</div>'+
							'</div>'+
							'</a >';
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
