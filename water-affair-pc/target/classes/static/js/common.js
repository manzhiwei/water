/**
 * 自定义 js
 * Created by wuchong on 2016/12/12.
 */

$(document).ready(function () {
    // 滚动条
    var slimScrollConfig = {
        '.slimScroll-default': {
            height: '100%',
            color: '#666',
            size: '6px',
            ailOpacity: 0.9,
            alwaysVisible: false
        }
    };
    for (var slimScroll in slimScrollConfig) {
        $(slimScroll).slimScroll(slimScrollConfig[slimScroll]);
    }

    // chosen-select
    var config = {
        '.chosen-select-normal': {
            width:"100%",
            no_results_text:'暂无选项'
        },
        '.chosen-select-max6': {
            width:"100%",
            no_results_text:'暂无选项',
            max_selected_options: 6
        }
    };
    for (var selector in config) {
        $(selector).chosen(config[selector]);
    }
    // 文件上传
    // $(".file-pretty input[type='file']" ).prettyFile();

    // 分页表格
    var dataTableLanguage = {
        "sProcessing": "处理中...",
            "sLengthMenu": "显示 _MENU_ 项结果",
            "sZeroRecords": "没有匹配结果",
            "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
            "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
            "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
            "sInfoPostFix": "",
            "sSearch": "搜索:",
            "sUrl": "",
            "sEmptyTable": "表中数据为空",
            "sLoadingRecords": "载入中...",
            "sInfoThousands": ",",
            "oPaginate": {
            "sFirst": "首页",
                "sPrevious": "上页",
                "sNext": "下页",
                "sLast": "末页"
        },
        "oAria": {
            "sSortAscending": ": 以升序排列此列",
                "sSortDescending": ": 以降序排列此列"
        }
    };
    var dataTableConfig = {
        '.dataTables-default': {
            language:dataTableLanguage,
            buttons: ['excel'],
            lengthChange: false, // 是否允许用户定义每页的显示条目
            searching: false,
            ordering: false
        },
        '.dataTables-meter': {
            dom: '<"excelButtons"B>lTfgt<"pages"p>i',
            language:dataTableLanguage,
            buttons: ['excel'],
            lengthChange: false, // 是否允许用户定义每页的显示条目
            searching: false,
            ordering: false
        },
        '.dataTables-excel': {
            dom: '<"excelButtons"B>lTfgt<"pages"p>i',
            language:dataTableLanguage,
            buttons: ['excel'],
            lengthChange: false, // 是否允许用户定义每页的显示条目
            searching: false,
            ordering: false
        },
        '.dataTables-scrollX': {
            dom: '<"excelButtons"B>lTfgt<"pages"p>i',
            language:dataTableLanguage,
            buttons: ['excel'],
            lengthChange: false, // 是否允许用户定义每页的显示条目
            searching: false,
            ordering: false,
            scrollX: true
        }
    };
    for (var dataTable in dataTableConfig) {
        $(dataTable).DataTable(dataTableConfig[dataTable]);
    }

});

function checknum(value) {
	if (value==null||value==""||isNaN(value)) {
		return false;
	}else{
		return true;
	}
}

/**
 * 格式化数值保留2位小数
 * @param value
 * @returns {___anonymous2976_2980}
 */
function returnFloat(value){
	if(!checknum(value)){
		return value;
	}
	 var value=Math.round(parseFloat(value)*100)/100;
	 var xsd=value.toString().split(".");
	 if(xsd.length==1){
	 value=value.toString()+".00";
	 return value;
	 }
	 if(xsd.length>1){
	 if(xsd[1].length<2){
	  value=value.toString()+"0";
	 }
	 return value;
	 }
}