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
	$('#include-dead-states').change(function() {
		var show = $(this).prop('checked');

		if (show) {
			$('.state-dead').show();
		} else {
			$('.state-dead').hide();
		}
	});
});
