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
					}
				}
			},
			longitude : {
				message : '经度验证失败',
				validators : {
					numeric : {
						message : '口径只能输入double'
					}
				}
			},
			latitude : {
				message : '维度验证失败',
				validators : {
					numeric : {
						message : '口径只能输入double'
					}
				}
			}
		},
		submitHandler : function(validator, form, submitButton) {
			validator.defaultSubmit();
		}
	});
	var bootstrapValidator = $('#commentForm').data('bootstrapValidator');
	bootstrapValidator.on('success.form.bv', function (e) {
	    e.preventDefault();
	    //提交逻辑
	    submitMeter();
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
	$("#commentForm").submit();
}

/**
 * 请求仪表数据
 * 
 * @param parameterObj
 * @param url
 */
function findMktList() {
	$('#meterListTable')
			.DataTable(
					{
						"autoWidth" : false,
						"searching":false,
						"ajax" : {
							url : "/queryMeterInfo",
							type : "post",
							dataSrc : "entity"
						},
						aoColumns : [ {
							"mData" : "num"
						}, {
							"mData" : "company"
						}, {
							"mData" : "metertype"
						}, {
							"mData" : "meterSizeStr"
						}, {
							"mData" : "subUserName"
						}, {
							"mData" : "shortName"
						}, {
							"mData" : null
						}, {
							"mData" : null
						} ],
						aoColumnDefs : [
								{
									targets : [ 1 ],
									mRender : function(a, b, c, d) {
										return a;
									}
								},{
									targets : [ 6 ],
									mRender : function(a, b, c, d) {
										if (a.isVipAccount == 1) {
											return "是";
										} else {
											return "否";
										}
									}
								},
								{
									targets : [ 7 ],
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
