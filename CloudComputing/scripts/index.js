$( document ).ready(function() {
	$(".signin-form").submit(function (e) {
	    var email = $("#email").val();
	    var password = $("#password").val();
	    var dataString = 'email=' + email + '&password=' + password;

	    
	    $.ajax({
            type: "POST",
            url: "SignIn",
            data: dataString,
            success: function () {
             console.log("submited");   
            }
        });
	
	    return false;
	});

});