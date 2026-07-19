// Create TaskTicket inherited class
package com.aticket.app;

public class TaskTicket extends Ticket {

    private String task;

    // Constructor for TaskTicket that sets the type value to task
    public TaskTicket() {
        setType("task");
    }

    // Getter and Setter for task
    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
    
    // ToString method to convert the class into a string representation
    @Override
    public String toString() {
        return super.toString() + ", task='" + task + "'";
    }
}