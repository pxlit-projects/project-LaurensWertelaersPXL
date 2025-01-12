# Architecture

:heavy_check_mark:_(COMMENT) Add a description of the architecture of your application and create a diagram like the one below. Link to the diagram in this document._
![architectuur](https://github.com/user-attachments/assets/1d81ab82-139f-41d5-8542-dea1613d112f)

## Synchronous communications:
### Newsarticle-service:
-Is used by reviewservice when approving or disapproving a newsarticle.
-Is used by commentservice when creating a comment.

#### Review-service:
-When approving or disapproving a newsarticle, the reviewservice calls the newsarticleservice to change the articlestatus in the newsarticleservice.
-When approving or disapporing a newsarticle, the reviewservice calls the notificationservice to create a notification in the notificationservice.

#### Comment-service:
-When creating a comment, the commentservice calls the newsarticleservice to check if the newsarticle exists and if its status is approved.

#### Notification-service:
-Is used by reviewservice when approving/disapproving to create a notification in the notificationservice.

## API Gateway-service
-All microservices use this microservice to expose their APIs.

## Config-service
-All microservices use this microservice to get their configuration files from on startup.

## Databases
-Each microservice uses a MySQL database that is each running in a docker container. These databases are started by the docker-compose.yml file.



![STD van een NewsArticle](https://github.com/user-attachments/assets/194b86cd-7bd3-4150-94f2-1fb57d071f76)


