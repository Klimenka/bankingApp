


$(document).on("submit", "#resetform", function (e) {
    e.preventDefault();
  
    var username = document.getElementById('username').value;
    var password = document.getElementById('newPassword').value;
    var userId = document.getElementById('userId').value;
    var data = {
        "userName": username,
        "password": password
    };
    $.ajax({
        type: "PUT",
        url: "https://banking-application-api.herokuapp.com/users/" + userId + "/updatePassword",
        data: JSON.stringify(data),
        contentType: "application/json",
        success: function (data) {
            console.log(data);
            $('#message').html(data).delay(3000).fadeOut(3000);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            $('#message').html(textStatus).delay(2000).fadeOut(2000);
        }
    });
});