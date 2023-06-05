Showcase (deployed on Google Kubernetes Engine (GKE):
===

https://gke-test.ruecker.dev/

Description:
===

This is just an example project to discuss some technologies for an interview.
There are currently maaany TODO'S in this project (See TODO section).

How to get started for development:
===

1. Prerequisites:
 - Java 20 JDK (should also work with higher versions).
 - Maven >= 3.8.7 (should also work with lower versions)
 - Node.JS v16.13.2 (should also work with lower versions)
 - Docker Desktop => 2.x.x.x (should also work with lower versions)


2. Starting the database:

```shell
cd backend
docker-compose up
```

3. Starting the backend:

```
mvn clean package
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
1. Write Github action that is deploying the kubernetes files to an GKE cluster or another cloud instance
2. Write much more tests especially for the frontend and the backend
3. More coming soon...