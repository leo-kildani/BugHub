package com.teamtwo.service;

import java.util.List;
import java.util.stream.Collectors;

import com.teamtwo.access.BugHubDataAccess;
import com.teamtwo.entity.Comment;
import com.teamtwo.entity.Project;
import com.teamtwo.entity.Ticket;

public class BugHubServiceImpl implements BugHubService {
	
	private final BugHubDataAccess dao;
	
	public BugHubServiceImpl(BugHubDataAccess dao) {
		this.dao = dao;
	}

	@Override
	public List<Project> getProjects() {
		return dao.getProjects();
	}

	@Override
	public List<Project> getProjectsByTitle(String title) {
		return dao.getProjects().stream()
				.filter(p -> p.getTitle().toLowerCase().contains(title.toLowerCase()))
				.collect(Collectors.toList());
	}

	@Override
	public List<Ticket> getTickets() {
		return dao.getTickets();
	}

	@Override
	public List<Ticket> getTicketsByTitle(String title) {
		return dao.getTickets().stream()
				.filter(t -> t.getTitle().toLowerCase().contains(title.toLowerCase()))
				.collect(Collectors.toList());
	}

	@Override
	public List<Ticket> getTicketsByProjectId(int projectId) {
		return dao.getTickets().stream()
				.filter(t -> t.getProjectId() == projectId)
				.collect(Collectors.toList());
	}

	@Override
	public List<Comment> getCommentsByTicketId(int ticketId) {
		return dao.getComments().stream()
				.filter(c -> c.getTicketId() == ticketId)
				.collect(Collectors.toList());
	}

	@Override
	public void addProject(Project project) {
		dao.addProject(project);
	}

	@Override
	public void addTicket(Ticket Ticket) {
		dao.addTicket(Ticket);
	}

	@Override
	public void addComment(Comment Comment) {
		dao.addComment(Comment);
	}

	@Override
	public void deleteProject(int projectId) {
		dao.deleteProject(projectId);
		dao.getTickets().stream()
				.filter(t -> t.getProjectId() == projectId)
				.forEach(t -> {
					dao.getComments().stream()
							.filter(c -> c.getTicketId() == t.getId())
							.forEach(c -> dao.deleteComment(c.getId()));
					dao.deleteTicket(t.getId());
				});
	}

	@Override
	public void deleteTicket(int ticketId) {
		dao.deleteTicket(ticketId);
		dao.getComments().stream()
				.filter(c -> c.getTicketId() == ticketId)
				.forEach(c -> dao.deleteComment(c.getId()));
	}

	@Override
	public void deleteComment(int commentId) {
		dao.deleteComment(commentId);
	}

	@Override
	public Project getProject(int projectId) {
		return dao.getProject(projectId);
	}

	@Override
	public Ticket getTicket(int ticketId) {
		return dao.getTicket(ticketId);
	}

	@Override
	public Comment getComment(int commentId) {
		return dao.getComment(commentId);
	}

}
