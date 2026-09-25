package com.kqu.services;

import com.kqu.dao.UserDAO;
import com.kqu.models.User;

/**
 * AuthenticationService.java
 * Handles secure user authentication and role verification for KQU.
 */
public class AuthenticationService {

    private final UserDAO userDAO;
    private User loggedInUser;

    public AuthenticationService() {
        this.userDAO = new UserDAO();
    }

    /**
     * Attempts login for Admin, Lecturer, or Student.
     * @param identifier Username, email, or Student Reg Number
     * @param password Password
     * @return User if successful, null otherwise
     */
    public User login(String identifier, String password) {
        if (identifier == null || identifier.trim().isEmpty() || password == null) {
            return null;
        }

        User user = userDAO.authenticate(identifier.trim(), password.trim());
        if (user != null) {
            this.loggedInUser = user;
            System.out.println("[KQU Auth] Login successful for: " + user.getFullName() + " (" + user.getRole() + ")");
        } else {
            System.out.println("[KQU Auth] Invalid credentials for identifier: " + identifier);
        }
        return user;
    }

    public void logout() {
        if (this.loggedInUser != null) {
            System.out.println("[KQU Auth] User logged out: " + this.loggedInUser.getUsername());
        }
        this.loggedInUser = null;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public boolean isAuthenticated() {
        return loggedInUser != null;
    }

    public boolean isAdmin() {
        return loggedInUser != null && "ADMIN".equalsIgnoreCase(loggedInUser.getRole());
    }

    public boolean isLecturer() {
        return loggedInUser != null && "LECTURER".equalsIgnoreCase(loggedInUser.getRole());
    }

    public boolean isStudent() {
        return loggedInUser != null && "STUDENT".equalsIgnoreCase(loggedInUser.getRole());
    }
}
