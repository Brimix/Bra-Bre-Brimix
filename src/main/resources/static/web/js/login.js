$(function() {
    $('.submitbutton').click(function () {
        submitButton = $(this).attr('name')
    });
});

$('#login-form').on('submit', function (event) {
    event.preventDefault();

    if (submitButton == "login") {
        $.post("/api/login",
            { name: $("#username").val(),
                pwd: $("#password").val() })
            .done(function() {
                console.log("login ok");
                $('#loginSuccess').show( "slow" ).delay(2000).hide( "slow" );
                $("#username").val("");
                $("#password").val("");
                // $("#createGameForm").show();
                $("#loginState").text("Login Successful!");
                setTimeout( function(){ location.href = "/web/main.html"; }, 3000);
            })
            .fail(function() {
                console.log("login failed");
                $('#loginFailed').show( "slow" ).delay(2000).hide( "slow" );
                $("#username").val("");
                $("#password").val("");
                //$("#username").focus();
                //$('#loginFailed').hide( "slow" );
                $("#loginState").text("Login failed! Bad credentials!");
            })
            .always(function() {

            });

    }/* else if (submitButton == "signup") {
        $.post("/api/players",
            { email: $("#username").val(),
                password: $("#password").val() })
            .done(function(data) {
                console.log("signup ok");
                console.log(data);
                $('#signupSuccess').show( "slow" ).delay(2000).hide( "slow" );
                $.post("/api/login",
                    { name: $("#username").val(),
                        pwd: $("#password").val() })
                    .done(function() {
                        console.log("login ok");
                        $('#loginSuccess').show( "slow" ).delay(2500).hide( "slow" );
                        $("#username").val("");
                        $("#password").val("");
                        updateJson();

                    })
                    .fail(function() {
                        console.log("login failed");
                        $('#loginFailed').show( "slow" ).delay(2000).hide( "slow" );
                        $("#username").val("");
                        $("#password").val("");
                        $("#username").focus();
                        // $('#loginFailed').hide( "slow" );
                    })
                    .always(function() {

                    });
            })
            .fail(function(data) {
                console.log("signup failed");
                 //console.log(data);
                $("#username").val("");
                $("#password").val("");
                $("#username").focus();
                $('#errorSignup').text(data.responseJSON.error);
                $('#errorSignup').show( "slow" ).delay(3000).hide( "slow" );
            })
            .always(function() {

            });

    } */ else {
        //no button pressed
    }
});