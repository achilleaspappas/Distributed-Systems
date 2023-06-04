package gr.uniwa;

import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class THServer {

    public THServer() {
        try {
            // Notify that the server has started
            System.out.println("Server Started.");
            // Create a new instance of the remote object implementation
            THImpl server = new THImpl();
            // Create a new registry on port 65000
            Registry reg = LocateRegistry.createRegistry(65000);
            // Construct the URL for the remote object
            String url = "rmi://" + InetAddress.getLocalHost().getHostAddress() + ":65000/kekw";
            // Rebind the URL to the remote object
            Naming.rebind(url, server);
            }catch(Exception e) {
                // Print any exceptions that occur
                e.printStackTrace();
                System.out.println("Exception happend.\n");
            }
    }

    public static void main(String[] args) {
        // Instantiate a new server
        new THServer();
    }
}
