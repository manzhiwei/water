$(function(){
    var table = $('#meterListTable').dataTable();

   if (table) {
   		table.fnDestroy();
   	}
   	findMktList("/queryTodayInfo",null);
})

function format ( d ) {
    /*return 'Full name: '+d.first_name+' '+d.last_name+'<br>'+
        'Salary: '+d.salary+'<br>'+
        'The child row can contain any data you wish, including links, images, inner tables etc.';*/
        return "反向累计: "+ d.ftotalflow + '<br>'+
        '内部编号: ' +d.num + '<br>'+
        '信号强度: '+d.signal_strength + '<br>'+
        '电池电量: '+d.me+'<br>';
}

/**
 * 请求仪表数据
 *
 * @param parameterObj
 * @param url
 */
function findMktList(url,data) {
	 var dt = $('#meterListTable').DataTable(
        {
            dom: '<"html5buttons"B>lTfgtip',
//             dom: '<"excelButtons"B>lTfgt<"pages"p>i',
            "autoWidth" : false,
            "searching":true,
            "language": {
                "search": "过滤：",
                "lengthMenu": "每页 _MENU_ 条记录",
                "loadingRecords": "请等待，数据正在加载中......",
                "zeroRecords": "没有找到记录",
                "info": "从 _START_ 到  _END_ 条记录 总记录数为 _TOTAL_ 条，第 _PAGE_ 页 ( 总共 _PAGES_ 页 )",
                "infoEmpty": "没有数据",
                "infoFiltered": "(从 _MAX_ 条数据中检索)",
           },
            buttons: [
                { extend: 'copy'},
                {extend: 'csv'},
                {extend: 'excel', title: '仪表实时数据信息',
                exportOptions: {
                     modifier: {
                         page:[0,1,2,3,4,5,6,7,8]
                     }
                 }},
                /*{extend: 'pdf', title: '仪表实时数据信息',
                exportOptions: {
                     modifier: {
                         page:[0,1,2,3,4,5,6,7,8]
                     }
                 }},*/
                {extend: 'print',
                 customize: function (win){
                        $(win.document.body).addClass('white-bg');
                        $(win.document.body).css('font-size', '10px');

                        $(win.document.body).find('table')
                                .addClass('compact')
                                .css('font-size', 'inherit');
                }
                }
            ],
            "ajax" : {
                url : url,
                type : "post",
                dataType : "json",
                data : data,
                dataSrc : "entity"
            },
            "order": [[2, 'asc']],
            aoColumns : [
                        {
                            "class":          "details-control",
                            "orderable":      false,
                            "data":           null,
                            "defaultContent": ""
                        }, /*{          "mData" : "num" },*/
                        {
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
                        },{
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
                         var html = '<a href="#" onclick="onPost(\'/dataStatisticsHourByIdAndName\',{name:\''
                                + result.subUserName
                                + '\'})" class="btn btn-success btn-xs">'
                                + '<span class="bold">历史数据</span>'
                                + '</a>';
                        return html;
                     }
                  },
                    {
                        "defaultContent" : "-",
                        "targets" : "_all"
                    } ]
        });
         // Array to track the ids of the details displayed rows
            var detailRows = [];

            $('#meterListTable tbody').on( 'click', 'tr td.details-control', function () {
                var tr = $(this).closest('tr');
                var row = dt.row( tr );
                var idx = $.inArray( tr.attr('id'), detailRows );

                if ( row.child.isShown() ) {
                    tr.removeClass( 'details' );
                    row.child.hide();

                    // Remove from the 'open' array
                    detailRows.splice( idx, 1 );
                }
                else {
                    tr.addClass( 'details' );
                    row.child( format( row.data() ) ).show(); // here the function is used

                    // Add to the 'open' array
                    if ( idx === -1 ) {
                        detailRows.push( tr.attr('id') );
                    }
                }
            } );

            // On each draw, loop over the `detailRows` array and show any child rows
            dt.on( 'draw', function () {
                $.each( detailRows, function ( i, id ) {
                    $('#'+id+' td.details-control').trigger( 'click' );
                } );
            } );
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
