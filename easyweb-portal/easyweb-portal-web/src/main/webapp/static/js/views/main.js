$(function() {
	$.permCheck.run();
	$('#logout').bind('click', function() {
		var logoutUrl = window.ctxPaths + "/";
		$.ajax({
			url : window.ctxPaths + '/portal/logout.ajax',
			type : 'POST',
			timeout : 30000,
			dataType : 'json',
			async : false,
			success : function(data) {
				if (data.success) {
					// 退出
					window.location.href = logoutUrl;
				} else {
					if (data.data && data.data.msg) {
						// alert('退出失败，原因：' + data.data.msg);

					} else {
						// alert('退出失败，原因未知');
					}
					window.location.href = logoutUrl;
				}
			},
			error : function() {
				alert('退出失败，原因未知');
				window.location.href = logoutUrl;
			}

		});
	});
	var menuLevelClazz = ['nav-second-level','nav-third-level'];
	// 生成菜单
	var buildMenu = function(menus) {
		var buildMenuHtml = function(menus) {
			var html = [];
			$
					.each(
							menus,
							function(i, menu) {
								html.push('<li>');
								html
										.push('<a href="#"><i class="'
												+ menu['icon']
												+ '"></i><span class="nav-label">'
												+ menu.text + ' </span>');
								if (menu.leaf != true && menu.leaf != 'true') {
									html
											.push('<span class="fa arrow"></span>');
								}
								html.push('</a>');
								var menuLevel = 1;
								var initSubMenu = function(menu) {
									if (menu.leaf != true
											&& menu.leaf != 'true') {
										var childrens = menu.children, len = childrens.length;
										html
												.push('<ul class="nav '+menuLevelClazz[menuLevel++]+' collapse" aria-expanded="false">');
										for (var i = 0; i < len; i++) {
											html.push('<li>');
											var url = childrens[i].url;
											if (childrens[i].url) {
												// url = url +
												// (url.lastIndexOf('?') == -1?
												// '?' : '&') + (new
												// Date()).getTime();
												html
														.push('<a class="J_menuItem" href="'+url+'">'
																+ childrens[i].text
																+ '</a>');
											} else {
												html
														.push('<a href="#">' + childrens[i].text +' <span class="fa arrow"></span></a>');
											}
											initSubMenu(childrens[i]);
											html.push('</li>');

										}
										html.push('</ul>');
									}
								};
								initSubMenu(menu);
								html.push('</li>');
							});
			return html.join('');
		};
		var htmlFrame = buildMenuHtml(menus);
		// 追加到menu div 中
		$('#side-menu').append(htmlFrame);
	};
	
	// 添加选中效果
	// $("#menu ul li").click(function(){
	// 设置select样式，并找到同级和后代中设置为select的移除select
	// $(this).addClass("selected").siblings("li.selected").removeClass("selected").find('li.selected').removeClass("selected");
	// });

	/**$.ajax({
		// url: $.appendExtraParams(window.ctxPaths + '/menu.ajax'),
		url : ctxPaths + '/portal/menu.ajax',
		type : 'POST',
		timeout : 30000,
		dataType : 'json',
		success : function(data) {
			if (data.success) {
				var menus = data.data;
				if (menus && menus.length > 0) {
					buildMenu(menus);
				}
			}
		}

	});**/
});
function getViewPort(obj) {
	var viewportwidth = 0, viewportheight = 0;
	if (typeof window.innerWidth != 'undefined') {
		obj = obj || window;
		viewportwidth = obj.innerWidth;
		viewportheight = obj.innerHeight;
	} else if (typeof document.documentElement != 'undefined'
			&& typeof document.documentElement.clientWidth != 'undefined'
			&& document.documentElement.clientWidth != 0) {
		obj = obj || document.documentElement;
		viewportwidth = obj.clientWidth;
		viewportheight = obj.clientHeight;
	}
	return {
		width : viewportwidth,
		height : viewportheight
	};
}
function setCookie(name, value, p) {
	var sCookie = name + '=' + encodeURIComponent(value);
	if (p) {
		if (p.expires) {
			if (p.expires != "session") {
				var etime = new Date();
				if (p.expires instanceof Date) {
					etime = p.expires;
				} else if (!isNaN(p.expires)) {
					etime.setTime(etime.getTime() + p.expires);
				} else if (p.expires == "hour") {
					etime.setHours(etime.getHours() + 1);
				} else if (p.expires == "day") {
					etime.setDate(etime.getDate() + 1);
				} else if (p.expires == "week") {
					etime.setDate(etime.getDate() + 7);
				} else if (p.expires == "year") {
					etime.setFullYear(etime.getFullYear() + 1);
				} else if (p.expires == "forever") {
					etime.setFullYear(etime.getFullYear() + 120);
				} else {
					etime = p.expires;
				}
				sCookie += "; expires=" + etime.toGMTString();
			}
		}
		if (p.path) {
			sCookie += "; path=" + p.path;
		}
		if (p.secure) {
			sCookie += "; secure=" + p.secure;
		}
	}
	document.cookie = sCookie;
}
var initIfmStyle = function() {
	var ifms = $('.tabcontent').find('.tab-pane:visible').find('iframe');
	if (ifms.length == 0)
		return;
	var winWH = getViewPort(), ifm = ifms.eq(0), ifmW = ifm.width(), ifmH = ifm
			.height(), ifmLeft = ifm.offset().left, ifmTop = ifm.offset().top, st = document.documentElement.scrollTop
			+ document.body.scrollTop, sl = document.documentElement.scrollLeft, val = 'winW='
			+ winWH['width']
			+ '&winH='
			+ winWH['height']
			+ '&ifmW='
			+ ifmW
			+ '&ifmH='
			+ ifmH
			+ '&ifmLeft='
			+ ifmLeft
			+ '&ifmTop='
			+ ifmTop
			+ '&scrollTop=' + st + '&scrollLeft=' + sl;
	return val;
};