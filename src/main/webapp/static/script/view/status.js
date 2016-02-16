

$(function () {
	console.log('in menu');
    $('.nav-tabs>li>a').click(function (e) {
    	console.log("menu clicked")
        $(this).addClass('active').siblings().removeClass('active');
         // e.preventDefault();
    });
});
