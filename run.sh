#!/bin/bash

# Check if .env file exists, if not, copy from .env.example
if [ ! -f .env ]; then
    echo "Creating .env file from .env.example"
    cp .env.example .env
    echo "Please update the .env file with your actual credentials before running the application."
    exit 1
fi

# Run the Docker container
docker-compose up
