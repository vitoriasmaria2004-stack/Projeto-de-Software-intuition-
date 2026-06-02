# Intuition

A markdown editor with some nice features

## Stack

- back-end: Spring Boot (Java)
- Front-end: React + vite (Node.js)
- Database: In memory (Hashmap on backend)

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

# Features

## Complete features
- Login/Register
- Markdown file editor
- External image insertion
- Math katex notation
- Share copy with another user
- Create folder and subfolders

## Incomplete features
- Manual graph creation (backend only)
- Auto graph creation (not complete on backend)

# Project structure
- ```entities``` - the fundamental entities of the application
  -   User
  -   File (Abstract)
  -   Folder
  -    Document
  -   Edge
  -   Vertex
- ```repositories``` - classes that are responsible for manage collections of entities (is what would connect to the database, if there was any)
- ```controllers``` - manage HTTP requests and call the repository to return the requested information or create object informed on the request body

# Objected Orientation information
## Inheritance
In this project I abstracted the common properties between a folder and a editable document as a abstract class called ```File```, then the classed of ```Folder``` and ```Document``` inherit most of their attributes from File

<img width="772" height="632" alt="carbon" src="https://github.com/user-attachments/assets/13ccd8b0-2616-4996-81a2-ddd0dac6e71f" />
&nbsp;
<img width="908" height="446" alt="inheritance2" src="https://github.com/user-attachments/assets/07a80593-1cb4-4087-9660-39c940c053c2" />
&nbsp;
<img width="874" height="446" alt="inheritance1" src="https://github.com/user-attachments/assets/1b9721ac-8ab6-4a08-8dc6-fa6c3714d08a" />
&nbsp;

## Polymorphism

In the File class there are two abstract methods that are implemented differently in both Folder and Document

<img width="874" height="520" alt="poly1" src="https://github.com/user-attachments/assets/7dd6e045-68f2-483e-991c-e52ef2c87d56" />
&nbsp;
<img width="1666" height="894" alt="poly2" src="https://github.com/user-attachments/assets/b0c4c0a9-8356-4f7f-8cfa-30f2a053ba6a" />
&nbsp;
<img width="908" height="782" alt="poly3" src="https://github.com/user-attachments/assets/7a26c56b-677b-425e-b45c-90daa337ccbc" />
&nbsp;

These methods are called on the ```FileRepository``` class. Note that in both these calls we do not specify which type of object is being used, whether is a Folder or a  Document

<img width="1684" height="1006" alt="carbon(1)" src="https://github.com/user-attachments/assets/1740d43d-3d9c-4326-beb1-9bf04f3c917b" />

## PADRÃO CRIACIONAL : Singleton


