package com.example.myworkshops.model;

public class Workshop {
    private int id;
    private String courseName;
    private String instructorName;
    private String description;

    public Workshop() {
    }

    public Workshop(String courseName, String instructorName, String description) {
        this.courseName = courseName;
        this.instructorName = instructorName;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }
}
