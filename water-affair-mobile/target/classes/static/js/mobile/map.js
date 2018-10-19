
$(document).ready(function () {
    //刷新水表地图信息
    showMeterMap();
});

var infoWindow = new AMap.InfoWindow({offset: new AMap.Pixel(0, -30)});
var meterMap = '';

function showMeterMap() { // 获取节点数据
    markers.splice(0,markers.length);
    map.clearMap();  // 清除地图覆盖物
    meterMap = getData('/queryMeterList4MobileMap');
    $.each(meterMap, function(i, meter){   
        console.log(meter.status);
        var marker;


        if(meter.status==1){
            marker= new AMap.Marker({
                       map: map,
                       animation:"AMAP_ANIMATION_BOUNCE",
                       position:[meter.longitude,meter.latitude]
                   });
        }
        if(meter.status==2){
            marker= new AMap.Marker({
                       map: map,
                       position:[meter.longitude,meter.latitude]
                   });
        }

        if(meter.status==0){
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
        marker.name=meter.name;
        marker.lo=meter.longitude;
        marker.la=meter.latitude;
        marker.content=meter.detail;
        marker.dcontent=meter.detail;
        marker.state=1;
        markers.push(marker);
    }); 
    map.setFitView();
}

function markerClick(e) {
       infoWindow.setContent(e.target.dcontent);
       infoWindow.open(map, e.target.getPosition());
}


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


