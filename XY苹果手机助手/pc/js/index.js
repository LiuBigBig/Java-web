$(function() {
	
	//点击小火箭回到顶部按钮
	var topcontrol = $('#topcontrol');
	//导航栏对象
	var nav = $('.nav-wrap');
	
	//导航栏相对网页原点的位置(导航栏的偏移量的上便宜量)
	var navPos = nav.offset().top;
	//导航栏的实际高度
	var navHeight = nav.outerHeight();
	
	//滚动条事件
	$(window).scroll(function() {
		
		//获得滚动条的位置
		var sTop = $(window).scrollTop();
		
		//动态显示隐藏回到顶部按钮(超过200像素显示)
		if(sTop >= 200) {
			topcontrol.fadeIn(300);
		} else {
			topcontrol.fadeOut(300);
		}
		
		//自动给导航栏添加fixed样式
		if (sTop >= navPos) {
			//判断是否有这个样式
			if (!nav.hasClass('fix')) {
				nav.addClass('fix');
				$('.banner').css('margin-bottom',navHeight);//banner下添加空白占位保留原来导航栏的位置
			}
		} else {
			if (nav.hasClass('fix')) {
				nav.removeClass('fix');
				$('.banner').css('margin-bottom',0);
			}
		}
		
//		//滚动监听导航自动高亮
//		function navActive(target) {
//			//判断当前target位置是否有高亮类
//			if ( !$(target).hasClass('active') ) {
//				
//				//删除所有的高亮
//				$('.nav-wrap li').removeClass('active');
//				//单独添加当前target高亮类
//				$(target).addClass('active');
//				
//			}
//			
//		}
//		
//		//海量正版免费下区域导航自动高亮
//		var introStart = $('#post-intro').offset().top - navHeight;
//		var introEnd = $('#post-intro').offset().top + $('#post-intro').outerHeight() - navHeight;
//		
//		if (sTop >= introStart && sTop < introEnd) {
//			navActive('.intro');
//		} else {
//			if ( $('.intro').hasClass('active') ) {
//				$('.intro').removeClass('active');
//			}
//		}

		//海量正版免费下区域导航自动高亮
		var introStart = $('#post-intro').offset().top - navHeight;
		var introEnd = $('#post-intro').offset().top + $('#post-intro').outerHeight() - navHeight;
		
		if (sTop >= introStart && sTop < introEnd) {
			if ( !$('.intro').hasClass('active') ) {
				$('.intro').addClass('active');
			}
		} else {
			if ( $('.intro').hasClass('active') ) {
				$('.intro').removeClass('active');
			}
		}
		
		//进入到无需账号密码区域导航自动高亮
		var usageStart = $('#post-usage').offset().top - navHeight;
		var usageEnd = $('#post-usage').offset().top + $('#post-usage').outerHeight() - navHeight;
		
		if (sTop >= usageStart && sTop < usageEnd) {
			if ( !$('.usage').hasClass('active') ) {
				$('.usage').addClass('active');
			}
		} else {
			if ( $('.usage').hasClass('active') ) {
				$('.usage').removeClass('active');
			}
		}
		
		//进入到无需账号密码区域导航自动高亮
		var choiceStart = $('#choiceness').offset().top - navHeight;
		var choiceEnd = $('#choiceness').offset().top + $('#choiceness').outerHeight() - navHeight;
		
		if (sTop >= choiceStart && sTop < choiceEnd) {
			if ( !$('.choice').hasClass('active') ) {
				$('.choice').addClass('active');
			}
		} else {
			if ( $('.choice').hasClass('active') ) {
				$('.choice').removeClass('active');
			}
		}
		
	});
	
	//回到顶部功能
	topcontrol.click(function() {
		
		$('html,body').animate({scrollTop:0},1000);
		
	});
	
	//网页锚点链接切换过渡
	$('.nav-wrap a').click(function() {
		
		//获得板块相对于网页原点的位置
		var top = $(this.hash).offset().top - navHeight + 7;
		
		$('html,body').animate({scrollTop:top},1000);
		
		return false;
	});
	
});
