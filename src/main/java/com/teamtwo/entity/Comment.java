package com.teamtwo.entity;

import lombok.Getter;

@Getter
public class Comment extends AbstractEntry {

    /**
     * The ticket that this comment is associated with.
     */
    private final Ticket ticket;

    /**
     * Construct a Comment object with a provided id, title, and description. A super call to AbstractEntry is made using the provided arguments.
     *
     * @param id id to be associated with this comment
     * @param title title to be associated with this comment
     * @param descr description to be associated with this comment
     */
    public Comment(int id, String title, String descr, Ticket ticket) {
        super(id, title, descr);
        this.ticket = ticket;
    }
}
