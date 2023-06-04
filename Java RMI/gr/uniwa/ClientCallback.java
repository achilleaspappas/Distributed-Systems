package gr.uniwa;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientCallback extends Remote {
    // Notifies client with a given message
    void notify(String message) throws RemoteException;
}
