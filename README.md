# Examples demonstrating the use of RabbitMQ

#### *There are several examples here, each lies in a separated package with its own instruction.*
#### *These instructions assume that you are using IntelliJ IDEA and login as admin `cc-admin`, password is `taxi123`.*

## 1. Install RabbitMQ
#### Start Docker engine or Docker desktop on your machine.
#### Build the Docker image:
```
docker build -t ubuntu-rabbitmq .
```
---
#### Create a container:
```
docker run -d --hostname my-rabbit --name my-rabbit -p 5672:5672 -p 15672:15672 -it ubuntu-rabbitmq bash
```
---
## 2. Configure and start RabbitMQ
#### Get access to the container:
```
docker exec -it my-rabbit bash
```
---
#### Run `config.sh`:
```
./config.sh
```
---
#### Go to the web-UI management console:
```
http://localhost:15672
```
---