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
    //$('#undo_redo').multiselect();

    var currentDmaId='';

    //去除选中DMA区域已有的水表
    $('#removeOne4InMeter').click(function(){
        //拼接选中的水表id
        var selectedMeterIdList='';
        var selected = $("#from option:selected");
        for(i=0;i<selected.length;i++){
           var item = selected.get(i);
           selectedMeterIdList=selectedMeterIdList+item.value+",";
        }

        console.log(currentDmaId);

        //向后台请求更新dma水表关系
        $.ajax({
          url: '/removeInMeter4DMA?did='+currentDmaId+'&mids='+selectedMeterIdList,
          type:"POST",
          dataType: "json",
          async: false
        }).done(function(dmaList, textStatus){
             $("#from option:selected").clone().appendTo("#to");
             $("#from option:selected").clone().appendTo("#to2");
             $("#from option:selected").remove();
        }).fail(function(){
          console.log("出错啦！");
        });
    });

    //增加选中DMA区域的进水表
    $('#addOne4InMeter').click(function(){
        //拼接选中的水表id
        var selectedMeterIdList='';
        var selected = $("#to option:selected");
        for(i=0;i<selected.length;i++){
           var item = selected.get(i);
           selectedMeterIdList=selectedMeterIdList+item.value+",";
        }
        //向后台请求更新dma水表关系
        $.ajax({
          url: '/addInMeter4DMA?did='+currentDmaId+'&mids='+selectedMeterIdList,
          type:"POST",
          dataType: "json",
          async: false
        }).done(function(dmaList, textStatus){
              $("#to option:selected").clone().appendTo("#from");
              var selVal = $("#to option:selected").val();
              $("#to option:selected").remove();
              $("#to2 option[value="+selVal+"]").remove();
        }).fail(function(){
          console.log("出错啦！");
        });

    });

  //去除选中DMA区域已有的水表
    $('#removeOne4OutMeter').click(function(){
        //拼接选中的水表id
        var selectedMeterIdList='';
        var selected = $("#from2 option:selected");
        for(i=0;i<selected.length;i++){
           var item = selected.get(i);
           selectedMeterIdList=selectedMeterIdList+item.value+",";
        }

        console.log(currentDmaId);

        //向后台请求更新dma水表关系
        $.ajax({
          url: '/removeOutMeter4DMA?did='+currentDmaId+'&mids='+selectedMeterIdList,
          type:"POST",
          dataType: "json",
          async: false
        }).done(function(dmaList, textStatus){
        	 $("#from2 option:selected").clone().appendTo("#to");
             $("#from2 option:selected").clone().appendTo("#to2");
             $("#from2 option:selected").remove();
        }).fail(function(){
          console.log("出错啦！");
        });
    });

    //增加选中DMA区域的进水表
    $('#addOne4OutMeter').click(function(){
        //拼接选中的水表id
        var selectedMeterIdList='';
        var selected = $("#to2 option:selected");
        for(i=0;i<selected.length;i++){
           var item = selected.get(i);
           selectedMeterIdList=selectedMeterIdList+item.value+",";
        }
        //向后台请求更新dma水表关系
        $.ajax({
          url: '/addOutMeter4DMA?did='+currentDmaId+'&mids='+selectedMeterIdList,
          type:"POST",
          dataType: "json",
          async: false
        }).done(function(dmaList, textStatus){
              $("#to2 option:selected").clone().appendTo("#from2");
              var selVal = $("#to2 option:selected").val();
              $("#to2 option:selected").remove();
              $("#to option[value="+selVal+"]").remove();
        }).fail(function(){
          console.log("出错啦！");
        });

    });
    

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

                var menu = {
                    "rename": {
                      "separator_before" : false,
                      "separator_after"  : true,
                      "_disabled"          : false,
                      "label"             : "重命名",
                      "action": function (data) {
                          var inst = $.jstree.reference(data.reference),
                          obj = inst.get_node(data.reference);
                          inst.edit(obj);
                      }
                    },
                    "remove": {
                      "separator_before" : false,
                      "separator_after"  : false,
                      "_disabled"          : false,
                      "label"             : "删除",
                      "action": function (data) {
                            var inst = $.jstree.reference(data.reference),
                            obj = inst.get_node(data.reference);
                            $.ajax({
                              url: '/removeDMA4Tree',
                              data:{id:obj.original.id},
                              type:"POST",
                              dataType: "json",
                              async: false
                            }).done(function(res, textStatus){
                                  if(res.result){
                                      $("#from option").remove();
                                      $("#to option").remove();
                                      dmaList.jstree(true).settings.core.data=getData('/getDmaList');
                                      dmaList.jstree(true).refresh();
                                  }
                                  selectedDmaId=0;
                            }).fail(function(){
                                  console.log("出错啦！");
                            });
                      }
                    }
              };

                return menu;
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
                $("#from option").remove();
                $("#to option").remove();
                $("#from2 option").remove();
                $("#to2 option").remove();
                //请求结果按照类型分配到已选择和未选择区域
                $.each(dmaList, function(i, dma){
                     var value=dma.mid;
                     var text=dma.text;
                     if(dma.selected){
                    	 $("#from").append("<option value='"+value+"'>"+text+"</option>");
                     } else if(dma.selected2){
                    	 $("#from2").append("<option value='"+value+"'>"+text+"</option>");
                     } else{
                        $("#to").append("<option value='"+value+"'>"+text+"</option>");
                        $("#to2").append("<option value='"+value+"'>"+text+"</option>");
                     }
                 });

                 currentDmaId=selectedDmaId;

            }).fail(function(){
              console.log("变更出错啦！");
            });
        }catch (e)
        {
            console.log("错误");
        }

    }).bind("rename_node.jstree", function (e, data) { // 创建和重命名触发
          var parentNode=data.instance.get_node(data.node.parent);
          var selectedNode=data.instance.get_node(data.node.id);
          console.log(selectedNode.original.id);

          if(selectedNode.original.id)
          {
                $.ajax({
                  url: '/renameDMA4Tree',
                  data:{id:selectedNode.original.id,name:data.node.text},
                  type:"POST",
                  dataType: "json",
                  async: false
                }).done(function(res, textStatus){
                    toastr.success('名称更新成功！','');

                    dmaList.jstree(true).settings.core.data=getData('/getDmaList');
                    dmaList.jstree(true).refresh();

                }).fail(function(){
                    toastr.error('名称更新失败！','')
                });
          }

    });

});