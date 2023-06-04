package gr.uniwa;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class THImpl extends UnicastRemoteObject implements THInterface {
    private Map<String, Area> areaMap;
    private Map<String, List<Reservation>> guestMap;
    private Map<String, List<ClientCallback>> callbackLists = new HashMap<>();

    public THImpl() throws RemoteException {
        super();
        areaMap = new HashMap<>();
        guestMap = new HashMap<>();
        // Initialize areas with their codes, capacities, and seat costs
        areaMap.put("PA", new Area("PA", 100, 45.0));
        areaMap.put("PB", new Area("PB", 200, 35.0));
        areaMap.put("PC", new Area("PC", 400, 25.0));
        areaMap.put("CE", new Area("CE", 225, 30.0));
        areaMap.put("PS", new Area("PS", 75, 20.0));
    }

    // Method to provide a list of all theater areas
    @Override
    public String thClientList() throws RemoteException {
        StringBuilder theaterListBuilder = new StringBuilder();

        for (Area area : areaMap.values()) {
            theaterListBuilder.append(area.getAvailableSeats()).append(" available seats in area ")
                    .append(area.getAreaName()).append(" ( code: ").append(area.getAreaCode())
                    .append(" ) - cost: ").append(area.getSeatCost()).append(" euro\n");
        }
        return theaterListBuilder.toString();
    }

    // Method to book seats for a guest
    @Override
    public String thClientBook(String areaCode, int seats, String name) throws RemoteException {
        Area area = areaMap.get(areaCode);
        if (area == null) {
            return "Error: Invalid area code!";
        }
        if (seats > area.getAvailableSeats()) {
            return "Error: Not enough available seats in the area " + area.getAreaName()
                    + ". Available seats that you can book: " + area.getAvailableSeats();
        }
        area.decreaseAvailableSeats(seats);
        Reservation newReservation = new Reservation(area, seats);
        guestMap.computeIfAbsent(name, k -> new ArrayList<>()).add(newReservation);
        return "Reservation successful! " + name + " has reserved " + seats + " seats in the area " +
               area.getAreaName() + ". Total cost: " + newReservation.getTotalCost() + " euro.";
    }

    // Method to provide a list of all guests and their reservations
    @Override
    public String thClientGuests() throws RemoteException {
        StringBuilder theaterListBuilder = new StringBuilder();

        for (Map.Entry<String, List<Reservation>> entry : guestMap.entrySet()) {
            theaterListBuilder.append(entry.getKey()).append(" has reservations:\n");
            for (Reservation reservation : entry.getValue()) {
                theaterListBuilder.append("Area Code: ").append(reservation.getArea().getAreaCode())
                    .append(" - Seats: ").append(reservation.getSeats())
                    .append(" - Total Cost: ").append(reservation.getTotalCost()).append(" euro\n");
            }
        }
        return theaterListBuilder.toString();
    }
    
    // Method to cancel reservations for a guest
    @Override
    public String thClientCancel(String areaCode, int seats, String name) throws RemoteException {
        List<Reservation> reservations = guestMap.get(name);
        if (reservations == null) {
            return "Error: No reservation found for " + name;
        }
        for (Iterator<Reservation> iterator = reservations.iterator(); iterator.hasNext();) {
            Reservation reservation = iterator.next();
            if (reservation.getArea().getAreaCode().equals(areaCode)) {
                if (reservation.getSeats() < seats) {
                    return "Error: The number of seats to cancel is greater than the number of reserved seats.";
                }
                reservation.getArea().increaseAvailableSeats(seats);
                if (reservation.getSeats() == seats) {
                    iterator.remove();
                } else {
                    reservation.decreaseSeats(seats);
                }
                List<ClientCallback> callbacks = callbackLists.get(areaCode);
                if (callbacks != null) {
                    for (ClientCallback callback : callbacks) {
                        callback.notify("Seats are now available in area " + areaCode);
                    }
                }
                return "Cancellation successful! " + name + " has cancelled " + seats + " seats in the area " +
                       reservation.getArea().getAreaName() + ". Here are the remaining reservations:\n" +
                       thClientGuests();
            }
        }
        return "Error: No reservation found for area " + areaCode + " for " + name;
    }

    // Method to subscribe for callbacks
    @Override
    public void subscribe(String areaCode, ClientCallback client) throws RemoteException {
        callbackLists.computeIfAbsent(areaCode, k -> new ArrayList<>()).add(client);
    }


}

class Area {
    // Class Area represents a theater area with its code, available seats, and cost per seat
    private String areaCode;
    private int availableSeats;
    private double seatCost;

    public Area(String areaCode, int availableSeats, double seatCost) {
        this.areaCode = areaCode;
        this.availableSeats = availableSeats;
        this.seatCost = seatCost;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public String getAreaName() {
        switch (this.areaCode) {
            case "PA":
                return "A";
            case "PB":
                return "B";
            case "PC":
                return "C";
            case "CE":
                return "Center";
            case "PS":
                return "Side";
            default:
                return "";
        }
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public synchronized void decreaseAvailableSeats(int seats) {
        if (seats <= this.availableSeats) {
            this.availableSeats -= seats;
        }
    }

    public synchronized void increaseAvailableSeats(int seats) {
        this.availableSeats += seats;
    }

    public double getSeatCost() {
        return seatCost;
    }
}

class Reservation {
    // Class Reservation represents a reservation with its associated area, number of seats, and total cost
    private Area area;
    private int seats;
    private double totalCost;

    public Reservation(Area area, int seats) {
        this.area = area;
        this.seats = seats;
        area.decreaseAvailableSeats(seats);
        this.totalCost = area.getSeatCost() * seats;
    }

    public Area getArea() {
        return area;
    }

    public int getSeats() {
        return seats;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void decreaseSeats(int seats) {
        this.seats -= seats;
        this.totalCost = this.area.getSeatCost() * this.seats;
    }
}



