$(function(){
    var table = $('#meterListTable').dataTable();

   if (table) {
   		table.fnDestroy();
   	}
   	findMktList("/queryTodayInfo",null);
})

/**
 * 请求仪表数据
 *
 * @param parameterObj
 * @param url
 */
function findMktList(url,data) {
	$('#meterListTable').DataTable(
        {
            "autoWidth" : false,
            "searching":false,
            "ajax" : {
                url : url,
                type : "post",
                dataType : "json",
                data : data,
                dataSrc : "entity"
            },
            aoColumns : [ {
                            "mData" : "num"
                        },{
                            "mData" : "subUserName"
                        },{
                              "mData" : "instrNo"
                        }, {
                            "mData" : "flow"
                        }, {
                            "mData" : "press"
                        }, {
                            "mData" : "totalflow"
                        },{
                            "mData" : "increaseTotalflow"
                        },{
                             "mData" : "increaseTotalflowMonth"
                        },{
                            "mData"  : "i_time"
                        },
                        {
                            "mData" : null
                        } ],
            aoColumnDefs : [
                       {
                            targets : [3],
                            mRender : function(result) {
                                 var html = result.toFixed(2)
                                 return html;
                            }
                       },
                     {
                            targets : [6],
                            mRender : function(result) {
                                 var html = result.toFixed(2)
                                 return html;
                            }
                      },
                     {
                         targets : [7],
                         mRender : function(result) {
                             var html = result.toFixed(2)
                              return html;
                         }
                      },{
                        targets : [8],
                        mRender : function(result) {
                            var html = new Date(result);
                            return html.getFullYear()+'-'+(html.getMonth()+1)+'-'+html.getDate()+' '+html.getHours()+':'+html.getMinutes()+':'+html.getSeconds();
                        }
                     },{
                       targets : [9],
                       mRender : function(result) {
                       var html = '<a href="#" onclick="onPost(\'/todaymeterListDetail\',{num:\''
                               + result.num
                               + '\'})" class="btn btn-success btn-xs">'
                               + '<span class="bold">查看详情</span>'
                               + '</a>';
                       return html;
                       }
                     },
                    {
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
//查询某个站点
function show(){
    var obj = document.getElementById("search-content1");
    var value = obj.value;
    console.log(value);
	$('#meterListTable').dataTable().fnDestroy();
    findMktList("/queryTodayInfoByShortName",{"shortName":value,"flag":true});

};