//this is used to retrieve a specific transaction from the user
async function getTransactionById(id) {
    const specURL = "https://raw.githubusercontent.com/AliYussef/Swagger/master/Swagger_API_Spec.yaml";

    let swaggerClient = await new SwaggerClient(specURL);

    var input = document.getElementById(id).value;

    let result = await swaggerClient.apis.transactions.getTransaction({transactionId: input})
    let resultsObject = JSON.parse(JSON.stringify(result.obj))

    var array = [];
    array.push(resultsObject);
    return array
}

//this is used to retrieve all transaction which the user has made
async function getTransactionsByAccountName() {
    const specURL = "https://raw.githubusercontent.com/AliYussef/Swagger/master/Swagger_API_Spec.yaml";

    let swaggerClient = await new SwaggerClient(specURL);

    let result = await swaggerClient.apis.transactions.getTransactionHistory({accountNumber: 'NL49INHO0000000003'})
    let resultsObject = JSON.parse(JSON.stringify(result.obj))
    return resultsObject
}

//this is used to retrieve information about the user
//async function getUserPerformingById(id)
//{
//    const specURL = "https://raw.githubusercontent.com/Klimenka/sawagger_spec/master/openapi.yaml";
//
//    let swaggerClient = await new SwaggerClient(specURL);
//
//    let result = await swaggerClient.apis.users.getUserById({userId: id});
//    let resultsObject = JSON.parse(result.data);
//    return resultsObject;
//}

function getUserPerformingById(id) {
    var specUrl = 'https://raw.githubusercontent.com/Klimenka/sawagger_spec/master/openapi.yaml';
    SwaggerClient.http.withCredentials = true; // this activates CORS, if necessary
    return swaggerClient = new SwaggerClient(specUrl)
            .then(function (swaggerClient) {
                return swaggerClient.apis.users.getUserById({userId: id});
            }, function (reason) {
                console.error("failed to load the spec" + reason);
            })
            .then(function (result) {
                console.log(result.obj);
                return result.obj;
            });
}


//this is used to get the date of the transaction
function getDateOfToday()
{
    var date = new Date();

    var day = date.getDate();
    var month = date.getMonth();
    var year = date.getFullYear();

    if (month < 10)
        month = "0" + month;
    if (day < 10)
        day = "0" + day;

    var today = year + "-" + month + "-" + day;
    return today;
}

//this is used to create a transaction
$(document).on("submit", "#createForm", function (e) {
    e.preventDefault();
    var accountFromValue = document.getElementById('accountFrom').value;
    var accountToValue = document.getElementById('accountTo').value;
    var amountValue = document.getElementById('amount').value;

    var userPerforming = getUserPerformingById(3).then(function (result)
    {
        return result
    })

    var date = getDateOfToday()

    $.ajax({
        url: 'https://banking-application-api.herokuapp.com/transactions',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({"accountFrom": accountFromValue, "accountTo": accountToValue, "amount": amountValue, "userPerforming": userPerforming,
            "date": date.value, "transactionType": 'transaction'}),
        success: function (data) {
            $('#message').html(data).delay(3000).fadeOut(3000);
        },
        error: function (jqXhr, textStatus, errorThrown) {
            $('#message').html(textStatus).delay(2000).fadeOut(2000);
        }
    });
});