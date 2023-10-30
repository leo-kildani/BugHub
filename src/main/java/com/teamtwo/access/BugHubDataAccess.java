package com.teamtwo.access;

import com.teamtwo.entity.Comment;
import com.teamtwo.entity.Project;
import com.teamtwo.entity.Ticket;

import java.util.List;

public interface BugHubDataAccess {

    /**
     * Returns a list of all the saved projects in a database.
     *
     * @return a list of all saved projects
     */
    public List<Project> getProjects();
    
    /**
     * Returns a list of all the saved tickets in a database.
     *
     * @return a list of all saved tickets
     */
    public List<Ticket> getTickets();
    
    /**
     * Returns a list of all the saved comments in a database.
     *
     * @return a list of all saved comments
     */
    public List<Comment> getComments();

    /**
     * Add a project to database.
     * @param project project to be added
     */
    public void addProject(Project project);

    /**
     * Delete a project from database given a project id.
     *
     * @param projectId id of project to be deleted
     */
    public void deleteProject(int projectId);

    /**
     * Retrieve a project given a project id.
     *
     * @param projectId id of project to be retrieved
     * @return project if project was found and retrieved with given id; null otherwise
     */
    public Project getProject(int projectId);

    /**
     * Add a ticket to database.
     *
     * @param ticket ticket to be added
     */
    public void addTicket(Ticket ticket);

    /**
     * Remove a ticket from databse given the ticket id.
     *
     * @param ticketId id of ticket to be removed
     */
    public void deleteTicket(int ticketId);

    /**
     * Retrieve a ticket from database given the ticket id.
     *
     * @param ticketId id of ticket to be retrieved
     * @return ticket if ticket was found with given id; null otherwise
     */
    public Ticket getTicket(int ticketId);

    /**
     * Add a comment to database.
     *
     * @param comment comment to be added
     */
    public void addComment(Comment comment);

    /**
     * Remove a comment from database.
     *
     * @param commentId id of comment to be removed
     */
    public void deleteComment(int commentId);

    /**
     * Retrieve a comment from database
     *
     * @param commentId id of comment to retrieve
     * @return comment if comment was found with given id; null otherwise
     */
    public Comment getComment(int commentId);

    /**
     * Used to close any access to stored data. Either closing a file source or closing a database connection.
     */
    public void close() throws Exception;
}
