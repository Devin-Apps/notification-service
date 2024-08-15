# Notification Service

![CI Status](https://github.com/Devin-Apps/notification-service/actions/workflows/ci.yml/badge.svg)
![CD Status](https://github.com/Devin-Apps/notification-service/actions/workflows/cd.yml/badge.svg)

This is a containerized notification service application built with Java and Spring Boot. It uses Twilio for sending SMS notifications.

## Prerequisites

- Docker
- Docker Compose

## Building the Docker Image

To build the Docker image for the notification service, run the following command:

```bash
./build.sh
```

This script uses Docker Compose to build the image based on the Dockerfile in the project root.

## Running the Containerized Application

Before running the application, make sure to set up your environment variables:

1. Copy the `.env.example` file to `.env`:
   ```bash
   cp .env.example .env
   ```

2. Edit the `.env` file and replace the placeholder values with your actual Twilio credentials.

To run the containerized application, use the following command:

```bash
./run.sh
```

This script will check for the presence of the `.env` file, create it if it doesn't exist, and then start the container using Docker Compose.

## Using Docker Compose

The `docker-compose.yml` file in the project root defines the service and its configuration. To start the service and any dependencies using Docker Compose, run:

```bash
docker-compose up
```

This command will start the notification service and expose it on port 8080.

To stop the service, use:

```bash
docker-compose down
```

## Health Check

The application includes a health check endpoint at `http://localhost:8080/healthcheck`. The Docker container is configured to use this endpoint to monitor the application's health.

## Environment Variables

The following environment variables are required:

- `TWILIO_ACCOUNT_SID`: Your Twilio account SID
- `TWILIO_AUTH_TOKEN`: Your Twilio auth token
- `TWILIO_PHONE_NUMBER`: Your Twilio phone number for sending SMS

These variables should be set in the `.env` file.

## Troubleshooting

If you encounter any issues, please ensure that:

1. Docker and Docker Compose are properly installed and running on your system.
2. The `.env` file exists and cont
