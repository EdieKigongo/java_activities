package com.kqu.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Lecturer.java
 * Subclass inheriting from User.
 * Represents an academic lecturer assigned to course units under a faculty.
 */
public class Lecturer extends User {
    private String staffId;         // e.g. "KQU-LEC-001"
    private String facultyId;
    private String facultyName;
    private String department;
    private String title;          // e.g. "Dr.", "Prof.", "Eng."
    private String specialization; // e.g. "Distributed Systems & Java Architectures"
    private String qualification;  // e.g. "PhD in Computer Science"
    private List<String> assignedCourseUnitIds;

    public Lecturer() {
        super();
        this.role = "LECTURER";
        this.assignedCourseUnitIds = new ArrayList<>();
    }

    public Lecturer(String id, String username, String password, String email, String fullName,
                    String staffId, String facultyId, String department, String title, String specialization) {
        super(id, username, password, email, "LECTURER", fullName, "");
        this.staffId = staffId;
        this.facultyId = facultyId;
        this.department = department;
        this.title = title;
        this.specialization = specialization;
        this.assignedCourseUnitIds = new ArrayList<>();
    }

    public String getStaffId() { return staffId; }
    public void setStaffId(String staffId) { this.staffId = staffId; }

    public String getFacultyId() { return facultyId; }
    public void setFacultyId(String facultyId) { this.facultyId = facultyId; }

    public String getFacultyName() { return facultyName; }
    public void setFacultyName(String facultyName) { this.facultyName = facultyName; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public String getQualification() { return qualification; }
    public void setQualification(String qualification) { this.qualification = qualification; }

    public List<String> getAssignedCourseUnitIds() { return assignedCourseUnitIds; }
    public void setAssignedCourseUnitIds(List<String> assignedCourseUnitIds) { this.assignedCourseUnitIds = assignedCourseUnitIds; }
}
