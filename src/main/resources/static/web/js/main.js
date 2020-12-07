$(function() {
    $('.submitbutton').click(function () {
        submitButton = $(this).attr('name')
    });
});

$('#logout-form').on('submit', function (event) {
    event.preventDefault();
    $.post("/api/logout")
        .done(function () {
            console.log("logout ok");
            $("#logoutState").text("Logout Success!");
            setTimeout( function(){ location.href = "/web/login.html"; }, 1000);
        })
        .fail(function () {
            console.log("logout fails");
        })
        .always(function () {
        });
});