Description:
===

This is just an example project to discuss some technologies for an interview.
There are currently maaany TODO'S in this project (See TODO section).

How to get started for development:
===

1. Prerequisites:
 - Java 11 JDK.
 - Maven >= 3.6.3
 - Node.JS v.12.x
 - Docker Desktop => 2.x.x.x (should also work with lower versions)


2. Starting the database:

```shell
cd backend
docker-compose up
```

3. Starting the backend:

```
mvn clean install
```
```
Open Intellij and jump to class: 

InterviewExampleProjectApplication.java 

and press the green start button on the left.
```

Hint: You can open http://localhost:4667/swagger-ui.html for the REST swagger documentation.

4. Starting the frontend (NPM preffered but yarn could also be used):

```
npm install
npm run start
```


TODO list:
===

1. Write scripts to automate the deployment process and build docker containers and pushing them to docker hub
2. Maybe use "Travis CI" or some other build tool to monitor builds and execute some steps for production
3. When starting the integration tests run a fresh docker container on start (integration tests will currently be always successfull) and maybe some clean up logic after every integration test
4. Write much more tests especially for the frontend and the backend 
5. More coming soon...