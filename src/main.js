$(function() {
	$('.state-ref').hover(function() {
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
	$('.state-ref').dblclick(function() {
		var me = $(this);
		window.location.hash = '#' + me.data('target');
	});
	$('#include-dead-states').change(function() {
		var show = $(this).prop('checked');

		if (show) {
			$('.chart-dead').show();
		} else {
			$('.chart-dead').hide();
		}
		if (show) {
			$('.state-dead').show();
		} else {
			$('.state-dead').hide();
		}
		if (show) {
			$('.parameter-cause-dead').show();
		} else {
			$('.parameter-cause-dead').hide();
		}
	});
});
