
		$(function(){
			//模拟数据  可通过ajax 一次加载
			// $.getJSON("定义的接口"，function(msg){
			// 		msg  返回的数据
			// })
			//站点名称 name 安装地址 adress 设备型号 model 序列号 snumber 站点编号 number  定义的几个参数 没有的话  就可以为空就好了
			var data = {
				"1": {
					"name":"莆田市第二水厂",
					"adress": "静安区华山路1083号",
					"snumber": "4659",
					"number": "4659",
					"model": "AE85"
				},
				"2": {
					"name":"莆田市第二水厂",
					"adress": "静安区华山路1083号",
					"snumber": "4659",
					"number": "4659",
					"model": "AE85"
				},
				"3": {
					"name":"莆田市第二水厂",
					"adress": "静安区华山路1083号",
					"snumber": "4659",
					"number": "4659",
					"model": "AE85"
				},
				"4": {
					"name":"莆田市第二水厂",
					"adress": "静安区华山路1083号",
					"snumber": "4659",
					"number": "4659",
					"model": "AE85"
				}
			};
			//判断要加载数据的 id值
			var idType = location.search.split("=")[1] || "";
			function spTemplate(data,callback){
				var html = '<li><ul><ol>站点名称</ol><ol class="sp-adress">'+data['name']+'</ol></ul></li>';
				html += '<li><ul><li>序列号</li><li class="sp-snumber">'+data['snumber']+'</li><li>站点编号</li><li class="sp-number">'+data['number']+'</li></ul></li>';
				//没有定义数据的直接为空
				html += '<li><ul><li>设备卡号</li><li></li><li>设备分组</li><li></li></ul></li>';
				html += '<li><ul><li>超时设定</li><li></li><li>安装人员</li><li></li></ul></li>';
				html += '<li><ul><li>安装日期</li><li></li><li>设备型号</li><li>'+data['model']+'</li></ul></li>';
				html += '<li><ul><li>设备状态</li><li></li><li>上传周期</li><li></li></ul></li>';
				html = '<li><ul><ol>安装地址</ol><ol class="sp-adress">'+data['adress']+'</ol></ul></li>';
				callback&&callback(html);
			}
			// spTemplate(data['1']);
			// console.log(location.search);
			spTemplate(data[idType],function(msg){
				//获取模板成功之后的dosomething
				// console.log(msg);
				$('.sp').html('').append(msg);
			})
		});