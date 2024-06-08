# Redis Server

This repository contains a Redis server trial implementation with RESP (REdis Serialization Protocol) messaging. The server is developed using Java and is aimed at experimenting with Redis functionalities and RESP messaging protocols.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)

## Overview

This project demonstrates a basic implementation of a Redis-like server. It handles client requests using the RESP messaging protocol, commonly used by Redis. The server processes commands, stores data in memory, and responds according to the Redis protocol.
The code is implemented using the command pattern, where each command is represented as an object. The server receives the command object, executes it, and returns the response to the client.

## Features

- **RESP Protocol**: Supports basic Redis commands using RESP.
- **Java Implementation**: Developed in Java, leveraging its concurrency features for handling multiple clients.
- **In-Memory Data Storage**: Stores data in memory for quick access.
- **Functionalities**: The server supports basic Redis commands such as `GET`, `SET`, `PING`, `INCR`, `DECR`, `ECHO`, `DBSIZE` and `QUIT`. 

## Installation

To run this project, ensure you have Java installed on your machine. Clone the repository and use Maven to build the project.

```sh
git clone https://github.com/MahmoudAbdelkhaleq/Redis-Server.git
cd Redis-Server
mvn clean install
```

## Usage
1. Start the server by running the `Server` class.
2. Run one or multiple instances of the `Client` class to connect to the server.
3. Send commands to the server using the client instances.
```sh
Client> PING
Server> PONG
```
```sh
Client> GET key
Server> (nil)
```
```sh
Client> SET key value
Server> OK
```
```sh
Client> GET key
Server> value
```
