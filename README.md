# Distributed Systems

The project aims to provide a simple implementation of a remote procedure call (RPC) server and client, as well as a socket client, using C programming language.

The rpc_server module serves as the server-side implementation of the RPC architecture. It provides an interface for registering functions that can be called remotely by the rpc_client. The server listens for incoming RPC requests, executes the corresponding registered function, and sends the response back to the rpc_client.

The rpc_client module allows users to make remote procedure calls to the rpc_server. It establishes a connection with the rpc_server and sends the requested function call along with any arguments. The client then waits for the response from the rpc_server and returns it to the socket_client.

The socket_client module is a basic implementation of a socket-based client. It provides a way to establish a connection with the rpc_client and exchange messages. The socket_client can send messages to the rpc_client and receive responses.

Also the project contains a simple implementation of a Theater Reservation System utilizing Java's Remote Method Invocation (RMI) feature. The system consists of a server and a client, enabling remote interaction for booking and cancelling seats in a theater.

## Prerequisites
To use the files in this repository, you will need the following:

RPC:
- [GCC](https://gcc.gnu.org/)
- [Make](https://man7.org/linux/man-pages/man1/make.1.html)
- [RPCBind](https://man7.org/linux/man-pages/man8/rpcbind.8.html)

Java RMI:
- [Java](https://openjdk.org/)

## Getting Started
To get started with this project, follow these steps:

For the RPC:
1. Clone this repository to your local machine.
2. Run the make command.
    ```
    make
    ```
3. Run rpc_server.
    ```
    sudo ./rpc_server
    ```
5. Run rpc_client.
    ```
    sudo ./rpc_client localhost 65001
    ```
7. Run socker_client.
    ```
    sudo ./rpc_client localhost 65001
    ```
For Java RMI:
1. Clone this repository to your local machine.
2. Compile.
    ```
    javac *.java
    ```
3. Run the server.
    ```
    java THServer
    ```
5. Run the client with one of the following options.
    ```
    java gr.uniwa.TΗClient
    java gr.uniwa.TΗClient list <hostname>
    java gr.uniwa.TΗClient book <hostname> <type> <number> <name>
    java gr.uniwa.TΗClient guests <hostname>
    java gr.uniwa.TΗClient cancel <hostname> <type> <number> <name>
    ```
    
## Contents
This repository contains the following files: 
1. Makefile
2. rpc.h
3. rpc.x
4. rpc_client.c
5. rpc_clnt.c
6. rpc_server.c
7. rpc_svc.c
8. rpc_xdr.c
9. socket_client.c
10. THServer.java
11. THClient.java
12. THImpl.java
13. THInterface.java

## Contributing

This is a university project so you can not contribute.

## Authors

* **[University of West Attica]** - *Provided the exersice*
* **[Achilleas Pappas]** - *Wrote the code*

## License

This project is licensed by University of West Attica as is a part of a university course. Do not redistribute.

## Acknowledgments

Thank you to **University of West Attica** and my professors for providing the resources and knowledge to complete this project.
