# This is an example demonstrating the use of RabbitMQ

## 1. Install and set up
#### Start Docker engine or Docker desktop on your machine.
#### Build the Docker image:
```
docker build -t ubuntu-rabbitmq .
```
---
#### Create a container:
```
docker run -d --hostname my-rabbit --name my-rabbit --mount type=bind,src="$(pwd)",target=/app -p 5000:15672 -it ubuntu-rabbitmq bash
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
http://localhost:5000
```
---
#### Login as the admin `cc-admin` or the development user `cc-dev`. Password is `taxi123`