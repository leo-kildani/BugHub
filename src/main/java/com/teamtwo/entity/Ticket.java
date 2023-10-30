package com.teamtwo.entity;

import java.util.HashMap;
import java.util.Map;

public class Ticket extends AbstractEntry{

    /**
     * The id of the project that this ticket is associated with.
     */
    private int projectId;

    public Ticket() {
        super();
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
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }
}
