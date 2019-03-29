$(function() {
	var table = $('#changeIPAndPort').dataTable();
	if (table) {
		table.fnDestroy();
	}
	 $("#query1").bind("click", onsubmit);
	 onsubmit();
})

function onsubmit(){
        var value = $("#search-content").val();

        console.log(value);

        if(value != null){
            $('#changeIPAndPort').dataTable().fnDestroy();
            findMktList("/queryIPAndPortByMeterName",{"shortName":value});
        }
}

function findMktList(url,data){
      console.log(data);
     $("#changeIPAndPort").dataTable({
            "paging":true,
            "bPaginate":true,
            "searching":false,
            "bServerSide":false,
            "serverSide":true,
            "lengthChange":true,
            "bInfo": true,//页脚信息
            "sScrollY": "300px",
            "ajax" : {
                url : url,
                dataType : "json",
                type : "post",
                data:data,
                dataSrc : "entity"
            },
            aoColumns : [ {
                "mData" : "shortName"
            },{
                "mData" : "rIp"
            }, {
                "mData" : "rIpport"
            }, {
                "mData" : "ip"
            }, {
                "mData" : "ipPort"
            },{
                "mData" : null
            },{
                "mData" : "iTime"
            } ],
            aoColumnDefs : [
            {
                targets : [5],
                mRender : function(result) {

                   var html ='<button class="btn btn-primary" data-toggle="modal" data-mid="' + result.shortName + ","+ result.num +'" data-target="#myModal">写入</button>';
                   /* var html='<a href="#" onclick="insertDisplayBoard(\'/writeDisplayBoard\',{num:\''+ result.num +
                    '\'})" class="btn btn-success btn-xs">'+ '<span class="bold">写入</span>'+'</a>';*/
                    return html;
                }
            },
            {
                targets : [6],
                mRender : function(result) {
                    var html = new Date(result);
                    return html.getFullYear()+'-'+(html.getMonth()+1)+'-'+html.getDate()+' '+html.getHours()+':'+html.getMinutes()+':'+html.getSeconds();
                }
            },{
                "defaultContent": "-",
                "targets": "_all"
            }]
     });


};



/*function writeDisplayBoard(url,data){
    $('#changeIPAndPort').dataTable().fnDestroy();
    findMktList(url,data);
};*/

//点击查看详情
$('#myModal').on('show.bs.modal', function (event) {
    var detail = $(event.relatedTarget);
    cleanDetailInfo();
    var arr= detail.data('mid').split(",");
    var shortName = arr[0];
    var num =  arr[1];
   /* console.log(shortName);
    console.log(num);*/
    createDetailInfo({"shortName":shortName,"num":num})

    document.getElementById("model_title").value="修改站点：" + shortName + "的IP和端口";
});


function cleanDetailInfo(){
	$(".detail-content").children().remove();
};

function createDetailInfo(data){
	var content='<form id="myform">'
	            +'<div class="form-group">'
                        +'<label for="num">内部编号</label>'
                        +'<input type="hidden" readonly  class="form-control" id="num" name ="num" value ="'+ data.num +'" >'
                    +'</div>'
	            +'<div class="form-group">'
                        +'<label for="shortName">仪表名称</label>'
                        +'<input type="text" readonly  class="form-control" id="shortName" name ="shortName" value ="'+ data.shortName+'" >'
                    +'</div>'
	            + '<div class="form-group"><label for="ip">IP</label>'
                    +'<input type="text"  class="form-control" id="ip" name= "ip" aria-describedby="ipHelp" placeholder="Enter IP：106.14.4.240">'
                    +'<small id="ipHelp" class="form-text text-muted">修改IP和端口成功以后，下一次连接将指向新的IP和端口</small>'
                +'</div>'
                +'<div class="form-group">'
                    +'<label for="port">Port</label>'
                    +'<input type="text"  class="form-control" id="port" name = "port" placeholder="端口:4632">'
                +'</div>'
                +' <button type="button" class="btn btn-primary" id="button1" onclick="updateIP()">提交</button>'
                +' </form>';
	$(".detail-content").append(content);//追加content
};
$("#button1").on("click",function(){
    $.ajax({
        type:"post",
        url:"/updateIpAndPort",
        data:$("#myform").serialize(),
        async:false,
        success:function(data){
            alert(data);
        }
    });
    // $("#myModal").modal('hide');
});
function updateIP(){
         $.ajax({
                type:"post",
                url:"/updateIpAndPort",
                data:$("#myform").serialize(),
                async:false,
                success:function(data){
                    alert(data);
                }
            });
             $("#myModal").modal('hide');
}

/*//验证输入的IP地址的正确性
function formValidate(){
        var ip = $("#ip").val();
        console.log(ip);
        var reg = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
        var result = ip.match(reg);
        if(result==null){
            return false;
        }
        var port = $("#port").val();
        console.log(port);
        return true;
}*/
/*$("form").on("submit", function(event) {
  event.preventDefault();
});*/
 function getMyDate(time){

    var oDate = new Date(time),
        oYear = oDate.getFullYear(),
        oMonth = oDate.getMonth()+1,
        oDay = oDate.getDate(),
        oHour = oDate.getHours(),
        oMin = oDate.getMinutes(),
        oSen = oDate.getSeconds(),
        oTime = oYear +'-'+ getzf(oMonth) +'-'+ getzf(oDay) +' '+ getzf(oHour) +':'+ getzf(oMin) +':'+getzf(oSen);//最后拼接时间
    return oTime;
  };
