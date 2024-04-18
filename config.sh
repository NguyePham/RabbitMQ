#!/bin/bash

# Start the rabbitMQ server as a service
systemctl enable rabbitmq-server
systemctl start rabbitmq-server
systemctl status rabbitmq-server

# Enable the web UI management console
rabbitmq-plugins enable rabbitmq_management

# Create an administrator
rabbitmqctl add_user cc-admin taxi123
rabbitmqctl set_user_tags cc-admin administrator

# Create a user for development purpose
rabbitmqctl add_user cc-dev taxi123

# Create a vhost called cc-dev-vhost
rabbitmqctl add_vhost cc-dev-vhost

# Give the two users full rights on the cc-dev-vhost vhost
rabbitmqctl set_permissions -p cc-dev-vhost cc-admin ".*" ".*" ".*"
rabbitmqctl set_permissions -p cc-dev-vhost cc-dev ".*" ".*" ".*"