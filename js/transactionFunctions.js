//this is used to retrieve a specific transaction from the user
async function getTransactionById(id){
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
async function getTransactionsByAccountName(){
    const specURL = "https://raw.githubusercontent.com/AliYussef/Swagger/master/Swagger_API_Spec.yaml";
  
    let swaggerClient = await new SwaggerClient(specURL);
    
    let result = await swaggerClient.apis.transactions.getTransactionHistory({accountNumber: 'NL49INHO0000000003'})
    let resultsObject = JSON.parse(JSON.stringify(result.obj))
    return resultsObject
}

var userPerforming = 0;
//this is used to retrieve information about the user
async function getUserPerformingById(id)
{
    const specURL = "https://raw.githubusercontent.com/Klimenka/sawagger_spec/master/openapi.yaml"

    let swaggerClient = await new SwaggerClient(specURL)
  
    let result = await swaggerClient.apis.users.getUserById({userId: id})
    let resultsObject = JSON.parse(JSON.stringify(result.obj))
    userPerforming = resultsObject;
    return resultsObject
}
getUserPerformingById(3);

//this is used to get the date of the transaction
function getDateOfToday()
{
    var date = new Date();

    var day = date.getDate();
    var month = date.getMonth()+1;
    var year = date.getFullYear();

    if (month < 10) month = "0" + month;
    if (day < 10) day = "0" + day;

    var today = year + "-" + month + "-" + day;    
    return today;
}

//this is used to create a transaction
$(document).on("submit", "#createTransactionForm", function (e) {
    e.preventDefault();

    var user = JSON.stringify(userPerforming);

    var accountFromValue = document.getElementById('accountFrom').value;
    var accountToValue = document.getElementById('accountTo').value;
    var amountValue = document.getElementById('amount').value;
    var date = getDateOfToday()

    $.ajax({
        url: 'https://banking-application-api.herokuapp.com/transactions',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify( { "accountFrom": accountFromValue, "accountTo": accountToValue, "amount": amountValue,
        "userPerforming": user, "date": date, "transactionType": 'transaction'} ),
        success: function(data){
            $('#message').html(data).delay(3000).fadeOut(3000);
        },
        error: function( jqXhr, textStatus, errorThrown ){
            $('#message').html(textStatus).delay(2000).fadeOut(2000);
        }
    });
});

//this is used to perform a withdraw
$(document).on("submit", "#createWitdrawForm", function (e) {
    e.preventDefault();
    var accountFromValue = document.getElementById('accountFrom').value;
    var amountValue = document.getElementById('amount').value;
    var user = JSON.stringify(userPerforming);
    var date = getDateOfToday()

    $.ajax({
        url: 'https://banking-application-api.herokuapp.com/transactions',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify( { "accountFrom": accountFromValue, "accountTo": null, "amount": amountValue, "userPerforming": user,
        "date": date.value, "transactionType": 'withdraw'} ),
        success: function( data){
            $('#message').html(data).delay(3000).fadeOut(3000);
        },
        error: function( jqXhr, textStatus, errorThrown ){
            $('#message').html(textStatus).delay(2000).fadeOut(2000);
        }
    });
});

//this is used to perform a deposit
$(document).on("submit", "#createDepositForm", function (e) {
    e.preventDefault();
    var accountFromValue = document.getElementById('accountFrom').value;
    var amountValue = document.getElementById('amount').value;
    var user = JSON.stringify(userPerforming);
    var date = getDateOfToday()

    $.ajax({
        url: 'https://banking-application-api.herokuapp.com/transactions',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify( { "accountFrom": accountFromValue, "accountTo": null, "amount": amountValue, "userPerforming": user,
        "date": date.value, "transactionType": 'deposit'} ),
        success: function( data){
            $('#message').html(data).delay(3000).fadeOut(3000);
        },
        error: function( jqXhr, textStatus, errorThrown ){
            $('#message').html(textStatus).delay(2000).fadeOut(2000);
        }
    });
});