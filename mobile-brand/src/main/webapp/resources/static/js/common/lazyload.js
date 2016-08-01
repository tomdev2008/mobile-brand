function lazyload(option) {
	
	var settings = {
		defObj : null,
		defHeight : 0
	};
	settings = $.extend(settings, option || {});
	var defObj = (typeof settings.defObj == "object")? settings.defObj.find("img"): $(settings.defObj).find("img");
	var pageTop = function() {
		var d = document, y = (navigator.userAgent.toLowerCase().match(/iPad/i) == "ipad")? window.pageYOffset: Math.max(d.documentElement.scrollTop, d.body.scrollTop);
		return d.documentElement.clientHeight + y - settings.defHeight;
	};
	var imgLoad = function() {
		defObj.each(function() {
			if ($(this).offset().top <= pageTop()) {
				var src3 = $(this).attr("src3");
				if (src3) {
					$(this).attr("src", src3).removeAttr("src3");
				}
			}
		});
	};
	imgLoad();
	$(window).bind("scroll", function() {
		imgLoad();
	});
}