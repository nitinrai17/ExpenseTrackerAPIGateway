#This is the User Rest Api.
// need to complete this 


##Docker command 

###Build: 
 docker build -f Dockerfile . 

###Run : 
docker run -p 8100:8100 <buildname>


### Compile command 
gradlew.bat build 

### Run Springboot 
gradlew.bat bootRun 





SignUp 

URL POST Method  :- http://localhost:8100/api/v1/auth/signup

Request Body sample 

{
    "name":"Nitin",
    "email":"nitin@gmail.com",
    "password":"1234"
}

Response :- 
{
    "message": "User successfully registered",
    "success": true
}


Login 

URL POST Method  :- http://localhost:8100/api/v1/auth/login

Request Body Sample:- 

{
    "email":"nitin@gmail.com",
    "password":"1234"
}

Response:- 
{
    "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjAyNjU2MTQ5LCJleHAiOjE2MDI3NDI1NDl9.Xa2qjt2DKGfwknL1MnYpPz4xjXHoogRNkGdThegTnExEzV_RdgI6rXoQjnutbfoZok8__tGosjBc6pkCVMvY_Q",
    "tokenType": "Bearer"
}
