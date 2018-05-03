$( document ).ready(function() {
	$("#create-app-button").on("click", function() {
		console.log("does it find element");
		if ($("#create-app-button").hasClass("hidden-form")) {
			$("#create-app-button").removeClass("hidden-form");
			$("#create-app-button").html("Hide form");
			$(".add-app-form").show();
			$(".apps").hide();
		} else {
			$("#create-app-button").html("Show form");
			$(".add-app-form").hide();
			$(".apps").show();
			$("#create-app-button").addClass("hidden-form");
		}
	})

});


