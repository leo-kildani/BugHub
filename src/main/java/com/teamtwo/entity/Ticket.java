package com.teamtwo.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Ticket extends AbstractEntry{

    /**
     * The id of the project that this ticket is associated with.
     */
    private int projectId;

    /**
     * A map of comments associated with this ticket, stored using their ids. Initialized as an empty HashMap.
     */
    private final Map<Integer, Comment> comments;

    public Ticket() {
        super();
        comments = new HashMap<>();
    }

    /**
     * Construct a Ticket object with the provided id, title, descr, and project.
     *
     * @param id id associated with this ticket
     * @param title title associated with this ticket
     * @param descr description associated with this ticket
     * @param projectId id of project this ticket is associated with
     */
    public Ticket(int id, String title, String descr, int projectId) {
        super(id, title, descr);
        this.projectId = projectId;
        comments = new HashMap<>();
    }
}
