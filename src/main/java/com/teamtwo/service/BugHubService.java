package com.teamtwo.service;

import java.util.List;

import com.teamtwo.entity.*;

public interface BugHubService {
	
	public List<Project> getProjects();
	
	public List<Project> getProjectsByTitle(String title);
	
	public List<Ticket> getTickets();
	
	public List<Ticket> getTicketsByTitle(String title);
	
	public List<Ticket> getTicketsByProjectId(int projectId);
	
	public List<Comment> getCommentsByTicketId(int ticketId);
	
	public void addProject(Project project);
	
	public void addTicket(Ticket Ticket);
	
	public void addComment(Comment Comment);
	
	public void deleteProject(int projectId);
	
	public void deleteTicket(int ticketId);
	
	public void deleteComment(int commentId);
	
	public Project getProject(int projectId);
	
	public Ticket getTicket(int ticketId);
	
	public Comment getComment(int commentId);

	public void closeProject() throws Exception;
}
