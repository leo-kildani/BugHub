package com.teamtwo.access;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.teamtwo.entity.Comment;
import com.teamtwo.entity.Project;
import com.teamtwo.entity.Ticket;
import java.io.*;
import java.util.*;

public class BugHubDataAccessFileImpl implements BugHubDataAccess {

    private final Map<Integer, Project> projectMap;
    private final Map<Integer, Ticket> ticketMap;
    private final Map<Integer, Comment> commentMap;

    private final String[] DATA_FILE_PATHS;  // let idx... 0 = projects, 1 = tickets, 2 = comments

    private final ObjectMapper mapper;

    public BugHubDataAccessFileImpl() {
    	String dataDir = "src/main/resources/data/";
        this.DATA_FILE_PATHS = new String[]{dataDir + "projects.json", dataDir + "tickets.json", dataDir + "comments.json"};
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
        this.projectMap = loadProjects();
        this.ticketMap = loadTickets();
        this.commentMap = loadComments();
    }

    public BugHubDataAccessFileImpl(String[] fileNames) {
        this.DATA_FILE_PATHS = fileNames;
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
        this.projectMap = loadProjects();
        this.ticketMap = loadTickets();
        this.commentMap = loadComments();
    }
    
    @Override
    public List<Project> getProjects() {
    	return new ArrayList<>(projectMap.values());
    }
    
    @Override
    public List<Ticket> getTickets() {
    	return new ArrayList<>(ticketMap.values());
    }
    
    @Override
    public List<Comment> getComments() {
    	return new ArrayList<>(commentMap.values());
    }
    
    @Override
    public void addProject(Project project) {
    	projectMap.put(project.getId(), project);
    }
    
    @Override
    public void deleteProject(int projectId) {
    	projectMap.remove(projectId);
    }
    
    @Override
    public Project getProject(int projectId) {
    	return projectMap.get(projectId);
    }
    
    @Override
    public void addTicket(Ticket ticket) {
    	ticketMap.put(ticket.getId(), ticket);
    }
    
    @Override
    public void deleteTicket(int ticketId) {
    	ticketMap.remove(ticketId);
    }
    
    @Override
    public Ticket getTicket(int ticketId) {
    	return ticketMap.get(ticketId);
    }
    
    @Override
    public void addComment(Comment comment) {
    	commentMap.put(comment.getId(), comment);
    }
    
    @Override
    public void deleteComment(int commentId) {
    	commentMap.remove(commentId);
    }
    
    @Override
    public Comment getComment(int commentId) {
    	return commentMap.get(commentId);
    }
    



    @Override
    public void close() throws IOException {
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(DATA_FILE_PATHS[0]), projectMap);
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(DATA_FILE_PATHS[1]), ticketMap);
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(DATA_FILE_PATHS[2]), commentMap);                
    }

    private Map<Integer, Project> loadProjects() {
        Map <Integer, Project> res = null;
        try {
            TypeFactory factory = TypeFactory.defaultInstance();
            MapType mapType = factory.constructMapType(HashMap.class, Integer.class, Project.class);
            res = mapper.readValue(new File(DATA_FILE_PATHS[0]), mapType);
        } catch (IOException e) {
            res = new HashMap<>();
        }
        return res;
    }
    
    private Map<Integer, Ticket> loadTickets() {
        Map <Integer, Ticket> res = null;
        try {
            TypeFactory factory = TypeFactory.defaultInstance();
            MapType mapType = factory.constructMapType(HashMap.class, Integer.class, Ticket.class);
            res = mapper.readValue(new File(DATA_FILE_PATHS[1]), mapType);
        } catch (IOException e) {
            res = new HashMap<>();
        }
        return res;
    }
    
    private Map<Integer, Comment> loadComments() {
        Map <Integer, Comment> res = null;
        try {
            TypeFactory factory = TypeFactory.defaultInstance();
            MapType mapType = factory.constructMapType(HashMap.class, Integer.class, Comment.class);
            res = mapper.readValue(new File(DATA_FILE_PATHS[0]), mapType);
        } catch (IOException e) {
            res = new HashMap<>();
        }
        return res;
    }
}
