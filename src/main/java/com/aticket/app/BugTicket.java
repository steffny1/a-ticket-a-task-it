// BugTicket inherited class

package com.aticket.app;

public class BugTicket extends Ticket {

    private String description;
    private int error;

    // Constructor for BugTicket that sets the type value to bug
    public BugTicket() {
        setType("bug");
    }

    // Getters and Setters for BugTicket attributes
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    // ToString method to convert the class into a string representation
    @Override
    public String toString() {
        return super.toString() +
                ", description='" + description + '\'' +
                ", error=" + error;
    }
}