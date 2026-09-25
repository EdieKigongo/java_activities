package com.kqu.models;

import java.io.Serializable;

/**
 * CourseUnit.java
 * Represents an individual study module (e.g. CSC1101 Structured Programming in Java).
 */
public class CourseUnit implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String courseId;
    private String facultyId;
    private String code;           // e.g. "CSC1101"
    private String title;          // e.g. "Structured Programming in Java"
    private int creditUnits;       // e.g. 4
    private int semester;          // 1 or 2
    private int yearOfStudy;       // 1, 2, 3...
    private String lecturerId;
    private String lecturerName;
    private String description;

    public CourseUnit() {}

    public CourseUnit(String id, String courseId, String facultyId, String code, String title, int creditUnits, int semester, int yearOfStudy) {
        this.id = id;
        this.courseId = courseId;
        this.facultyId = facultyId;
        this.code = code;
        this.title = title;
        this.creditUnits = creditUnits;
        this.semester = semester;
        this.yearOfStudy = yearOfStudy;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCourseId() { return courseId; }
    public void setCourseId(String courseId) { this.courseId = courseId; }

    public String getFacultyId() { return facultyId; }
    public void setFacultyId(String facultyId) { this.facultyId = facultyId; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getCreditUnits() { return creditUnits; }
    public void setCreditUnits(int creditUnits) { this.creditUnits = creditUnits; }

    public int getSemester() { return semester; }
    public void setSemester(int semester) { this.semester = semester; }

    public int getYearOfStudy() { return yearOfStudy; }
    public void setYearOfStudy(int yearOfStudy) { this.yearOfStudy = yearOfStudy; }

    public String getLecturerId() { return lecturerId; }
    public void setLecturerId(String lecturerId) { this.lecturerId = lecturerId; }

    public String getLecturerName() { return lecturerName; }
    public void setLecturerName(String lecturerName) { this.lecturerName = lecturerName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
