# cake order
1. Start the application CakeserviceApplication.java
   Application is available on port 8080.
   Booting will insert few users, cakes and roles into h2 DB.
2. Please open h2 console to check data on db. http://localhost:8080/h2-console
    Populate JDBC URL with "jdbc:h2:mem:waracledb"
3. I added security layer to authenticate and authorise with roles added
   VIEW : can access get api only
   ADMIN: can access post apis to add user / assign role to user., etc
4. Written test cases for code coverage for controller and service layers.
5. Written global exception handler.
6. Logging added wherever it is applicable.

7. To test apis we need to fetch JWT token and provide that to api in Headers as Authorisation
   test Accounts :
   "admin" has role ADMIN
   "user" has role VIEW should give 403 when trying to access POST apis


to get token :
api: POST
http://localhost:8080/api/login
Request: add below two params in body
eg:
    username: user
    password: 1234
Response:

        {
            "access_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwicm9sZXMiOlsiUk9MRV9FTVAiXSwiaXNzIjoiL2FwaS9sb2dpbiIsImV4cCI6MTYzOTY1MTQ3Nn0.jV5ZYlSpJEO3fSvI_6BNiWuGdHtaml7dHd_VA1x-GBo",
            "refresh_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiaXNzIjoiL2FwaS9sb2dpbiIsImV4cCI6MTYzOTY1MjY3Nn0.T5lqabuh0PhiFl2JDcQVTyS3R_2GyAcg_FI-u_jkbwM"
        }
copy access_token (valid for 10 mins)

to get cakes by client Name:
api: GET
http://localhost:8080/api/v1/cakes/{clientName}
eg: clientName="Ravi"

     headers: Bearer <access_token>
     Response:
     Success :

     {
    "status": "Success",
    "records": 3,
    "cakes": [
        {
            "id": 1,
            "name": "Red Velvet",
            "client": {
                "clientId": 1,
                "name": "Ravi"
            }
        },
        {
            "id": 2,
            "name": "Black Forest",
            "client": {
                "clientId": 2,
                "name": "Ravi"
            }
        },
        {
            "id": 3,
            "name": "Chocolate",
            "client": {
                "clientId": 3,
                "name": "Ravi"
            }
        }
    ]
}
     If client name doesn't exists in DB then response should be like as below:
{
"status": "failed",
"errorMessage": "Cakes does not exist for provided client nameRavi-1",
"records": 0,
"cakes": []
}


Get http://localhost:8080/api/v1/

{
"status": "Success",
"records": 6,
"cakes": [
{
"id": 1,
"name": "RED VELVET",
"clientName": "RAVI"
},
{
"id": 2,
"name": "BLACK FOREST",
"clientName": "RAVI"
},
{
"id": 3,
"name": "CHOCOLATE",
"clientName": "RAVI"
},
{
"id": 4,
"name": "BELGIUM CHOCOLATE",
"clientName": "JOHN"
},
{
"id": 5,
"name": "RED VELVET",
"clientName": "JOHN"
},
{
"id": 6,
"name": "BLACK FOREST",
"clientName": "JOHN"
}
]
}

Get token for 'Admin'
add cake
Post: http://localhost:8080/api/v1/cakes
Request body :
{
"name": "Blackcurant cake",
"clientName": "Shreya"
}

Response:
{
"id": 7,
"name": "BLACKCURANT CAKE",
"clientName": "SHREYA"
}