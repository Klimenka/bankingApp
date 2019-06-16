
async function GetUsers(){
  const specURL = "https://raw.githubusercontent.com/Klimenka/sawagger_spec/master/openapi.yaml"
	
  let swaggerClient = await new SwaggerClient(specURL)
  
  swaggerClient.http.withCredentials = true; 
  let result = await swaggerClient.apis.users.getUsers()
  let resultsObject = JSON.parse(JSON.stringify(result.obj))
  console.log(resultsObject)
  return resultsObject
  
}
async function GetEmployees(){
  const specURL = "https://raw.githubusercontent.com/Klimenka/sawagger_spec/master/openapi.yaml"
	
  let swaggerClient = await new SwaggerClient(specURL)
  
  swaggerClient.http.withCredentials = true; 
  let result = await swaggerClient.apis.users.getUsers({userType:"Employee"})
  let resultsObject = JSON.parse(JSON.stringify(result.obj))
  console.log(resultsObject)
  return resultsObject
  
}
async function GetCustomers(){
  const specURL = "https://raw.githubusercontent.com/Klimenka/sawagger_spec/master/openapi.yaml"
	
  let swaggerClient = await new SwaggerClient(specURL)
  
  swaggerClient.http.withCredentials = true; 
  let result = await swaggerClient.apis.users.getUsers({userType:"Customer"})
  let resultsObject = JSON.parse(JSON.stringify(result.obj))
  console.log(resultsObject)
  return resultsObject
  
}
async function GetOneUser(id){
  const specURL = "https://raw.githubusercontent.com/Klimenka/sawagger_spec/master/openapi.yaml"
  console.log(id)
  let swaggerClient = await new SwaggerClient(specURL)
  
  let result = await swaggerClient.apis.users.getUserById({userId:id})
  let resultsObject = JSON.parse(JSON.stringify(result.obj))
 
  return resultsObject
  
}
async function DeleteUser(id){
  const specURL = "https://raw.githubusercontent.com/Klimenka/sawagger_spec/master/openapi.yaml"
  console.log(id)
  let swaggerClient = await new SwaggerClient(specURL)
  
  let result = await swaggerClient.apis.users.deleteUserById({userId:id})

}

 
 
