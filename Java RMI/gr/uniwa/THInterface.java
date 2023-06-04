package gr.uniwa;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface THInterface extends Remote {
    // Fetches a list of clients
    String thClientList() throws RemoteException; 
    // Books a client with a specific type, number and name
    String thClientBook(String type, int number, String name) throws RemoteException;
    // Fetches a list of guests
    String thClientGuests() throws RemoteException;
    // Cancels a booking for a client with a specific type, number and name
    String thClientCancel(String type, int number, String name) throws RemoteException;
    // Subscribes users to notification list
    void subscribe(String areaCode, ClientCallback client) throws RemoteException;
}
