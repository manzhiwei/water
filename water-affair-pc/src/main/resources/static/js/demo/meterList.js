$(function() {
	var table = $('#meterListTable').dataTable();
	if (table) {
		table.fnDestroy();
	}
	findMktList();
	$("#commentForm").bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},

		fields : {
			num : {
				message : '仪表编号验证失败',
				validators : {
					notEmpty : {
						message : '仪表编号不能为空'
					},
					numeric : {
						message : '仪表编号只能输入数字'
					},
					remote: {  
                        url: '/isExists',  
                        message:"仪表编号已存在",  
                        type: "get",
                        dataType: 'json',  
                        data: {  
                              
                        },  
                        delay: 500
                    }
				}
			},
			meterSize : {
				message : '口径验证失败',
				validators : {
					numeric : {
						message : '口径只能输入数字'
					},
					between : {
						message : '口径必须0~21',
						min : 0,
						max : 21
					}
				}
			},
			longitude : {
				message : '经度验证失败',
				validators : {
					numeric : {
						message : '经度只能输入double'
					}
				}
			},
			latitude : {
				message : '维度验证失败',
				validators : {
					numeric : {
						message : '维度只能输入double'
					}
				}
			}
		},
		submitHandler : function(validator, form, submitButton) {
//			validator.defaultSubmit();
			submitMeter();
		}
	}).on("success.form.bv",function(e){
		// 版本号0.4.5支持
		// 版本号v0.5.2-dev支持
		submitMeter();
		}); 
	
	///////////
	 $('.layer-date').click(function(){
	        laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss', choose: function(dates){
	            console.log(dates);
	        }})
	    });
})

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
//	$("#commentForm").submit();
}
function format ( d ) {
    /*return 'Full name: '+d.first_name+' '+d.last_name+'<br>'+
        'Salary: '+d.salary+'<br>'+
        'The child row can contain any data you wish, including links, images, inner tables etc.';*/
        return '内部编号: ' +d.num ;
}
/**
 * 请求仪表数据
 * 
 * @param parameterObj
 * @param url
 */
function findMktList(parameter) {
	var columns=[
	{
        "class":          "details-control",
        "orderable":      false,
        "data":           null,
        "defaultContent": ""
    },{
        "mData" : "shortName"
    }, {
      	"mData" : "instrNo"
    },{
        "mData" : "ccid"
    },{
		"mData" : "metertype"
	}, {
		"mData" : "meterSizeStr"
	}, {
		"mData" : "address"
	}, {
		"mData" : null
	}, {
		"mData" : null
	} ];
	if(isAdmin){
		columns=[ {
          "class":          "details-control",
          "orderable":      false,
          "data":           null,
          "defaultContent": ""
        },{
            "mData" : "subUserName"
        },{
           	"mData" : "instrNo"
        },{
            "mData" : "ccid"
        },{
            "mData" : "company"
        },{
            "mData" : "metertype"
		}, {
			"mData" : "meterSizeStr"
		}, {
			"mData" : "address"
		}, {
			"mData" : null
		}, {
			"mData" : null
		} ];
	}
	var dt = $('#meterListTable').DataTable(
	                {
					    dom: '<"html5buttons"B>lTfgtip',
//					    dom: 'Blfrtip',
						"autoWidth" : false,
						"searching":true,
						"ajax" : {
                            url : "/queryMeterInfo",
                            type : "post",
                            dataType : "json",
                            data : parameter,
                            dataSrc : "entity"
                        },
                      "order": [[2, 'asc']],
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
                                     {
                                         extend: 'excel',
                                         title:'仪表实时数据信息',
                                         exportOptions: {
                                             modifier: {
                                                 page:[0,1,2,3,4,5,6,7,8]
                                             }
                                         }
                                     },
                                     {
                                          extend: 'print',
                                          title:'仪表实时数据信息',
                                          exportOptions: {
                                              modifier: {
                                                  page:[0,1,2,3,4,5,6,7,8]
                                              }
                                          }
                                      },
                                      {
                                           extend: 'copy',
                                           title:'仪表实时数据信息',
                                           exportOptions: {
                                               modifier: {
                                                   page:[0,1,2,3,4,5,6,7,8]
                                               }
                                           }
                                       }
                                 ],
						aoColumns : columns,
						aoColumnDefs : [
								{
									targets : [ isAdmin?0:1 ],
									mRender : function(a, b, c, d) {
										return a;
									}
								},{
									targets : [ isAdmin?8:7 ],
									mRender : function(a, b, c, d) {
										if (a.isVipAccount == 1) {
											return "是";
										} else {
											return "否";
										}
									}
								},
								{
									targets : [ isAdmin?9:8 ],
									mRender : function(a, b, c, d) {
										var html = '<a href="#" onclick="onPost(\'/meterListDetail\',{num:\''
												+ a.num
												+ '\'})" class="btn btn-success btn-xs">'
												+ '<span class="bold">查看详情</span>'
												+ '</a>';
										return html;
									}
								}, {
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


//查询某个站点
function show(){
    var obj = document.getElementById("search-content1");
    var value = obj.value;
    console.log(value);
	$('#meterListTable').dataTable().fnDestroy(); 
    findMktList({"subUserName":value,"flag":"true"});
};
