package com.teamtwo.controllers;

import com.teamtwo.access.BugHubDataAccess;
import com.teamtwo.access.BugHubDataAccessFileImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ProjectsPageController
{
    @FXML
    private TextField projectNameField;
    @FXML
    private DatePicker startingDate;
    @FXML
    private TextArea descriptionArea;

    private BugHubDataAccess dao = new BugHubDataAccessFileImpl();

    public void clear(ActionEvent e)
    {
        projectNameField.clear();
        startingDate.setValue(null);
        descriptionArea.clear();
    }
}