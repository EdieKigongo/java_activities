package com.kqu.models;

import java.io.Serializable;

/**
 * Course.java
 * Represents an academic degree or diploma program (e.g. MBChB, BSc Computer Science).
 */
public class Course implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String facultyId;
    private String departmentId;
    private String name;
    private String code;
    private String awardType; // "Bachelor", "Diploma"
    private int durationYears;
    private int totalCredits;

    public Course() {}

    public Course(String id, String facultyId, String name, String code, String awardType, int durationYears, int totalCredits) {
        this.id = id;
        this.facultyId = facultyId;
        this.name = name;
        this.code = code;
        this.awardType = awardType;
        this.durationYears = durationYears;
        this.totalCredits = totalCredits;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getFacultyId() { return facultyId; }
    public void setFacultyId(String facultyId) { this.facultyId = facultyId; }

    public String getDepartmentId() { return departmentId; }
    public void setDepartmentId(String departmentId) { this.departmentId = departmentId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getAwardType() { return awardType; }
    public void setAwardType(String awardType) { this.awardType = awardType; }

    public int getDurationYears() { return durationYears; }
    public void setDurationYears(int durationYears) { this.durationYears = durationYears; }

    public int getTotalCredits() { return totalCredits; }
    public void setTotalCredits(int totalCredits) { this.totalCredits = totalCredits; }
}
