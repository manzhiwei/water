
$(document).ready(function(){
            function HashMap(){
                                //定义长度
                                var length = 0;
                                //创建一个对象
                                var obj = new Object();

                                /**
                                * 判断Map是否为空
                                */
                                this.isEmpty = function(){
                                    return length == 0;
                                };

                                /**
                                * 判断对象中是否包含给定Key
                                */
                                this.containsKey=function(key){
                                    return (key in obj);
                                };

                                /**
                                * 判断对象中是否包含给定的Value
                                */
                                this.containsValue=function(value){
                                    for(var key in obj){
                                        if(obj[key] == value){
                                            return true;
                                        }
                                    }
                                    return false;
                                };

                                /**
                                *向map中添加数据
                                */
                                this.put=function(key,value){
                                    if(!this.containsKey(key)){
                                        length++;
                                    }
                                    obj[key] = value;
                                };

                                /**
                                * 根据给定的Key获得Value
                                */
                                this.get=function(key){
                                    return this.containsKey(key)?obj[key]:null;
                                };

                                /**
                                * 根据给定的Key删除一个值
                                */
                                this.remove=function(key){
                                    if(this.containsKey(key)&&(delete obj[key])){
                                        length--;
                                    }
                                };

                                /**
                                * 获得Map中的所有Value
                                */
                                this.values=function(){
                                    var _values= new Array();
                                    for(var key in obj){
                                        _values.push(obj[key]);
                                    }
                                    return _values;
                                };

                                /**
                                * 获得Map中的所有Key
                                */
                                this.keySet=function(){
                                    var _keys = new Array();
                                    for(var key in obj){
                                        _keys.push(key);
                                    }
                                    return _keys;
                                };

                                /**
                                * 获得Map的长度
                                */
                                this.size = function(){
                                    return length;
                                };

                                /**
                                * 清空Map
                                */
                                this.clear = function(){
                                    length = 0;
                                    obj = new Object();
                                };
                            };

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

            function postData (url,para) { // 获取节点数据
                            var data = '';
                            $.ajax({
                                url: url,
                                data:{id:para},
                                type:"POST",
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



            //管理总仪表中当前被选中的仪表
            var map = new HashMap();
            //请求所有客户列表（总公司＋分公司）
            var companyList = getData('/clientManageController');
            //某个选中公司或分公司的仪表列表
            var companyMeterList = '';
            //所有仪表列表
            var allMeterList = '';

            //当前选中公司
            var selectedCompanyId='';

            var companyListTree=$('.companyListTree').jstree({
                'core' : {
                    "check_callback" : true,
                    "multiple" : false,
                    'data' :  companyList,
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
                     var menuL0 = {
                        "create": {
                          "separator_before" : false,
                          "separator_after"  : true,
                          "_disabled"          : false,
                          "label"             : "新建总公司",
                          "action": function (data) {
                              var inst = $.jstree.reference(data.reference),
                              obj = inst.get_node(data.reference);
                              inst.create_node(obj, {}, "last", function (new_node) {
                                setTimeout(function () { inst.edit(new_node); },0);
                              });
                          }
                        }
                      };
                     var menuL1 = {
                        "create": {
                          "separator_before" : false,
                          "separator_after"  : true,
                          "_disabled"          : false,
                          "label"             : "新建分公司",
                          "action": function (data) {
                              var inst = $.jstree.reference(data.reference),
                              obj = inst.get_node(data.reference);
                              inst.create_node(obj, {}, "last", function (new_node) {
                                setTimeout(function () {inst.edit(new_node); },0);
                              });
                          }
                        },
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
                              var nodeName=obj.text;
                              console.log(nodeName);
                              inst.delete_node(obj);

                                $.ajax({
                                  url: '/removeCompany4Tree',
                                  data:{id:obj.original.id},
                                  type:"POST",
                                  dataType: "json",
                                  async: false
                                }).done(function(res, textStatus){

                                }).fail(function(){
                                  console.log("出错啦！");
                                });


                              var nodeName=obj.text;
                              console.log(nodeName);
                              inst.delete_node(obj);
                               //用户点击分公司
                               //companyMeterList=postData('/queryMeterList4Company',selectedNode.original.id);
                               companyMeterListTree.jstree(true).settings.core.data='';
                               companyMeterListTree.jstree(true).refresh();

                               allMeterListTree.jstree(true).settings.core.data=[{text:'所有公司',state:{opened:false}}];
                               allMeterListTree.jstree(true).refresh();
                               //allMeterList=postData('/queryAllMeter4Company','20');
                               allMeterListTree.jstree(true).settings.core.data=postData('/queryAllMeter4Company','9999');
                               allMeterListTree.jstree(true).refresh();

                               map.clear();

                          }
                        }
                     };

                     var menuL2 = {
                        "create": {
                          "separator_before" : false,
                          "separator_after"  : true,
                          "_disabled"          : false,
                          "label"             : "新建区域",
                          "action": function (data) {
                              var inst = $.jstree.reference(data.reference),
                              obj = inst.get_node(data.reference);
                              inst.create_node(obj, {}, "last", function (new_node) {
                                setTimeout(function () { inst.edit(new_node); },0);
                              });
                          }
                        },
                        "rename": {
                          "separator_before" : false,
                          "separator_after"  : true,
                          "_disabled"          : false,
                          "label"             : "重命名",
                          "action": function (data) {
                              var inst = $.jstree.reference(data.reference),
                              obj = inst.get_node(data.reference);
                              inst.edit(obj);
                              console.log(inst.text);
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
                                url: '/removeCompany4Tree',
                                data:{id:obj.original.id},
                                type:"POST",
                                dataType: "json",
                                async: false
                              }).done(function(res, textStatus){

                              }).fail(function(){
                                console.log("出错啦！");
                              });

                              var nodeName=obj.text;
                              console.log(nodeName);
                              inst.delete_node(obj);
                               //用户点击分公司
                               //companyMeterList=postData('/queryMeterList4Company',selectedNode.original.id);
                               companyMeterListTree.jstree(true).settings.core.data='';
                               companyMeterListTree.jstree(true).refresh();

                               allMeterListTree.jstree(true).settings.core.data=[{text:'所有公司',state:{opened:false}}];
                               allMeterListTree.jstree(true).refresh();
                               //allMeterList=postData('/queryAllMeter4Company','20');
                               allMeterListTree.jstree(true).settings.core.data=postData('/queryAllMeter4Company','9999');
                               allMeterListTree.jstree(true).refresh();

                               map.clear();


                          }
                        }
                      };

                      var menuL3 = {
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
                                url: '/removeCompany4Tree',
                                data:{id:obj.original.id},
                                type:"POST",
                                dataType: "json",
                                async: false
                              }).done(function(res, textStatus){

                              }).fail(function(){
                                console.log("出错啦！");
                              });

                              var nodeName=obj.text;
                              console.log(nodeName);
                              inst.delete_node(obj);
                          }
                        }
                      };


                      if (this.get_path(node).length == 3) {
                        return menuL2;
                      } else if (this.get_path(node).length == 2) {
                        return menuL1;
                      } else if (this.get_path(node).length == 4) {
                        return menuL3;
                      }else if (this.get_path(node).length == 1) {
                        return menuL0;
                      }
                    }
                  }
            }).on('changed.jstree', function (e, data) {
                try
                {
                var nodeDepth=data.instance.get_path(data.selected[0]).length;
                var nodeName=data.instance.get_node(data.selected[0]).text;
                var selectedNode=data.instance.get_node(data.selected[0]);
                selectedCompanyId=selectedNode.original.id;
                if(nodeDepth>1){
                    updatePage();
                }

                console.log('公司ID：'+selectedCompanyId);

                //用户点击总公司
                /*
                if(nodeDepth==2){
                    $('.btn-add-area').addClass('hide');
                    $('.btn-add-company').addClass('hide');
                    $('.btn-add-branch-company').removeClass('hide');
                }else if(nodeDepth==3){
                    $('.btn-add-company').addClass('hide');
                    $('.btn-add-branch-company').addClass('hide');
                    $('.btn-add-area').removeClass('hide');
                }else if(nodeDepth==1){
                    $('.btn-add-area').addClass('hide');
                    $('.btn-add-company').removeClass('hide');
                    $('.btn-add-branch-company').addClass('hide');
                }else{
                    $('.btn-add-area').addClass('hide');
                    $('.btn-add-company').addClass('hide');
                    $('.btn-add-branch-company').addClass('hide');
                }
                */
                }catch (e)
                 {
                 }

             }).bind("rename_node.jstree", function (e, data) { // 创建和重命名触发
                    var parentNode=data.instance.get_node(data.node.parent);
                    var selectedNode=data.instance.get_node(data.node.id);
                    console.log(selectedNode.original.id);

                    if(selectedNode.original.id)
                    {
                              $.ajax({
                                url: '/renameCompany4Tree',
                                data:{id:selectedNode.original.id,name:data.node.text},
                                type:"POST",
                                dataType: "json",
                                async: false
                              }).done(function(res, textStatus){

                                data = res;
                              }).fail(function(){
                                console.log("出错啦！");
                              });
                    }
             }).bind("create_node.jstree", function (e, data) { // 创建和重命名触发
                    console.log(data);
                    console.log(data.node.id); // 选择的node id
                    console.log(data.node.text); // 选择的node text
                    console.log(data.node.parent); // 选择项的父级id
                    var newNode=data.instance.get_node(data.node.id);
                    var parentNode=data.instance.get_node(data.node.parent);

                    //var nodeDepth=data.instance.get_path(data.selected[0]).length;
                    //console.log(nodeDepth);


                    if(parentNode.original.id){
                      $.ajax({
                        url: '/addCompany4Tree',
                        data:{id:parentNode.original.id,name:data.node.text},
                        type:"POST",
                        dataType: "json",
                        async: false
                      }).done(function(res, textStatus){
                        newNode.original.id=res.id;
                        selectedCompanyId=res.id;
                        console.log(res.id);
                      }).fail(function(){
                        console.log("出错啦！");
                      });
                    }else{
                      $.ajax({
                        url: '/addCompany4Tree',
                        data:{id:"",name:data.node.text},
                        type:"POST",
                        dataType: "json",
                        async: false
                      }).done(function(res, textStatus){
                        newNode.original.id=res.id;
                        selectedCompanyId=res.id;
                        console.log(res.id);
                      }).fail(function(){
                        console.log("出错啦！");
                      });
                      console.log(parentNode.original.id);
                    }

             }).bind("remove_node.jstree", function (e, data) { // 创建和重命名触发
                    console.log(data);
                    console.log(data.node.id); // 选择的node id
                    console.log(data.node.text); // 选择的node text
                    console.log(data.node.parent); // 选择项的父级id
                    var selectedNode=data.instance.get_node(data.node.id);
                    var parentNode=data.instance.get_node(data.node.parent);
                    console.log(parentNode.original.id);
                    $.ajax({
                                url: '/removeCompany4Tree',
                                data:{id:selectedNode.original.id},
                                type:"POST",
                                dataType: "json",
                                async: false
                              }).done(function(res, textStatus){
                                console.log(res);
                              }).fail(function(){
                                console.log("出错啦！");
                    });

                    //用户点击分公司
                    //companyMeterList=postData('/queryMeterList4Company',selectedNode.original.id);
                    companyMeterListTree.jstree(true).settings.core.data='';
                    companyMeterListTree.jstree(true).refresh();

                    allMeterListTree.jstree(true).settings.core.data=[{text:'所有公司',state:{opened:false}}];
                    allMeterListTree.jstree(true).refresh();
                    allMeterList=postData('/queryAllMeter4Company',selectedNode.original.id);
                    allMeterListTree.jstree(true).settings.core.data=postData('/queryAllMeter4Company',selectedNode.original.id);
                    allMeterListTree.jstree(true).refresh();

                    map.clear();

             });

            var companyMeterListTree=$('.companyMeterListTree').jstree({
                'core' : {
                    "check_callback" : true,
                    "multiple" : true,
                    'data' : companyMeterList,
                    'strings': {
                        'Loading ...' : '加载中 ...'
                    },
                    'dblclick_toggle': false
                },
                'plugins' : [ 'types', "contextmenu"],
                'types' : {
                    "default" : {
                        "icon" : "glyphicon glyphicon-flash"
                    },
                    "demo" : {
                        "icon" : "glyphicon glyphicon-ok"
                    }
                },
                'checkbox': {
                    'three_state': false
                },
                'contextmenu': {
                    "select_node": false,
                    "items": function (node) {

                      var menuL2 = {

                        "remove": {
                          "separator_before" : false,
                          "icon"          : false,
                          "separator_after"  : false,
                          "_disabled"          : false,
                          "label"             : "移除仪表",
                          "action"         : function (data) {
                            var inst = $.jstree.reference(data.reference),
                            obj = inst.get_node(data.reference);

                            console.log(selectedCompanyId+"--------"+obj.original.id);
                            $.ajax({
                                        url: '/removeCompanyMeter4Tree',
                                        data:{cid:selectedCompanyId,mid:obj.original.id},
                                        type:"POST",
                                        dataType: "json",
                                        async: false
                                      }).done(function(res, textStatus){
                                         inst.delete_node(obj);
                                         allMeterListTree.jstree(true).settings.core.data=[{text:'所有公司',state:{opened:false}}];
                                         allMeterListTree.jstree(true).refresh();
                                         allMeterListTree.jstree(true).settings.core.data=postData('/queryAllMeter4Company',selectedCompanyId);
                                         allMeterListTree.jstree(true).refresh();
                                         map.clear();
                                      }).fail(function(){
                                        console.log("出错啦！");
                            });



                          }
                        }
                      };

                      if (this.get_path(node).length == 1) {
                        return menuL2;
                      }
                    }
                  }
            });

            //更新页面状态
            function updatePage(){
                    companyMeterList=postData('/queryMeterList4Company',selectedCompanyId);
                    companyMeterListTree.jstree(true).settings.core.data=companyMeterList;
                    companyMeterListTree.jstree(true).refresh();

                    allMeterListTree.jstree(true).settings.core.data=[{text:'所有公司',state:{opened:false}}];
                    allMeterListTree.jstree(true).refresh();
                    allMeterListTree.jstree(true).settings.core.data=postData('/queryAllMeter4Company',selectedCompanyId);
                    allMeterListTree.jstree(true).refresh();
                    map.clear();
            }

            var allMeterListTree=$('.allMeterListTree').jstree({
                'core' : {
                    "multiple" : true, // 所有水表列表打开多选多选
                    'data' :allMeterList,
                    'strings': {
                        'Loading ...' : '加载中 ...'
                    },
                    'dblclick_toggle': false
                },
                'plugins' : [ 'types', "checkbox" , "contextmenu","search"],
                'types' : {
                    "default" : {
                        "icon" : "glyphicon glyphicon-flash"
                    },
                    "demo" : {
                        "icon" : "glyphicon glyphicon-ok"
                    }
                },
                'checkbox': {
                    'three_state': false
                },
                'contextmenu': {
                    "select_node": false,
                    "items": function (node) {

                      var menuL1 = {
                        "add": {
                          "separator_before" : false,
                          "icon"          : false,
                          "separator_after"  : false,
                          "_disabled"          : false,
                          "label"             : "批量增加仪表",
                          "action"         : function (data) {
                            var inst = $.jstree.reference(data.reference),
                            obj = inst.get_node(data.reference);

                            //批量传递仪表编号
                            var values = map.values();
                            $.ajax({
                                url: '/addMultiMeter4Company',
                                data:{cid:selectedCompanyId,mid:values.join('-')},
                                type:"POST",
                                dataType: "json",
                                async: false
                            }).done(function(res, textStatus){
                                updatePage();
                            }).fail(function(){
                                console.log("出错啦！");
                            });
                          }
                        }
                      };
                      var menuL2 = {
                        "add": {
                          "separator_before" : false,
                          "icon"          : false,
                          "separator_after"  : false,
                          "_disabled"          : false,
                          "label"             : "增加仪表",
                          "action"         : function (data) {
                            var inst = jQuery.jstree.reference(data.reference),
                            obj = inst.get_node(data.reference);

                            $.ajax({
                                url: '/addMeter4Company',
                                data:{cid:selectedCompanyId,mid:obj.original.id},
                                type:"POST",
                                dataType: "json",
                                async: false
                            }).done(function(res, textStatus){
                                companyMeterListTree.jstree(true).settings.core.data=res;
                                companyMeterListTree.jstree(true).refresh();
                                updatePage();
                            }).fail(function(){
                                console.log("出错啦！");
                            });

                            console.log(obj.original.id);
                          }
                        }
                      };

                      if (this.get_path(node).length == 1) {
                        return menuL1;
                      }else if (this.get_path(node).length == 2&& node.state.selected==true&& node.state.disabled==false) {
                            console.log(node.state.selected)
                            return menuL2;
                      }
                    }
                  }
            }).bind("select_node.jstree", function (e, data) { // 选中节点触发
                map.put(data.node.id,data.node.id);
             }).bind("deselect_node.jstree", function (e, data) { // 取消选中节点触发
                map.remove(data.node.id);
             });



            var $csRemoveBtn = $('.cs-btn-remove'); // 移除按钮，触发ajax提交
            var $csAddBtn = $('.cs-btn-add'); // 添加按钮，触发ajax提交


            $csRemoveBtn.on('click',function(){
                $companyListTree.jstree({
                    'core' : {
                        "check_callback" : true,
                        "multiple" : true,
                        'data' : companyMeterList,
                        'strings': {
                            'Loading ...' : '加载中 ...'
                        },
                        'dblclick_toggle': false
                    },
                    'plugins' : [ 'types', "checkbox" ],
                    'types' : {
                        "default" : {
                            "icon" : "glyphicon glyphicon-flash"
                        },
                        "demo" : {
                            "icon" : "glyphicon glyphicon-ok"
                        }
                    },
                    'checkbox': {
                        'three_state': false
                    }
                });
            });

            // 添加仪表
                        $('.btn-add-meter').on('click', function () {
                            var $clientList = $('.client-list');
                            var $allMeterList = $('.all-meter-list');
                            var $clientMeterList=$('.client-meter-list');

                            if ($(this).text() == '添加仪表') {
                                $(this).text('隐藏所有仪表');
                                $allMeterList.removeClass('hide');
                                $clientMeterList.removeClass('col-md-6');
                                $clientMeterList.addClass('col-md-4');

                                $clientList.removeClass('col-md-6');
                                $clientList.addClass('col-md-4');

                                $allMeterList.addClass('col-md-4');
                                return false;
                            }
                            $(this).text('添加仪表');
                            $allMeterList.addClass('hide');
                            $allMeterList.removeClass('col-md-4');

                             $clientMeterList.removeClass('col-md-4');
                             $clientMeterList.addClass('col-md-6');

                             $clientList.removeClass('col-md-4');
                             $clientList.addClass('col-md-6');
                        });



            $('.btn-add-branch-company').on('click', function () {
                document.getElementById("branch-cid").value=selectedCompanyId;
            });

            $('.btn-add-area').on('click', function () {
                document.getElementById("area-cid").value=selectedCompanyId;
            });

            $('.btn-add-company-submit').on('click', function () {
                 console.log('提交分公司');
                 var data = '';
                 $.ajax({
                    url: '/addCompany',
                    data:{id:1},
                    type:"POST",
                    dataType: "json",
                    async: false
                 }).done(function(res, textStatus){

                 }).fail(function(){
                    console.log("出错啦！");
                 });
            });

        });
        
$(function () {
	$('#searchMeter').keyup(function () {
		var searchValue = $('#searchMeter').val();
		if( searchValue != ''){
			$('.allMeterListTree').jstree(true).search(searchValue,false,true);
		} else{
			$('.allMeterListTree').jstree(true).clear_search();
		}
	});
});