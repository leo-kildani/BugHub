package com.teamtwo.access;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
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

    private final String DATA_FILE_PATH;

    private final ObjectMapper mapper;

    public BugHubDataAccessFileImpl() {
        this.DATA_FILE_PATH = "src/main/resources/data/data.json";
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
        this.projectMap = load();
    }

    public BugHubDataAccessFileImpl(String fileName) {
        this.DATA_FILE_PATH = "src/main/resources/data/" + fileName;
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
        this.projectMap = load();
    }

    @Override
    public List<Project> getProjects() {
        return new ArrayList<>(projectMap.values());
    }

    @Override
    public void addProject(Project project) {
        projectMap.put(project.getId(), project);
    }

    @Override
    public boolean deleteProject(int projectId) {
        return Objects.nonNull(projectMap.get(projectId));
    }

    @Override
    public Project getProject(int projectId) {
        return projectMap.get(projectId);
    }

    @Override
    public void addTicket(Project project, Ticket ticket) {
        project.getTickets().put(ticket.getId(), ticket);
    }

    @Override
    public boolean removeTicket(Project project, int ticketId) {
        return Objects.nonNull(project.getTickets().remove(ticketId));
    }

    @Override
    public Ticket getTicket(Project project, int ticketId) {
        return project.getTickets().get(ticketId);
    }

    @Override
    public void addComment(Ticket ticket, Comment comment) {
        ticket.getComments().put(comment.getId(), comment);
    }

    @Override
    public boolean removeComment(Ticket ticket, int commentId) {
        return Objects.nonNull(ticket.getComments().remove(commentId));
    }

    @Override
    public Comment getComment(Ticket ticket, int commentId) {
        return ticket.getComments().get(commentId);
    }

    @Override
    public void close() {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(DATA_FILE_PATH), projectMap);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<Integer, Project> load() {
        Map <Integer, Project> res = null;
        try {
            TypeFactory factory = TypeFactory.defaultInstance();
            MapType mapType = factory.constructMapType(HashMap.class, Integer.class, Project.class);
            res = mapper.readValue(new File(DATA_FILE_PATH), mapType);
        } catch (IOException e) {
            res = new HashMap<>();
        }
        return res;
    }
}
