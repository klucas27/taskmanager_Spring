package com.lucas.taskmanager.model;

public class Task {
    private Long id;
    private String title;
    private String description;
    private String status;


    public Task(Long id, String title, String description, String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
    }

    //getters
    public Long getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public String getStatus() {
        return this.status;
    }

    // setters
    public void setStatus(String status) {
        this.status = status;
    }

}
