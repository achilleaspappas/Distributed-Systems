package gr.uniwa;

import java.io.Serializable;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class THClient implements ClientCallback, Serializable {

    @Override
    public void notify(String message) throws RemoteException {
        System.out.println(message);
    }
    
    public static void main(String[] args) {
        try {
            // Print to console that client has started
            System.out.println("Client Started.\n");

            // Check if any arguments are provided
            if (args.length == 0) {
                // Print usage instructions if no arguments are provided
                System.out.println("Usage:");
                System.out.println("java THClient");
                System.out.println("java THClient list <hostname>");
                System.out.println("java THClient book <hostname> <type> <number> <name>");
                System.out.println("java THClient guests <hostname>");
                System.out.println("java THClient cancel <hostname> <type> <number> <name>");
            } else {
                // Extract hostname from arguments
                String hostname = args[1];

                // Look up the remote object in the RMI registry
                THInterface client = (THInterface) Naming.lookup("rmi://" + hostname + ":65000/kekw");

                // Perform operations based on provided arguments
                if (args.length == 2 && args[0].equals("list")) {
                    String theaterList = client.thClientList();
                    System.out.println(theaterList);
                } else if (args.length == 5 && args[0].equals("book")) {
                    String type = args[2];
                    int number = Integer.parseInt(args[3]);
                    String name = args[4];
                    String result = client.thClientBook(type, number, name);
                    System.out.println(result);
                    if (result.startsWith("Error: Not enough available seats")) {
                        System.out.println("Would you like to be notified when seats become available in this area? (yes/no)");
                        Scanner scanner = new Scanner(System.in);
                        String response = scanner.nextLine();
                        if (response.equalsIgnoreCase("yes")) {
                            THClient inst = new THClient();
                            client.subscribe(type, inst);
                            System.out.println("You have been subscribed to notifications for area " + type);
                        }
                        scanner.close();
                    }
                } else if (args.length == 2 && args[0].equals("guests")) {
                    String guestList = client.thClientGuests();
                    System.out.println(guestList);
                } else if (args.length == 5 && args[0].equals("cancel")) {
                    String type = args[2];
                    int number = Integer.parseInt(args[3]);
                    String name = args[4];
                    String result = client.thClientCancel(type, number, name);
                    System.out.println(result);
                }

                
            }

            // Sleep
            TimeUnit.SECONDS.sleep(600);
            
        } catch (Exception e) {
            // Print any exceptions that occur
            e.printStackTrace();
            System.out.println("Exception happend.\n");
        }
    }
}
