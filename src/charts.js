$(function() {
	$('.state-ref').hover(function () {
		// hover in
		var me = $(this);
		var target = $('#' + me.data('target'));
		target.addClass('highlight');
	}, function() {
		// hover out
		var me = $(this);
		var target = $('#' + me.data('target'));
		target.removeClass('highlight');
	});
});
