$(function() {
   var pageNo = 1;

   var tempAreaId='';
	// 模拟数据
	var data = {}; // 定义存放数据的变量
	// ajax 获取数据的通道 获取全部数据
	function ajaxDate(_id, _url, callback) {
		if (true) {
			$.ajax({
				type : "post",
				url : _url,
				dataType : "json",
				success : function(msg) {
					callback(msg);
				}
			})
		} else {
			callback && callback(data[_id]);
		}
	}
	
	function formateDate(obj){
		if(obj<10) obj = '0' + obj;
		return obj;
	}
	
	// 数据模板
	var _onceT = true;
	function dataTemplate(type, data, appendFlg) {
		var _html = '';
		for (var i = 0; i < data.length; i++) {
			var _curTimer='无';
			if(data[i]["i_time"]!=null){
				var date = new Date(data[i]["i_time"]);
				var year = date.getFullYear();
				var month = date.getMonth() + 1;
				var day = date.getDate();
				var hour = date.getHours();
				var minute = date.getMinutes();
				var second = date.getSeconds();
				_curTimer = year + '-' + formateDate(month) + '-' + formateDate(day)+ ' ' + formateDate(hour)+':' + formateDate(minute) + ':' + formateDate(second);
			}
			console.log(_curTimer);
			var _url = "/history?id=" + data[i]["num"]+"&type="+type+"&area="+tempAreaId;
			console.log(data[i]["num"]);
			// 在这里要查看对应的历史曲线 可根据不同的id值
			_html += '<li class="content-list" data-url='
					+ '"'
					+ _url
					+ '"'
					+ '><span class="content-list-t">'+data[i]["subUserName"]+'</span><span class="content-list-t">'
					+ _curTimer + '</span><span class="content-list-t">'
					+ (data[i][type]==null?'无':data[i][type]) + '</span>';
//			if (type == "flow" || type == "press") {
//				_html += '<span class="content-list-t">正常</span>';
//			}
		}
//		if (type == "flow" || type == "press") {
//			$(".show-type-state").show();
//		} else {
			$(".show-type-state").hide();
//		}
		if(appendFlg){
			$(".lists-home").append(_html);
		} else{
			$(".lists-home").html(_html);
			pageNo = 2;
			dropload.unlock();
			dropload.noData(false);
		}
		// 重置
        dropload.resetload();
		// 每次渲染模板 都要重新绑定点击事件
		$(".content-list").on("tap", function() {
			var _url = $(this).attr("data-url");
			// 在这里跳转的时候 后期需要根据id值进行跳转
			window.location.href = _url;
		})
	}

	// 首次加载默认 渲染瞬时流量
	/**
	ajaxDate("flow", "/queryMeterInfoForMobile?pageNo="+pageNo, function(data) {
		if (data) {
			dataTemplate("flow", data);
		} else {
			$(".lists-home").html("暂时没有数据哦");
		}
	});
	*/
	$(".content-list").on("tap", function() {
		var _url = $(this).attr("data-url");
		console.log(_url);
		// 在这里跳转的时候 后期需要根据id值进行跳转
		window.location.href = _url;
	})
	$(".nav-list").on(
			"tap",
			function() {
				var _type = $(this).attr("data-type");
				var _show = $(".nav-ts-" + _type).attr("data-show");
				// 遮罩层点击空白关闭下拉框
				if ($(this).hasClass('active')) {
					$('#mcover').toggle();
					$('#mcover').on(
							'tap',
							function() {
								$('#mcover').hide();
								if ($('.nav-list').hasClass("active")) {
									$(".nav-ts-" + _type).hide().attr(
											"data-show", "false");
								}
							});
				} else {
					$('#mcover').show();
					$('#mcover').on(
							'tap',
							function() {
								$('#mcover').hide();
								if ($('.nav-list').hasClass("active")) {
									$(".nav-ts-" + _type).hide().attr(
											"data-show", "false");
								}
							});
				}
				_show == "false" ? $(".nav-ts-" + _type).fadeIn().attr(
						"data-show", "ture").parent().siblings().children(
						".nav-ts").hide().attr("data-show", "false") : $(
						".nav-ts-" + _type).hide().attr("data-show", "false");
				if ($(this).hasClass("active"))
					return false;
				$(this).addClass("active").siblings().removeClass("active");
			});
	$(".ts-list").on(
			"tap",
			function() {
				var _txt = $(this).html(), _type = $(this).parents(".nav-list")
						.attr("data-type"), _otxt = $(".nav-t-id").html();
				// 当选择数值的时候 和当前的进行对比 如果发生变动则 加载对应的数据
				// 这里地区 可以设置一个id 类型可以设置一个 type
				// 这里 我们列举 北京 id=1 上海 id = 2 广州 id = 3 全部地区 id = 0
				// 瞬时流量 = flux 净累计 = plus 正向累积总量 = fplus 反向累积总量 = rplus
				// 瞬时压力 = pa 信号强度 = strth
				$(this).parent().attr("data-show", "false").hide().siblings()
						.html(_txt);
				_type == "sarea" ? $(this).parent().siblings().attr("data-id",
						$(this).attr("data-id")) : '';
				_type == "spara" ? $(this).parent().siblings().attr(
						"data-type", $(this).attr("data-type")) : '';
				var _areaId = $(".nav-t-id").attr("data-id"), _dtype = '';

				tempAreaId=_areaId;

				$('#mcover').hide();
				if (_type == "spara") {
					_dtype = $(this).attr("data-type");
					ajaxDate(_areaId, "/queryMeterInfoForMobile?pageNo=1&id="
							+ _areaId, function(data) {
						// 渲染数据的类型 _dtype
						dataTemplate(_dtype, data, false);
					});
				}
				if (_type == "sarea") {
					if (_txt == _otxt)
						return false;
					_dtype = $(".nav-t-type").attr("data-type");
					ajaxDate(_areaId, "/queryMeterInfoForMobile?pageNo=1&id="
							+ _areaId, function(data) {
						dataTemplate(_dtype, data, false);
					});
				}
				return false;
			});

	$(".fixed-nav-list").on("tap", function() {
		var _type = $(this).attr("data-type");
		if ($(this).hasClass("active"))
			return false;
		$(this).addClass("active").siblings().removeClass("active");
	})
	
	var dropload = $(".content-lists").dropload({
		scrollArea : window,
        loadDownFn : function(me){
        	console.log("page down");
        	var _areaId = $(".nav-t-id").attr("data-id");
        	var newtype = $(".nav-t-type").attr("data-type");
            $.ajax({
				type : "post",
				url : "/queryMeterInfoForMobile?pageNo="+pageNo+"&id="+_areaId,
				dataType : "json",
				success : function(data) {
					if (data && data.length > 0) {
		    			dataTemplate(newtype, data, true);
		    			me.resetload();
		    		} else {
		    			// 锁定
                        me.lock();
                        // 无数据
                        me.noData()
		    		}
					me.resetload();
				},error: function(xhr, type){
                    // 即使加载出错，也得重置
                    me.resetload();
                }
			});
			pageNo ++;
        },
        domDown : { // 下方DOM
            domClass   : 'dropload-down',
            domRefresh : '<div class="dropload-refresh">↑上拉加载更多</div>',
            domLoad    : '<div class="dropload-load"><span class="loading"></span>加载中...</div>',
            domNoData  : ''
        }
	});
})