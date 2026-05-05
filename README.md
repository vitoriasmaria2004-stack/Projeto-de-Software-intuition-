# Intuition

A markdown editor with some nice features

## Stack

- back-end: Spring Boot (Java)
- Front-end: React + vite (Node.js)

# Running

## Prerequisites

Make sure you have a JVM and Node.js installed on your computer

Firstly clone the repository, thats already half the job done. After that enter the ```my-notes-app``` folder, then run ```npm install```

## Running both back and front end

To start the backend run the follow command while on the general project folder

```
./gradlew bootRun
```

On another terminal window or tab run the following command to start the frontend

```
npm run dev
```

OBS: This frontend command is ideal for development environments, on deploy another command must be used
