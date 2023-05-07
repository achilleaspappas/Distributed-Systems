#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <unistd.h>
#include <string.h>

int main(int argc, char *argv[])
{

    /* Initialaze socket setup */
    int sockfd, portno;
    struct sockaddr_in serv_addr;
    struct hostent *server;

    if (argc < 3)
    {
        fprintf(stderr, "Usage %s hostname port\n", argv[0]);
        exit(1);
    }

    // Get the server hostname and port from command line arguments
    server = gethostbyname(argv[1]);
    portno = atoi(argv[2]);

    // Create a socket
    sockfd = socket(AF_INET, SOCK_STREAM, 0);

    if (sockfd < 0)
    {
        perror("Error opening socket");
        exit(1);
    }

    if (server == NULL)
    {
        fprintf(stderr, "Error: No such host exists.\n");
        exit(1);
    }

    // Set up the server address structure
    bzero((char *)&serv_addr, sizeof(serv_addr));
    serv_addr.sin_family = AF_INET;
    bcopy((char *)server->h_addr_list[0],
          (char *)&serv_addr.sin_addr.s_addr,
          server->h_length);
    serv_addr.sin_port = htons(portno);

    printf("Establishing connection...\n");

    // Connect to the server
    if (connect(sockfd, (struct sockaddr *)&serv_addr, sizeof(serv_addr)) < 0)
    {
        perror("Error connecting to the server");
        exit(1);
    }

    printf("Connection established.\n\n");

    /* End of socket setup */

    /* Get inputs from user */

    int *x, *y, n;
    double r;

    // Get the value of n from the user
    printf("Enter the value of n: ");
    scanf("%d", &n);

    // Allocate memory for arrays x and y
    x = (int *)malloc(sizeof(int) * n);
    y = (int *)malloc(sizeof(int) * n);

    // Get elements for array x from the user
    printf("Enter elements for x: ");
    for (int i = 0; i < n; i++)
    {
        scanf("%d", &x[i]);
    }

    // Get elements for array y from the user
    printf("Enter elements for y: ");
    for (int i = 0; i < n; i++)
    {
        scanf("%d", &y[i]);
    }

    // Get the value of r from the user
    printf("Enter the value of r: ");
    scanf("%lf", &r);

    // Send the values of n, r, x, and y to the server
    send(sockfd, &n, sizeof(int), 0);
    send(sockfd, &r, sizeof(double), 0);
    send(sockfd, x, sizeof(int) * n, 0);
    send(sockfd, y, sizeof(int) * n, 0);

    /* End of user input */

    /* Initialize menu setup */

    int option;

    while (1)
    {
        printf("\n");
        printf("--------------- Menu ---------------\n");
        printf("1. Calculate the inner product of the two vectors X * Y.\n");
        printf("2. Calculate the average value of each vector Εx, Εy.\n");
        printf("3. Calculate the product of r*(X+Y).\n");
        printf("4. Exit\n");
        printf("------------------------------------\n");
        printf("Select an option: ");
        scanf("%d", &option);
        printf("\n");

        if (option != 1 && option != 2 && option != 3 && option != 4)
        {
            printf("Invalid option selected.\n");
            continue;
        }

        // Send the selected option to the server
        send(sockfd, &option, sizeof(int), 0);

        if (option == 4)
        {
            printf("Client terminated by user.\n");
            break;
        }

        if (option == 1)
        {
            int result1;
            // Receive the result of the inner product calculation from the server
            recv(sockfd, &result1, sizeof(int), 0);
            printf("The inner product of the two vectors X * Y is: %d\n", result1);
        }
        else if (option == 2)
        {
            double *result2 = (double *)malloc(sizeof(double) * 2);
            if (result2 == NULL)
            {
                printf("Error, malloc for result2 failed.\n");
                break;
            }
            // Receive the results of the average calculation from the server
            recv(sockfd, result2, sizeof(double) * n, 0);
            printf("The average value of each vector Εx, Εy is: %lf, %lf\n", result2[0], result2[1]);
            free(result2);
        }
        else if (option == 3)
        {
            double *result3 = (double *)malloc(sizeof(double) * 2);
            if (result3 == NULL)
            {
                printf("Error, malloc for result3 failed.\n");
                break;
            }
            // Receive the results of the product calculation from the server
            recv(sockfd, result3, sizeof(double) * n, 0);
            printf("The product of r * (X + Y) is:\n");
            for (int i = 0; i < n; i++)
            {
                printf("%lf \n", result3[i]);
            }
            free(result3);
        }
    }

    /* End of menu setup */

    /* Free memory and close socket */
    close(sockfd);
    free(x);
    free(y);

    return 0;
}