package com.kqu.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Admin.java
 * Subclass inheriting from User.
 * Holds system-wide administrative control over King's and Queen's University.
 */
public class Admin extends User {
    private String employeeId;
    private String office;
    private List<String> permissions;

    public Admin() {
        super();
        this.role = "ADMIN";
        this.permissions = new ArrayList<>();
    }

    public Admin(String id, String username, String password, String email, String fullName,
                 String employeeId, String office) {
        super(id, username, password, email, "ADMIN", fullName, "");
        this.employeeId = employeeId;
        this.office = office;
        this.permissions = new ArrayList<>();
        this.permissions.add("ALL_POWERS");
    }

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public String getOffice() { return office; }
    public void setOffice(String office) { this.office = office; }

    public List<String> getPermissions() { return permissions; }
    public void setPermissions(List<String> permissions) { this.permissions = permissions; }
}
