$(function() { loadData(); });

function getParameterByName(name) {
    var match = RegExp('[?&]' + name + '=([^&]*)').exec(window.location.search);
    return match && decodeURIComponent(match[1].replace(/\+/g, ' '));
};

function loadData(){
    $.get('/api/game_view/'+getParameterByName('gp'))
        .done(function(data) {
            $('#playerMe').text(data.me.username);

            if(data.rival != null){
                $('#playerRival').text(data.rival.username);
            }
            else {
                $('#playerRival').text("No rival yet!");
            }
            $('#created').text("Game started at: " + data.created);
        })
        .fail(function( jqXHR, textStatus ) {
          alert( "Failed to load gameboard: " + textStatus );
        });
};