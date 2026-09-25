package com.kqu.models;

import java.io.Serializable;

/**
 * Enrollment.java
 * Represents student registration for a course unit, including continuous
 * assessment (coursework / 40), examination (/ 60), total marks, grade, and GP.
 */
public class Enrollment implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String studentId;
    private String courseUnitId;
    private String academicYear;
    private int semester;
    private double courseworkMarks; // 0 to 40
    private double examMarks;       // 0 to 60
    private double totalMarks;      // 0 to 100
    private String grade;           // A, B+, B, C+, C, D, F
    private double gradePoint;      // 5.0 down to 0.0
    private String remarks;

    public Enrollment() {}

    public Enrollment(String id, String studentId, String courseUnitId, String academicYear, int semester) {
        this.id = id;
        this.studentId = studentId;
        this.courseUnitId = courseUnitId;
        this.academicYear = academicYear;
        this.semester = semester;
        this.courseworkMarks = 0.0;
        this.examMarks = 0.0;
        this.totalMarks = 0.0;
        calculateGrade();
    }

    /**
     * Automatic Grade & Grade Point calculation according to standard university grading policy:
     * 80 - 100% -> A (5.0)
     * 75 - 79%  -> B+ (4.5)
     * 70 - 74%  -> B (4.0)
     * 65 - 69%  -> C+ (3.5)
     * 60 - 64%  -> C (3.0)
     * 50 - 59%  -> D (2.0)
     * < 50%     -> F (0.0)
     */
    public void calculateGrade() {
        this.totalMarks = this.courseworkMarks + this.examMarks;
        if (this.totalMarks >= 80.0) {
            this.grade = "A";
            this.gradePoint = 5.0;
            this.remarks = "Excellent";
        } else if (this.totalMarks >= 75.0) {
            this.grade = "B+";
            this.gradePoint = 4.5;
            this.remarks = "Very Good";
        } else if (this.totalMarks >= 70.0) {
            this.grade = "B";
            this.gradePoint = 4.0;
            this.remarks = "Good";
        } else if (this.totalMarks >= 65.0) {
            this.grade = "C+";
            this.gradePoint = 3.5;
            this.remarks = "Fair";
        } else if (this.totalMarks >= 60.0) {
            this.grade = "C";
            this.gradePoint = 3.0;
            this.remarks = "Pass";
        } else if (this.totalMarks >= 50.0) {
            this.grade = "D";
            this.gradePoint = 2.0;
            this.remarks = "Marginal Pass";
        } else {
            this.grade = "F";
            this.gradePoint = 0.0;
            this.remarks = "Retake";
        }
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getCourseUnitId() { return courseUnitId; }
    public void setCourseUnitId(String courseUnitId) { this.courseUnitId = courseUnitId; }

    public String getAcademicYear() { return academicYear; }
    public void setAcademicYear(String academicYear) { this.academicYear = academicYear; }

    public int getSemester() { return semester; }
    public void setSemester(int semester) { this.semester = semester; }

    public double getCourseworkMarks() { return courseworkMarks; }
    public void setCourseworkMarks(double courseworkMarks) {
        this.courseworkMarks = Math.min(40.0, Math.max(0.0, courseworkMarks));
        calculateGrade();
    }

    public double getExamMarks() { return examMarks; }
    public void setExamMarks(double examMarks) {
        this.examMarks = Math.min(60.0, Math.max(0.0, examMarks));
        calculateGrade();
    }

    public double getTotalMarks() { return totalMarks; }
    public String getGrade() { return grade; }
    public double getGradePoint() { return gradePoint; }
    public String getRemarks() { return remarks; }
}
