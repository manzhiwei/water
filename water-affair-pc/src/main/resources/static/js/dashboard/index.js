var dashboardType=1;
var meterInfoLabelX=-60,meterInfoLabelY=-140;

$(document).ready(function () {

    setInterval('alarmMonitor()',300000);

    var dbTree=$('#index_tree').jstree({
        'core': {
            'data': getData('/queryMeterList4Tree?type='+dashboardType)
        },
        'plugins': [ 'types', 'dnd' ],
        'types': {
            'default': {
                'icon': 'fa fa-folder'
            },
            'html' : {
                'icon': 'fa fa-file-code-o'
            },
            'svg' : {
                'icon': 'fa fa-file-picture-o'
            },
            'css': {
                'icon': 'fa fa-file-code-o'
            },
            'img': {
                'icon': 'fa fa-file-image-o'
            },
            'js' : {
                'icon': 'fa fa-file-text-o'
            }
        }
    }).on('changed.jstree', function (e, data) {

          var nodeDepth=data.instance.get_path(data.selected[0]).length;
          var nodeName=data.instance.get_node(data.selected[0]).text;
          var selectedNode=data.instance.get_node(data.selected[0]);
//          console.log("--------------"+selectedNode.original.mid);
//          console.log("--------------"+selectedNode.original.longitude);
//          console.log("--------------"+selectedNode.original.latitude);
          if(selectedNode.original.mid>0&&selectedNode.original.longitude>0&&selectedNode.original.latitude>0){
            map.setZoomAndCenter(16, [selectedNode.original.longitude, selectedNode.original.latitude]);
          }else{
            if(selectedNode.original.mid>0&&selectedNode.original.longitude==0&&selectedNode.original.latitude==0){
                toastr.error('水表经纬度信息错误！','');
            }
          }
    });

    //刷新水表地图信息
    showMeterMap();

    alarmMonitor();
});

var infoWindow = new AMap.InfoWindow({offset: new AMap.Pixel(0, -30)});
var meterMap = '';

function alarmMonitor() {
        // 获取节点数据
        var data = '';
        $.ajax({
            url: "/queryAlarmMessage",
            dataType: "json",
            async: false
        }).done(function(res, textStatus){
            var alarmList=res[0];
            //console.log(res);
            //更新未读的告警数量
            document.getElementById("unread_alarm_number").innerText=alarmList.totalAmount;
            //显示前三条或少于三条的告警信息
            $("#alarms").children().remove();
            var index=0;
            $.each(alarmList.alarms, function(i, alarm){
                //只显示前三条
                if(index<3){
                    var li='<li><a onclick="readAlarmMessage(' + alarm.id + ')" data-toggle="modal" data-aid="' + alarm.id + '" data-target="#alertModel"><div class="text-center link-block"><i class="fa fa-warning fa-fw"></i>'+"   "+alarm.meterName+"   "+alarm.alarmType+"   "+alarm.alarmContent+'</div></a></li>';
                    $("#alarms").append(li);//追加tab
                    toastr.error('告警信息：'+alarm.alarmType,'')
                }
                index++;
            });
            var showAll='<li><div class="text-center link-block"><a href="/alarmList"><strong>显示全部告警</strong><i class="fa fa-angle-right"></i></a></div></li>';
            $("#alarms").append(showAll);//追加tab
            //将告警的站点标红
//            updateMeterMap();
        }).fail(function(){
            console.log("出错啦！");
        });
        return data;
    };

function updateMeterMap() { // 获取节点数据
    //animation:"AMAP_ANIMATION_BOUNCE",
    markers.splice(0,markers.length);
    map.clearMap();  // 清除地图覆盖物
    meterMap = getData('/queryMeterList4Map?type='+dashboardType);
    $.each(meterMap, function(i, meter){
        var marker ;
        if(meter.status==1){
            marker= new AMap.Marker({
                       map: map,
                       icon:"images/alarmmeter.png",

                       position:[meter.longitude,meter.latitude]
                   });
        }else if(meter.status==2){
            marker= new AMap.Marker({
                       map: map,
                       icon:"images/alarmmeter.png",
                       position:[meter.longitude,meter.latitude]
                   });
        }else{
            marker= new AMap.Marker({
               map: map,
               position:[meter.longitude,meter.latitude]
           });
        }

        marker.setLabel({
            offset: new AMap.Pixel(0, -22),
            content: meter.subUserName
        });
        marker.on('click', markerClick);
        marker.name=meter.subUserName;
        marker.lo=meter.longitude;
        marker.la=meter.latitude;
        marker.content=meter.subUserName;
        marker.dcontent=meter.detail;
        marker.state=1;
        marker.on('mouseover', mouseOver);
        marker.on('mouseout', mouseOut);
        marker.on('click', markerClick);
        markers.push(marker);
    });

    var obj = document.getElementById("showAllInfo");
    var value = obj.checked;
    if(obj.checked==true){
        //不改变地图视图，仅将所有地图点的详情打开
        $.each(markers, function(i, marker){
            if(marker.state==1){
                marker.setLabel({
                    offset: new AMap.Pixel(meterInfoLabelX, meterInfoLabelY),
                    content: marker.dcontent
                });
                marker.state=3;
            }
        });
    }else{
        $.each(markers, function(i, marker){
             if(marker.state==3){
                 marker.setLabel({
                     offset: new AMap.Pixel(0, -22),
                     content: marker.content
                 });
                 marker.state=1;
             }
         });
    }
}

function showMeterMap() { // 获取节点数据
    var obj = document.getElementById("showAllInfo");
    obj.checked=false;
    markers.splice(0,markers.length);
    map.clearMap();  // 清除地图覆盖物
    meterMap = getData('/queryMeterList4Map?type='+dashboardType);
    $.each(meterMap, function(i, meter){
        var marker ;
        if(meter.status==1){
            marker= new AMap.Marker({
                       map: map,
                       icon:"images/alarmmeter.png",
                       animation:"AMAP_ANIMATION_BOUNCE",
                       position:[meter.longitude,meter.latitude]
                   });
        }else if(meter.status==2){
             marker= new AMap.Marker({
                        map: map,
                        icon:"images/alarmmeter.png",
                        position:[meter.longitude,meter.latitude]
                    });
         }else{
             marker= new AMap.Marker({
                                   map: map,
                                   position:[meter.longitude,meter.latitude]
                               });
        }

        marker.setLabel({
            offset: new AMap.Pixel(0, -5),
            content: meter.subUserName
        });
        marker.on('click', markerClick);
        marker.name=meter.subUserName;
        marker.lo=meter.longitude;
        marker.la=meter.latitude;
        marker.content=meter.subUserName;
        marker.dcontent=meter.detail;
        marker.state=1;
        marker.on('mouseover', mouseOver);
        marker.on('mouseout', mouseOut);
        marker.on('click', markerClick);
        markers.push(marker);
    });
    map.setFitView();
}

function markerClick(e) {

       infoWindow.setContent(e.target.dcontent);
                     infoWindow.open(map, e.target.getPosition());
                     e.target.setLabel({
                         offset: new AMap.Pixel(-30, -78),
                         content: ''
                     });


       if(e.target.state==1){


            map.remove(e.target);
            marker = new AMap.Marker({
                map: map,
                icon:"http://webapi.amap.com/theme/v1.3/markers/n/mark_r.png",
                position:e.target.getPosition()
            });
            marker.content=e.target.content;
            marker.dcontent=e.target.dcontent;
            marker.on('mouseover', mouseOver);
            marker.on('mouseout', mouseOut);
            marker.on('click', markerClick);
            marker.setLabel({
                            offset: new AMap.Pixel(0, -22),
                            content: e.target.content
                        });
            /*marker.setLabel({
                offset: new AMap.Pixel(-30, -138),
                content: e.target.dcontent
            });*/
            /*
            infoWindow.setContent(e.target.dcontent);
            infoWindow.close(map, e.target.getPosition());
            */
            marker.state=2;
            markers.push(marker);
       }else if(e.target.state==2){
            map.remove(e.target);
            marker = new AMap.Marker({
                map: map,
                position:e.target.getPosition()
            });
            marker.content=e.target.content;
            marker.dcontent=e.target.dcontent;
            marker.on('mouseover', mouseOver);
            marker.on('mouseout', mouseOut);
            marker.on('click', markerClick);

            marker.setLabel({
                offset: new AMap.Pixel(0, -22),
                content: e.target.content
            });
            marker.state=1;
            markers.push(marker);
       }
       console.log(e.target.state);
    }

function mouseOver(e) {
         if(e.target.state==1){

         }
         infoWindow.setContent(e.target.dcontent);
                       infoWindow.open(map, e.target.getPosition());
                       e.target.setLabel({
                           offset: new AMap.Pixel(-30, -78),
                           content: ''
                       });
      }

function mouseOut(e) {

         if(e.target.state==1){
            infoWindow.setContent(e.target.dcontent);
            infoWindow.close(map, e.target.getPosition());
            e.target.setLabel({
                offset: new AMap.Pixel(0, -22),
                content: e.target.content
            });
         }
    }

$('.showAllInfo').click(function(){
    var obj = document.getElementById("showAllInfo");
    var value = obj.checked;
    if(obj.checked==true){
        //不改变地图视图，仅将所有地图点的详情打开
        $.each(markers, function(i, marker){
            if(marker.state==1){
                marker.setLabel({
                    offset: new AMap.Pixel(meterInfoLabelX, meterInfoLabelY),
                    content: marker.dcontent
                });
                marker.state=3;
            }

        });
    }else{
        $.each(markers, function(i, marker){
             if(marker.state==3){
                 marker.setLabel({
                     offset: new AMap.Pixel(0, -22),
                     content: marker.content
                 });
                 marker.state=1;
             }
         });
    }
});

function getData (url) {
    // 获取节点数据
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
};

//阅读当前弹出的告警信息框
function readAlarmMessage(){
   $.ajax({
           url: "/readAlarmByAid?aid="+document.getElementById("aid").value,
           dataType: "json",
           async: false
       }).done(function(res, textStatus){
           document.getElementById("unread_alarm_number").innerText=document.getElementById("unread_alarm_number").innerText-1;
           updateMeterMap();


       }).fail(function(){
           console.log("出错啦！");
       });
}

//点击查看详情
function  readAlarmMessage(aid) {

    $.ajax({
        url: "/queryAlarmByAid?aid="+aid,
        dataType: "json",
        async: false
    }).done(function(res, textStatus){
        //var content=res.split(",");
        console.log(res);
        document.getElementById("aid").value=res.id;
        document.getElementById("model_title").value="站点名称："+res.meterName;
        document.getElementById("alarm_type").value="告警类型："+res.alarmType;
        document.getElementById("alarm_content").value="告警内容："+res.alarmContent;

        var newDate = new Date();
        newDate.setTime(res.createTime.time);
        console.log();
        document.getElementById("alarm_time").value="告警时间："+newDate.toLocaleString();
    }).fail(function(){
        console.log("出错啦！");
    });


}

//查询某个站点
function show(){
      var obj = document.getElementById("search-content");
      var value = obj.value;
      var nobingo=true;
      console.log(value);
      $.each(markers, function(i, marker){
         console.log(marker.name);
         var mname=marker.name;
         if(mname.indexOf(value)>=0){
            toastr.success(marker.name+' 已匹配','');
            nobingo=false;
            map.setZoomAndCenter(18, [marker.lo, marker.la]);
            return false;
         }
     });
     if(nobingo){
        toastr.error('未查询到：'+value,'')
     }

};


