package models;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.*;

import java.util.UUID;

public class Event extends RecursiveTreeObject<Event> {

    private StringProperty name;
    private StringProperty venue;
    private StringProperty date;
    private IntegerProperty ticketsSold;
    private DoubleProperty ticketPrice;
    private DoubleProperty overheadCosts;

    private StringProperty eventID;

    public Event () {
        this.eventID = new SimpleStringProperty(UUID.randomUUID().toString());

        this.name = new SimpleStringProperty();
        this.venue = new SimpleStringProperty();
        this.date = new SimpleStringProperty();
        this.ticketPrice = new SimpleDoubleProperty();
        this.overheadCosts = new SimpleDoubleProperty();
        this.ticketsSold = new SimpleIntegerProperty();
    }

    public Event(String name, String venue, String date, double ticketPrice, double overheadCosts) {
        this.eventID = new SimpleStringProperty(UUID.randomUUID().toString());

        this.name = new SimpleStringProperty(name);
        this.venue = new SimpleStringProperty(venue);
        this.date = new SimpleStringProperty(date);
        this.ticketPrice = new SimpleDoubleProperty(ticketPrice);
        this.overheadCosts = new SimpleDoubleProperty(overheadCosts);
        this.ticketsSold = new SimpleIntegerProperty();
    }

    public StringProperty getEventID() {
        return this.eventID;
    }

    public StringProperty getName() {
        return name;
    }

    public StringProperty getVenue() {
        return venue;
    }

    public StringProperty getDate() {
        return date;
    }

    public DoubleProperty getTicketPrice() {
        return ticketPrice;
    }

    public DoubleProperty getOverheadCosts() {
        return overheadCosts;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setVenue(String venue) {
        this.venue.set(venue);
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice.set(ticketPrice);
    }

    public void setOverheadCosts(double overheadCosts) {
        this.overheadCosts.set(overheadCosts);
    }

    public void sellTicket() {
        this.ticketsSold.set(this.ticketsSold.getValue() + 1);
    }

    public IntegerProperty getSoldTickets () {
        return this.ticketsSold;
    }
}
