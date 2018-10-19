
$(document).ready(function(){




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

            $('.i-checks').iCheck({
                checkboxClass: 'icheckbox_square-blue',
                radioClass: 'iradio_square-blue'
            });

            //某个选中公司或分公司的仪表列表
            var companyMeterList = '';
            //所有仪表列表
            var allMeterList = '';
            //当前选中公司
            var selectedCompanyId='';

            var companyListTree=$('.companyListTree').jstree({
                'core' : {
                    "check_callback" : true,
                    "multiple" : true,
                    'data' :  getData('/meterList4AlarmMultiSetting'),
                    'strings': {
                        'Loading ...' : '加载中 ...'
                    },
                    'dblclick_toggle': false
                },
                'plugins' : [ 'types', "contextmenu", "checkbox" ],
                 "checkbox" : {
                   "keep_selected_style" : false
                 },
                'types' : {
                    "default" : {
                        "icon" : "fa fa-university"
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
                var meterList="";
                //获取最终被选中的水表
                $.each(data.instance.get_selected(true), function(index, child){

                    var selectedNode=data.instance.get_node(child.id);
                    if(data.instance.is_leaf(selectedNode)){
                        if(selectedNode.original.mid){

                            meterList=meterList+','+selectedNode.original.mid;
                        }
                     }
                });
                console.log(meterList);
                document.getElementById("meterList").value=meterList;
            });



            $('.btn-add-company').on('click', function () {
                //获取页面上的值
                 console.log('提交分公司'+$("#current_hhvalue").val()+"--------");
                 var data = '';
                 $.ajax({
                    url: '/updateAlarmMultiSetting',
                    data:{current_hhvalue:$("#current_hhvalue").val(),current_hhlevel:$("#current_hhlevel").val(),
                          current_hmvalue:$("#current_hmvalue").val(),current_hmlevel:$("#current_hmlevel").val(),
                          current_hlvalue:$("#current_hlvalue").val(),current_hllevel:$("#current_hllevel").val(),
                          current_lhvalue:$("#current_lhvalue").val(),current_lhlevel:$("#current_lhlevel").val(),
                          current_lmvalue:$("#current_lmvalue").val(),current_lmlevel:$("#current_lmlevel").val(),
                          current_llvalue:$("#current_llvalue").val(),current_lllevel:$("#current_lllevel").val(),
                          type:$("#paraType").val(),alarmType:$("#alarmType").val(),meterList:document.getElementById("meterList").value},
                    type:"POST",
                    dataType: "json",
                    async: false
                 }).done(function(res, textStatus){
                    console.log(res);
                     if(res){
                        toastr.success('配置更新成功','');
                     }else{
                        toastr.error('配置更新失败','');
                     }
                 }).fail(function(){
                    console.log("出错啦！");
                 });
            });

        });