function getData (url) { // 获取节点数据
    var data = '';
    $.ajax({
        url: url,
        dataType: "json",
        async: false
    }).done(function(res, textStatus){
        data = res;
    }).fail(function(){
        console.log("出错啦！");
    });
    return data;
}
$(document).ready(function() {

    var currentDmaId='';

    var dmaList=$('.dmaListTree').jstree({
        'core' : {
            "check_callback" : true,
            "multiple" : false,
            'data' :  getData('/getDmaList'),
            'strings': {
                'Loading ...' : '加载中 ...'
            },
            'dblclick_toggle': false
        },
        'plugins' : [ 'types', "contextmenu" ],
        'types' : {
            "default" : {
                "icon" : "glyphicon glyphicon-flash"
            },
            "demo" : {
                "icon" : "glyphicon glyphicon-ok"
            }
        },
        'contextmenu': {
            "select_node": false,
            "items": function (node) {

            }
          }
    }).on('changed.jstree', function (e, data) {
        try
        {
            var nodeDepth=data.instance.get_path(data.selected[0]).length;
            var nodeName=data.instance.get_node(data.selected[0]).text;
            var selectedNode=data.instance.get_node(data.selected[0]);
            selectedDmaId=selectedNode.original.id;



            $.ajax({
              url: '/changeDmaArea?id='+selectedDmaId,
              type:"POST",
              dataType: "json",
              async: false
            }).done(function(dmaList, textStatus){



                //请求结果按照类型分配到已选择和未选择区域
                $.each(dmaList, function(i, dma){   
                     var value=dma.mid;
                     var text=dma.text;
                     console.log(dma.text);
                     $("#from").append("<option value='"+value+"'>"+text+"</option>");
                 });

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

                 currentDmaId=selectedDmaId;

            }).fail(function(){
              console.log("变更出错啦！");
            });
        }catch (e)
        {
            console.log("错误");
        }

    });

});