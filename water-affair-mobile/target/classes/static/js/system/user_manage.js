function buildNewTable(companyid,companyName){
                try{
                    $("#table_list_2").jqGrid('GridUnload');
                }catch(err){

                }

                var fn_editSubmit=function(response,postdata){
                    console.log(postdata);
                    console.log(response);
                }

            // Configuration for jqGrid Example 2
            var jqtable=$("#table_list_2").jqGrid({
                url:'/queryCompanyUser?cid='+companyid,
                datatype: 'json',
                mtype: 'POST',
                height:600,
                autowidth: true,
                shrinkToFit: true,
                rowNum: 10,
                rowList: [10, 20, 30],
                colNames:['员工编号', '用户名', '密码','设置权限'],
                colModel:[
                    {name:'id',index:'id', editable: false, width:60, sorttype:"int", align:"center"},
                    {name:'userName',index:'userName', editable: true, width:100, align:"center"},
                    {name:'password',index:'password', editable: true, width:80, align:"center"},
                    {name:'modifypermission',index:'modifypermission', editable: true, width:80,align:"center", sorttype:"int"}
                ],
                pager: "#pager_list_2",
                viewrecords: true,
                caption: companyName,
                add: true,
                edit :true,
                addtext: 'Add',
                edittext: 'Edit',
                hidegrid: false
            });

            // Setup buttons
            $("#table_list_2").jqGrid('navGrid', '#pager_list_2',
                    {edit: true, add: true, del: true,search:false},
                    {
                        url:'/editCompanyUser4JQGrid'+'?cid='+companyid,
                        closeAfterEdit:true,
                        closeOnEscape:true,
                        afterComplete: function (response, postdata, formid) {

                            console.log('start');
                            console.log(response);
                            if(response.responseText.indexOf("成功") >= 0){
                                toastr.success(response.responseText,'')
                            }else{
                                toastr.error(response.responseText,'')
                            }
                            if ($("#list").getGridParam("datatype") === "local") {
                                $("#list").setGridParam({ datatype: 'local' });
                            }
                            $("#list").trigger("reloadGrid");
                        },
                        beforeShowForm: function(form) {
                            console.log('start');
                        }
                    },
                    {
                        url:'/addCompanyUser4JQGrid'+'?cid='+companyid,
                        closeAfterAdd:true,
                        closeOnEscape:true,
                        afterComplete: function (response, postdata, formid) {
                            console.log('start');
                            console.log(postdata);
                            if(response.responseText.indexOf("成功") >= 0){
                                toastr.success(response.responseText,'')
                            }else{
                                toastr.error(response.responseText,'')
                            }
                            $("#list").trigger("reloadGrid");
                        },
                        beforeShowForm: function(form) {
                            console.log('start');
                        }
                    },
                    {
                        url:'/deleteCompanyUser4JQGrid'+'?cid='+companyid,
                        closeAfterDelete:true,
                        closeOnEscape:true,
                        afterComplete: function (response, postdata, formid) {
                            console.log('start');
                            console.log(response);

                        },
                        beforeShowForm: function(form) {
                            console.log('start');
                        }
                    }
            );
        }

        $(document).ready(function(){
            var selectedCompany=1;
            //构造用户列表
            buildNewTable(selectedCompany,'系统管理员');

            $('.i-checks').iCheck({
                checkboxClass: 'icheckbox_square-blue',
                radioClass: 'iradio_square-blue'
            });

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
            var companyList = getData('/userManageController');
            $('.add-new-user').addClass('hide');



            var companyListTree=$('.tree-company').jstree({
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

                    }
                  }
            }).on('changed.jstree', function (e, data) {
                var nodeDepth=data.instance.get_path(data.selected[0]).length;
                var companyName=data.instance.get_node(data.selected[0]).text;

                var selectedNode=data.instance.get_node(data.selected[0]);
                var parentNode=data.instance.get_parent(data.selected[0]);
                if(nodeDepth>1){
                    selectedCompany=selectedNode.original.id;
                    buildNewTable(selectedNode.original.id,companyName);
                }else{
                    buildNewTable(1,'系统管理员');
                }

             });





        });