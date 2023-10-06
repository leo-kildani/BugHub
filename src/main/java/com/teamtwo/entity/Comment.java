package com.teamtwo.entity;

public class Comment extends AbstractEntry {

    /**
     * The id of the ticket that this comment is associated with.
     */
    private int ticketId;

    /**
     * Default Constructor
     */
    public Comment() {
        super();
    }

    /**
     * Construct a Comment object with a provided id, title, and description. A super call to AbstractEntry is made using the provided arguments.
     *
     * @param id id to be associated with this comment
     * @param title title to be associated with this comment
     * @param descr description to be associated with this comment
     * @param ticketId id of ticket this comment is associated with
     */
    public Comment(int id, String title, String descr, int ticketId) {
        super(id, title, descr);
        this.ticketId = ticketId;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }
}
