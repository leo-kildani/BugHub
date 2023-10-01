package com.teamtwo.access;

import com.teamtwo.entity.Comment;
import com.teamtwo.entity.Project;
import com.teamtwo.entity.Ticket;

public interface BugHubDataAccess {

    /**
     * Add a project to BugHub in-memory database.
     *
     * @param project project to be added
     */
    public void addProject(Project project);

    /**
     * Delete a project from database given a project id.
     *
     * @param projectId id of project to be deleted
     * @return true if project was found and deleted; false otherwise
     */
    public boolean deleteProject(int projectId);

    /**
     * Retrieve a project given a project id.
     *
     * @param projectId id of project to be retrieved
     * @return project if project was found and retrieved with given id; null otherwise
     */
    public Project getProject(int projectId);

    /**
     * Add a ticket to a project.
     *
     * @param project project to add ticket to
     * @param ticket ticket to be added
     * @return true if project was found and ticket was added; false otherwise
     */
    public boolean addTicket(Project project, Ticket ticket);

    /**
     * Remove a ticket from a specified project given the ticket id.
     *
     * @param project project to remove ticket from
     * @param ticketId id of ticket to be removed
     * @return true it project exists and ticket was found with given id; false otherwise
     */
    public boolean removeTicket(Project project, int ticketId);

    /**
     * Retrieve a ticket from a specified project given the ticket id.
     *
     * @param project project to retrieve ticket from
     * @param ticketId id of ticket to be retrieved
     * @return ticket id if project exists and ticket was found with given id; null otherwise
     */
    public Ticket getTicket(Project project, int ticketId);

    /**
     * Add a comment to the specified ticket.
     *
     * @param ticket ticket to add comment to
     * @param comment comment to be added
     * @return true if ticket exists and comment was added; false otherwise
     */
    public boolean addComment(Ticket ticket, Comment comment);

    /**
     * Remove a comment from a specified ticket.
     *
     * @param ticket ticket to remove comment from
     * @param commentId id of comment to be removed
     * @return true if ticket exists and comment was found with given id; false otherwise
     */
    public boolean removeComment(Ticket ticket, int commentId);

    /**
     * Retrieve a comment from a specified ticket
     *
     * @param ticket ticket to retrieve comment from
     * @param commentId id of comment to retrieve
     * @return comment if ticket exists and comment was found with given id; null otherwise
     */
    public Comment getComment(Ticket ticket, int commentId);
}
