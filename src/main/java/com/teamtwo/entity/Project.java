package com.teamtwo.entity;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Project extends AbstractEntry {

    /**
     * A map of tickets associated with this project, stored using their ids. Initialized as a new HashMap.
     */
    private final Map<Integer, Ticket> tickets;

    /**
     * Default Constructor
     */
    public Project() {
        super();
        this.tickets = new HashMap<>();
    }

    /**
     * Construct a Project object with the provided id, title, and descr, and date. A super call to AbstractEntry is made to initialize listed fields.
     *
     * @param id id associated with this project
     * @param title title associated with this project
     * @param descr description associated with this project
     * @param date date associated with this project
     */
    public Project(int id, String title, String descr, LocalDate date) {
        super(id, title, descr, date);
        tickets = new HashMap<>();
    }

    public Map<Integer, Ticket> getTickets() {
        return tickets;
    }
}
