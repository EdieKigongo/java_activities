package com.kqu.models;

import java.io.Serializable;

/**
 * Department.java
 * Represents an academic department within a faculty at King's and Queen's University.
 */
public class Department implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String facultyId;
    private String name;
    private String code;
    private String headOfDepartment;

    public Department() {}

    public Department(String id, String facultyId, String name, String code, String headOfDepartment) {
        this.id = id;
        this.facultyId = facultyId;
        this.name = name;
        this.code = code;
        this.headOfDepartment = headOfDepartment;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getFacultyId() { return facultyId; }
    public void setFacultyId(String facultyId) { this.facultyId = facultyId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getHeadOfDepartment() { return headOfDepartment; }
    public void setHeadOfDepartment(String headOfDepartment) { this.headOfDepartment = headOfDepartment; }
}
