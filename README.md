# PartyGames

Setup:

Install mssql from here : https://docs.microsoft.com/en-us/sql/ssms/download-sql-server-management-studio-ssms?redirectedfrom=MSDN&view=sql-server-ver15\
Install docker: https://docs.docker.com/desktop/windows/install/\ 
docker pull mcr.microsoft.com/mssql/server:2019-latest (sudo)
docker run -e "ACCEPT_EULA=Y" -e "SA_PASSWORD=Password1234!" -p 1433:1433 --name sql1 -h sql1 -d mcr.microsoft.com/mssql/server:2019-latest

-- always check the docker image(running before you start app)


MSSQL establish connection to see the database:
Authentification: SQL Server Auth
Server type: Database Engine
Server name: localhost,1433
Login: SA
Password: Password1234!

Create new datababase on mssql;

On the demo project update the following parameters:
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=<the db you created>
spring.datasource.username=SA
spring.datasource.password=Password1234!


