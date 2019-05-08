package models;

public class Stats {
    private int numberOfEvents;
    private int numberOfSoldTickets;
    private double revenue;
    private double expenses;

    public Stats() { }
    public Stats(int ne, int nt) {
        this.numberOfEvents = ne;
        this.numberOfSoldTickets = nt;
    }

    public int getNumberOfEvents() {
        return numberOfEvents;
    }

    public int getNumberOfSoldTickets() {
        return numberOfSoldTickets;
    }

    public double getExpenses() {
        return expenses;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setExpenses(double expenses) {
        this.expenses = expenses;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public void setNumberOfEvents(int numberOfEvents) {
        this.numberOfEvents = numberOfEvents;
    }

    public void setNumberOfSoldTickets(int numberOfSoldTickets) {
        this.numberOfSoldTickets = numberOfSoldTickets;
    }
}
