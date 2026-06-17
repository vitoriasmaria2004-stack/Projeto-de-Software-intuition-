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
O Singleton foi utilizado para assegurar que um recurso essencial da aplicação seja gerenciado por uma única instância. Com isso, o sistema mantém um comportamento consistente e evita a criação desnecessária de múltiplos objetos com a mesma responsabilidade.

PROBLEMA que  o Singleton resolveu:
Sem o Singleton, diferentes partes do sistema poderiam criar repositórios independentes, cada um com seu próprio conjunto de usuários. Isso poderia causar inconsistências como Usuários duplicados,IDs conflitantes,Dados diferentes em cada repositório . Com o Singleton, todos os componentes utilizam a mesma instância do repositório.

Implementado no UserRepository com getInstance()

## PADRÃO ESTRUTURAL : PROXY
O Proxy foi utilizado para criar uma camada intermediária entre os controladores e o repositório real de usuários.

PROBLEMA  que o Proxy resolveu: Sem o Proxy, os controladores acessavam diretamente o repositório, sem nenhum controle ou monitoramento. Isso dificultava Registrar logs de acesso,Validar permissões antes das operações,Adicionar verificações de segurança,Implementar cache de consultas.Com o Proxy, todas as operações passam por um ponto central onde é possível adicionar verificações, logs de auditoria e validações sem modificar o repositório original.

Implementado no UserRepositoryProxy como intermediário

## PADRÃO COMPORTAMENTAL : COMMAND
O Command foi utilizado para transformar cada operação do sistema (criar, editar e deletar usuários) em objetos independentes que encapsulam toda a informação necessária para executar e desfazer a ação. Um gerenciador central (CommandHistory) mantém o histórico de todas as operações realizadas, permitindo desfazer e refazer ações.

PROBLEMA que o Command resolveu: Sem o Command, o sistema não possuía histórico de operações, o que significava que um usuário criado por engano não podia ser desfeito facilmente;não era possível refazer uma operação desfeita;não havia registro do que foi alterado e em qual ordem Erros do usuário não podiam ser revertidos. Com o Command, o sistema mantém duas pilhas (undo e redo) que armazenam todas as operações realizadas, permitindo ao usuário desfazer ações indesejadas com Ctrl+Z e refazê-las com Ctrl+Y.

Implementado na pasta commands para controle de Undo/Redo

