package com.teamtwo.access;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.teamtwo.entity.Comment;
import com.teamtwo.entity.Project;
import com.teamtwo.entity.Ticket;

import javax.annotation.PreDestroy;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BugHubDataAccessFileImpl implements BugHubDataAccess{

    private Map<Integer, Project> projectMap;

    private final String DATA_FILE;

    private final ObjectMapper mapper;

    public BugHubDataAccessFileImpl() {
        this.DATA_FILE = Objects.requireNonNull(getClass()
                .getClassLoader()
                .getResource("data/"))
                .getPath() + "data.json";
        this.mapper = new ObjectMapper();
        load();
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

    @PreDestroy
    private void close() {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(DATA_FILE), projectMap);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void load() {
        try {
            TypeFactory factory = TypeFactory.defaultInstance();
            MapType mapType = factory.constructMapType(HashMap.class, Integer.class, Project.class);
            projectMap = mapper.readValue(new File(DATA_FILE), mapType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
