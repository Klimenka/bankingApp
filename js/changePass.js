//$(document).ready(function () {
//    var frm = $('#resetform');
//    frm.submit(function (e) {
//        e.preventDefault();
//
//        var formData = frm.serialize();
//        formData += '&' + $('#submit_btn').attr('name') + '=' + $('#submit_btn').attr('value');
//        $.ajax({
//            type: 'POST',
//            url: frm.attr('action'),
//            data: formData,
//            success: function (data) {
//                $('#message').html(data).delay(3000).fadeOut(3000);
//            },
//            error: function (jqXHR, textStatus, errorThrown) {
//                $('#message').html(textStatus).delay(2000).fadeOut(2000);
//            }
//
//        });
//
//    });
//});


$(document).on("submit", "#resetform", function (e) {
    e.preventDefault();
    //var data = $(this).serializeFormJSON();
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