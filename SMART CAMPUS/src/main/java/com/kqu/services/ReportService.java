package com.kqu.services;

import com.kqu.models.Enrollment;
import com.kqu.models.Student;

import java.util.List;

/**
 * ReportService.java
 * Service responsible for generating Official Academic Transcripts,
 * Examination Board reports, and Student Performance statistics for KQU.
 */
public class ReportService {

    /**
     * Determines degree classification based on Cumulative GPA (CGPA) on 5.0 scale:
     * 4.40 - 5.00: First Class Honours
     * 3.60 - 4.39: Second Class Honours (Upper Division)
     * 2.80 - 3.59: Second Class Honours (Lower Division)
     * 2.00 - 2.79: Pass Degree
     * < 2.00: Fail / Discontinued
     */
    public String getDegreeClassification(double cgpa) {
        if (cgpa >= 4.40) return "FIRST CLASS HONOURS";
        if (cgpa >= 3.60) return "SECOND CLASS HONOURS (UPPER DIVISION)";
        if (cgpa >= 2.80) return "SECOND CLASS HONOURS (LOWER DIVISION)";
        if (cgpa >= 2.00) return "PASS DEGREE";
        return "FAIL / ACADEMIC PROBATION";
    }

    /**
     * Formats an official text transcript report for a student.
     */
    public String generateOfficialTranscript(Student student, List<Enrollment> enrollments) {
        StringBuilder sb = new StringBuilder();
        sb.append("=================================================================\n");
        sb.append("             KING'S AND QUEEN'S UNIVERSITY (KQU)\n");
        sb.append("                 OFFICE OF THE ACADEMIC REGISTRAR\n");
        sb.append("                   OFFICIAL ACADEMIC TRANSCRIPT\n");
        sb.append("=================================================================\n\n");
        sb.append("STUDENT NAME:        ").append(student.getFullName().toUpperCase()).append("\n");
        sb.append("REGISTRATION NUMBER: ").append(student.getRegNumber()).append("\n");
        sb.append("FACULTY:             ").append(student.getFacultyName() != null ? student.getFacultyName() : "N/A").append("\n");
        sb.append("PROGRAMME OF STUDY:  ").append(student.getCourseName() != null ? student.getCourseName() : "N/A").append("\n");
        sb.append("CURRENT YEAR:        YEAR ").append(student.getYearOfStudy()).append("\n");
        sb.append("OVERALL ATTENDANCE:  ").append(student.getAttendanceRate()).append("%\n\n");

        sb.append("-----------------------------------------------------------------\n");
        sb.append(String.format("%-10s %-32s %-6s %-6s %-6s\n", "CODE", "COURSE UNIT TITLE", "CW/40", "EX/60", "GRADE"));
        sb.append("-----------------------------------------------------------------\n");

        if (enrollments != null) {
            for (Enrollment e : enrollments) {
                sb.append(String.format("%-10s %-32s %-6.1f %-6.1f %-6s\n",
                        e.getCourseUnitId(),
                        "Module " + e.getCourseUnitId(),
                        e.getCourseworkMarks(),
                        e.getExamMarks(),
                        e.getGrade()));
            }
        }

        sb.append("-----------------------------------------------------------------\n");
        sb.append("CUMULATIVE GPA (CGPA): ").append(String.format("%.2f", student.getCgpa())).append(" / 5.00\n");
        sb.append("AWARD CLASSIFICATION:  ").append(getDegreeClassification(student.getCgpa())).append("\n");
        sb.append("=================================================================\n");
        sb.append("Certified True Record - Signed by Academic Registrar & University Seal\n");
        return sb.toString();
    }
}
