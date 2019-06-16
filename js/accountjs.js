async function getBankAccounts(accountType, dateOfopening) {
    const specURL = "https://raw.githubusercontent.com/AliYussef/Swagger/master/Swagger_API_Spec.yaml"
    SwaggerClient.http.withCredentials = true;
    let swaggerClient = await new SwaggerClient(specURL);
    let result = await swaggerClient.apis.accounts.getBankAccounts({accountType: accountType, dateOfOpening: dateOfopening});
    let resultsObject = JSON.parse(result.data);

    $.each(resultsObject, function (i, f) {
        var tblRow = "<tr>" + "<td>" + resultsObject[i].accountNumber + "</td>" +
                "<td>" + resultsObject[i].iban + "</td>" + "<td>" + resultsObject[i].balance +
                "</td>" + "<td>" + resultsObject[i].dateOfOpening + "</td>" +
                "<td>" + resultsObject[i].accountType + "</td>" + "<td>" + resultsObject[i].accountStatus +
                "</td>" + "</tr>"
        $(tblRow).appendTo("#mydata tbody");
    });
}

async function getBankAccount(accountType, userId) {
    const specURL = "https://raw.githubusercontent.com/AliYussef/Swagger/master/Swagger_API_Spec.yaml"
    SwaggerClient.http.withCredentials = true;
    let swaggerClient = await new SwaggerClient(specURL);
    let result = await swaggerClient.apis.accounts.getBankAccount({userId: userId, accountType: accountType});
    let resultsObject = JSON.parse(result.data);

    $.each(resultsObject, function (i, f) {
        var tblRow = "<tr>" + "<td>" + resultsObject[i].accountNumber + "</td>" +
                "<td>" + resultsObject[i].iban + "</td>" + "<td>" + resultsObject[i].balance +
                "</td>" + "<td>" + resultsObject[i].dateOfOpening + "</td>" +
                "<td>" + resultsObject[i].accountType + "</td>" + "<td>" + resultsObject[i].accountStatus +
                "</td>" + "</tr>"
        $(tblRow).appendTo("#mydataOne tbody");
    });
}

function closeBankAccount(accountNumber) {
    var specUrl = 'https://raw.githubusercontent.com/AliYussef/Swagger/master/Swagger_API_Spec.yaml';
    SwaggerClient.http.withCredentials = true; // this activates CORS, if necessary
    var swaggerClient = new SwaggerClient(specUrl)
            .then(function (swaggerClient) {
                return swaggerClient.apis.accounts.closeBankAccount({accountNumber: accountNumber});
            }, function (reason) {
                console.error("failed to load the spec" + reason);
            })
            .then(function (result) {
                console.log(result.obj);
                $("#messageClose").show();
                $("#messageClose").delay(5000).fadeOut(500);

            }, function (reason) {
                console.error("failed on API call " + reason);
            });
}

(function ($) {
    $.fn.serializeFormJSON = function () {

        var o = {};
        var a = this.serializeArray();
        $.each(a, function () {
            if (o[this.name]) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return o;
    };
})(jQuery);

$(document).on("submit", "#idForm", function (e) {
    e.preventDefault();
    var data = $(this).serializeFormJSON();
    $.ajax({
        type: "POST",
        url: "https://banking-application-api.herokuapp.com/accounts",
        data: JSON.stringify(data),
        contentType: "application/json",
        success: function (data)
        {
            var tblRow = "<tr>" + "<td>" + data.accountNumber + "</td>" +
                    "<td>" + data.iban + "</td>" + "<td>" + data.balance +
                    "</td>" + "<td>" + data.dateOfOpening + "</td>" +
                    "<td>" + data.accountType + "</td>" + "<td>" + data.accountStatus +
                    "</td>" + "</tr>"
            $(tblRow).appendTo("#mydataCreate tbody");
        }
    });
});

function openAccountFunctionalty(func) {
    var i;
    var x = document.getElementsByClassName("AccountFunc");
    for (i = 0; i < x.length; i++) {
        x[i].style.display = "none";
    }
    document.getElementById(func).style.display = "block";
}

function getFormData() {
    var accountType = document.getElementById('accountType').value;
    var dateOfopening = document.getElementById('date').value;
    var userID = document.getElementById('userId').value;

    if (userID != "") {
        var accountType2 = document.getElementById('accountType2').value;
        getBankAccount(accountType2, userID);
        document.getElementById('userId').value = "";
        document.getElementById('accountType').value = "";
        $('#mydataOne').load(document.URL + ' #mydataOne');
    } else {
        getBankAccounts(accountType, dateOfopening);
        document.getElementById('date').value = "";
        document.getElementById('accountType').value = "";
        $('#mydata').load(document.URL + ' #mydata');
    }
}
$(document).on("submit", "#theform", function (e) {
    e.preventDefault();
    if (document.getElementById('accountNumber').value != "") {
        closeBankAccount(document.getElementById('accountNumber').value);
        document.getElementById('accountNumber').value = "";
    } else {
        getFormData();
    }
});

















