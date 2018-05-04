$( document ).ready(function() {
	$("#create-app-button").on("click", function() {
		if ($("#create-app-button").hasClass("hidden-form")) {
			$("#create-app-button").removeClass("hidden-form");
			$("#create-app-button").html("Hide create app form");
			$(".add-app-form").show();
			$(".send-peanuts-form").hide();
			$("#send-peanuts-button").addClass("hidden-form");
			$("#send-peanuts-button").html("Show peanuts form");
			$(".apps").hide();
		} else {
			$("#create-app-button").html("Show create app form");
			$(".add-app-form").hide();
			$(".apps").show();
			$("#create-app-button").addClass("hidden-form");
		}
	})
	
	$("#send-peanuts-button").on("click", function() {
		if ($("#send-peanuts-button").hasClass("hidden-form")) {
			$("#send-peanuts-button").removeClass("hidden-form");
			$("#send-peanuts-button").html("Hide peanuts form");
			$(".send-peanuts-form").show();
			$(".add-app-form").hide();
			$("#create-app-button").addClass("hidden-form");
			$("#create-app-button").html("Show create app form");
			$(".apps").hide();
		} else {
			$("#send-peanuts-button").html("Show peanuts form");
			$(".send-peanuts-form").hide();
			$(".apps").show();
			$("#send-peanuts-button").addClass("hidden-form");
		}
	})

});


