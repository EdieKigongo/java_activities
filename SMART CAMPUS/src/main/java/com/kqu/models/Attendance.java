package com.kqu.models;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Attendance.java
 * Represents a student attendance entry for a specific course unit lecture session.
 */
public class Attendance implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String courseUnitId;
    private String studentId;
    private LocalDate date;
    private String sessionTopic;
    private String status; // "PRESENT", "ABSENT", "LATE", "EXCUSED"
    private String lecturerId;

    public Attendance() {
        this.date = LocalDate.now();
        this.status = "PRESENT";
    }

    public Attendance(String id, String courseUnitId, String studentId, LocalDate date, String sessionTopic, String status, String lecturerId) {
        this.id = id;
        this.courseUnitId = courseUnitId;
        this.studentId = studentId;
        this.date = date;
        this.sessionTopic = sessionTopic;
        this.status = status;
        this.lecturerId = lecturerId;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCourseUnitId() { return courseUnitId; }
    public void setCourseUnitId(String courseUnitId) { this.courseUnitId = courseUnitId; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getSessionTopic() { return sessionTopic; }
    public void setSessionTopic(String sessionTopic) { this.sessionTopic = sessionTopic; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getLecturerId() { return lecturerId; }
    public void setLecturerId(String lecturerId) { this.lecturerId = lecturerId; }
}
