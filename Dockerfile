# Use the official Ubuntu base image
FROM ubuntu:latest

# Set environment variables to non-interactive (prevents some prompts)
ENV DEBIAN_FRONTEND=non-interactive

# Copy the two shell scripts to the container
COPY ./*.sh .

# Make startup.sh executable
RUN chmod +x ./startup.sh && \
    chmod +x ./config.sh && \
    sed -i -e 's/\r$//' startup.sh && \
    sed -i -e 's/\r$//' config.sh && \
    ./startup.sh

# Expose ports
EXPOSE 5672 15672
