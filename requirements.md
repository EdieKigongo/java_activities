# Requirements Document

## Introduction

The Smart Campus Student Management and Academic Information System is a comprehensive Java Spring Boot application designed to centralize and automate academic administration processes. The system addresses critical inefficiencies in manual record-keeping including data duplication, information loss, access delays, result inaccuracies, and unauthorized access to confidential information. The system provides role-based access for administrators, lecturers, and students to manage academic structures, course assignments, attendance tracking, assessment management, grade calculation, and transcript generation.

## Glossary

- **System**: The Smart Campus Student Management and Academic Information System
- **Administrator**: A user role with full system privileges to manage all entities and generate reports
- **Lecturer**: A user role assigned to teach courses with privileges to manage attendance, assessments, and view student performance
- **Student**: A user role representing enrolled learners with privileges to view academic information and register for courses
- **Program**: An academic program (e.g., Bachelor of Computer Science) consisting of multiple courses
- **Department**: An organizational unit that manages programs and courses
- **Course**: A specific subject offering within a program (e.g., Data Structures)
- **Course_Unit**: An individual component or module within a course
- **Academic_Year**: A 12-month period during which academic activities occur
- **Semester**: A division of the academic year (e.g., Fall, Spring)
- **Attendance_Record**: Documentation of student presence in scheduled classes
- **Coursework_Mark**: Points awarded for assignments, projects, and continuous assessment
- **Examination_Mark**: Points awarded for formal examinations
- **GPA**: Grade Point Average calculated from course grades
- **Transcript**: An official academic record showing courses, grades, and GPA
- **User_Account**: Authentication credentials and role assignment for system access
- **Assessment**: Evaluation mechanism including coursework and examinations
- **Grade**: Letter or numeric representation of academic performance (e.g., A, B+, 3.7)
- **Course_Registration**: The process of enrolling a student in specific courses
- **Dashboard**: Role-specific interface displaying relevant information and actions

## Requirements

### Requirement 1: User Authentication and Authorization

**User Story:** As a system user, I want secure role-based authentication, so that only authorized personnel can access appropriate system functions.

#### Acceptance Criteria

1. WHEN a user provides a username of 3 to 50 characters and a password matching stored credentials, THE System SHALL authenticate the user and grant access to role-specific functions
2. WHEN a user provides invalid credentials, THE System SHALL deny access and display an error message indicating invalid username or password
3. IF a user provides invalid credentials 5 times within 15 minutes, THEN THE System SHALL lock the account for 30 minutes and display an error message indicating account lockout
4. THE System SHALL enforce role-based authorization where Administrators access all system functions, Lecturers access attendance management and assessment entry functions, and Students access course registration and academic record viewing functions
5. WHEN an unauthorized user attempts to access restricted functions, THE System SHALL deny access with HTTP status 403 and log the attempt with username, timestamp, and requested function
6. THE System SHALL require passwords of 8 to 128 characters containing at least 1 uppercase letter, 1 lowercase letter, 1 numeric digit, and 1 special character
7. WHEN a user successfully authenticates, THE System SHALL create a session that expires after 30 minutes where inactivity is defined as no HTTP request received
8. IF a user attempts to access a function requiring authentication without a valid session, THEN THE System SHALL redirect to the login page with an error message indicating session expired
9. WHEN a user explicitly logs out, THE System SHALL immediately invalidate the session and redirect to the login page

### Requirement 2: Student Registration and Management

**User Story:** As an Administrator, I want to register and manage student information, so that the institution maintains accurate student records.

#### Acceptance Criteria

1. WHEN an Administrator provides first name, last name, date of birth, gender, email, phone number, program identifier, and enrollment date, THE System SHALL create a new student record with a unique system-generated identifier
2. IF any required field is missing or empty when creating a student record, THEN THE System SHALL reject the creation and display an error message indicating which required fields are missing
3. THE System SHALL store student first name of 1 to 100 characters, last name of 1 to 100 characters, date of birth in YYYY-MM-DD format, gender from enumeration Male or Female or Other, email of 5 to 100 characters, phone number of 7 to 20 characters, program identifier, and enrollment date in YYYY-MM-DD format
4. WHEN an Administrator requests student information update, THE System SHALL modify the existing record and store the modification timestamp in ISO 8601 format
5. WHEN an Administrator searches for a student by name or identifier, THE System SHALL return matching student records within 2 seconds using case-insensitive substring matching
6. IF a search query returns no matching students, THEN THE System SHALL display a message indicating no students found matching the search criteria
7. THE System SHALL validate email format as containing exactly one at symbol with at least one character before and after the at symbol before creating or updating student records
8. IF email format validation fails, THEN THE System SHALL reject the creation or update and display an error message indicating invalid email format
9. WHEN an Administrator requests to deactivate a student, THE System SHALL mark the student record status as inactive and store the deactivation timestamp without deleting the record
10. IF an Administrator attempts to create a student with an email that already exists for another active student, THEN THE System SHALL reject the creation and display an error message indicating duplicate email address

### Requirement 3: Academic Structure Management

**User Story:** As an Administrator, I want to manage programs, departments, courses, and course units, so that the academic structure accurately reflects institutional offerings.

#### Acceptance Criteria

1. WHEN an Administrator creates a department with department name, code, and head of department identifier, THE System SHALL store the department record with a unique system-generated identifier
2. IF department name, code, or head of department identifier is missing when creating a department, THEN THE System SHALL reject the creation and display an error message indicating which required fields are missing
3. WHEN an Administrator creates a program, THE System SHALL associate it with a department identifier and store program name of 1 to 200 characters, code of 2 to 20 characters, duration in years as an integer between 1 and 10, and degree type from enumeration Bachelor or Master or Doctorate
4. WHEN an Administrator creates a course, THE System SHALL store course name of 1 to 200 characters, code of 2 to 20 characters, credit hours as a decimal between 0.5 and 6.0, and associate it with a program identifier
5. WHEN an Administrator creates a course unit, THE System SHALL associate it with a course identifier and store unit name of 1 to 200 characters and description of 0 to 1000 characters
6. IF an Administrator attempts to create a department, program, or course with a code that already exists, THEN THE System SHALL reject the creation and display an error message indicating duplicate code
7. WHEN an Administrator updates a department, program, course, or course unit, THE System SHALL validate that all referenced identifiers exist before saving changes
8. IF referential integrity validation fails during an update, THEN THE System SHALL reject the update and display an error message indicating which referenced entity does not exist
9. IF an Administrator attempts to delete a department with associated programs, THEN THE System SHALL prevent deletion and display an error message indicating the department has dependent programs
10. IF an Administrator attempts to delete a program with associated courses, THEN THE System SHALL prevent deletion and display an error message indicating the program has dependent courses
11. IF an Administrator attempts to delete a course with enrolled students, THEN THE System SHALL prevent deletion and display an error message indicating the course has enrolled students

### Requirement 4: Academic Calendar Management

**User Story:** As an Administrator, I want to manage academic years and semesters, so that academic activities are organized by time periods.

#### Acceptance Criteria

1. WHEN an Administrator creates an academic year, THE System SHALL store start date, end date, and year label of 1 to 50 characters
2. IF the academic year end date is before the start date, THEN THE System SHALL reject the creation and display an error message indicating invalid date range
3. WHEN an Administrator creates a semester, THE System SHALL associate it with an academic year and store semester name of 1 to 100 characters, start date, and end date
4. IF the semester end date is before the start date, THEN THE System SHALL reject the creation and display an error message indicating invalid date range
5. IF semester start date or end date falls outside the associated academic year date range, THEN THE System SHALL reject the creation and display an error message indicating dates must fall within the academic year
6. IF a semester's date range overlaps with any existing semester in the same academic year, THEN THE System SHALL reject the creation and display an error message indicating overlapping dates
7. WHEN an Administrator marks a semester as active, THE System SHALL designate it as the current registration period and deactivate any previously active semester
8. THE System SHALL ensure only one semester has active status at any time

### Requirement 5: Lecturer Assignment to Courses

**User Story:** As an Administrator, I want to assign lecturers to courses, so that teaching responsibilities are clearly defined.

#### Acceptance Criteria

1. WHEN an Administrator assigns a lecturer identifier to a course identifier for a semester identifier and academic year identifier, THE System SHALL create an assignment record linking these four entities with a unique system-generated assignment identifier
2. IF an Administrator attempts to create an assignment with a lecturer identifier that does not exist, THEN THE System SHALL reject the assignment and display an error message indicating invalid lecturer identifier
3. IF an Administrator attempts to create an assignment with a course identifier that does not exist, THEN THE System SHALL reject the assignment and display an error message indicating invalid course identifier
4. THE System SHALL allow multiple lecturers to be assigned to the same course identifier in different semester identifiers
5. IF an Administrator attempts to assign the same lecturer identifier to the same course identifier in the same semester identifier, THEN THE System SHALL reject the assignment and display an error message indicating duplicate assignment
6. WHEN an Administrator requests to remove a lecturer assignment, THE System SHALL verify that no attendance records or assessment records reference the assignment identifier
7. IF attendance or assessment records exist for the assignment, THEN THE System SHALL prevent deletion and display an error message indicating dependent records exist
8. IF no dependent records exist, THEN THE System SHALL delete the assignment record when the Administrator requests removal
9. WHEN a Lecturer with lecturer identifier L logs in, THE System SHALL display all courses where an assignment record exists linking lecturer identifier L to a course identifier for the current active semester showing course name, course code, and credit hours

### Requirement 6: Student Course Registration

**User Story:** As a Student, I want to register for courses, so that I can enroll in classes for the current semester.

#### Acceptance Criteria

1. WHEN a Student views available courses, THE System SHALL display only courses where the course program matches the Student assigned program
2. IF the current date is outside the registration period defined for the semester, THEN THE System SHALL reject registration attempts with an error message indicating registration is closed
3. WHEN a Student selects a course for registration, THE System SHALL validate that all prerequisite courses for the selected course have been completed with passing grades by the Student
4. IF a Student attempts to register for a course without satisfied prerequisites, THEN THE System SHALL reject the registration and display an error message indicating which prerequisites are missing
5. IF a Student attempts to register for a course that has reached its enrollment capacity, THEN THE System SHALL reject the registration and display an error message indicating the course is full
6. IF a Student attempts to register for a course already in the Student active registrations for the current semester, THEN THE System SHALL reject the registration and display an error message indicating duplicate registration
7. IF a Student registration would cause total registered credit hours for the semester to exceed 24 credits, THEN THE System SHALL reject the registration and display an error message indicating the credit limit exceeded
8. WHEN a Student successfully registers for a course, THE System SHALL create a course registration record with status enrolled
9. WHEN a Student successfully registers for a course, THE System SHALL send a confirmation notification containing course name, course code, credit hours, and meeting schedule within 60 seconds
10. WHEN a Student requests to drop a course within 14 calendar days from the semester start date, THE System SHALL remove the registration record and set status to dropped
11. IF a Student requests to drop a course after 14 calendar days from the semester start date, THEN THE System SHALL set registration status to pending drop and create an approval request for an Administrator
12. WHEN an Administrator approves a late drop request, THE System SHALL remove the registration record and set status to dropped
13. IF an Administrator denies a late drop request, THEN THE System SHALL restore registration status to enrolled and notify the Student of the denial

### Requirement 7: Attendance Tracking

**User Story:** As a Lecturer, I want to record student attendance, so that attendance is monitored and tracked throughout the semester.

#### Acceptance Criteria

1. WHEN a Lecturer marks attendance for a class session, THE System SHALL create attendance records for all students registered for the course with initial status Absent
2. WHEN a Lecturer sets a student attendance status, THE System SHALL accept only the values Present, Absent, or Excused
3. IF a Lecturer attempts to mark attendance for a class session already marked for the same date, THEN THE System SHALL reject the submission and display an error message indicating duplicate attendance record
4. WHEN a Lecturer submits attendance, THE System SHALL store the record with date in YYYY-MM-DD format and time in HH:MM:SS format
5. WHEN calculating attendance percentage, THE System SHALL compute (count of Present plus count of Excused) divided by (total count of attendance records) multiplied by 100 and round to 2 decimal places
6. IF total count of attendance records equals zero when calculating percentage, THEN THE System SHALL set attendance percentage to 0.00
7. WHEN a Student views attendance for a course, THE System SHALL display attendance percentage and a list of attendance records showing date, status, and course name
8. WHEN a Lecturer requests to modify an attendance record within 24 hours of the submission timestamp, THE System SHALL allow the modification and update the timestamp
9. IF a Lecturer requests to modify an attendance record more than 24 hours after the submission timestamp, THEN THE System SHALL reject the modification and display an error message indicating modification window expired
10. IF attendance percentage is calculated as less than 75.00, THEN THE System SHALL set a flag on the student course registration record and send a notification to the Student within 24 hours
11. IF attendance percentage is later calculated as 75.00 or greater for a flagged record, THEN THE System SHALL remove the flag from the student course registration record

### Requirement 8: Assessment Management

**User Story:** As a Lecturer, I want to enter coursework and examination marks, so that student performance is accurately recorded.

#### Acceptance Criteria

1. WHEN a Lecturer enters coursework marks, THE System SHALL validate that marks are numeric values between 0.0 and 40.0 inclusive with maximum one decimal place
2. WHEN a Lecturer enters examination marks, THE System SHALL validate that marks are numeric values between 0.0 and 60.0 inclusive with maximum one decimal place
3. IF coursework or examination marks fail validation, THEN THE System SHALL reject the entry, retain the invalid value in the input field, and display an error message indicating the valid range
4. THE System SHALL store assessment marks with timestamps and the lecturer identifier
5. WHEN a Lecturer submits marks, THE System SHALL allow modification within 48 hours from the submission timestamp
6. IF a Lecturer attempts to modify marks more than 48 hours after submission, THEN THE System SHALL prevent the modification and display an error message indicating the deadline has passed
7. IF a Lecturer attempts to enter marks for a student not registered for the course, THEN THE System SHALL reject the entry and display an error message indicating the student is not registered
8. THE System SHALL prevent marks entry for students not registered for the course
9. WHEN both coursework marks and examination marks are entered for all students registered for the course, THE System SHALL enable final grade calculation
10. THE System SHALL require Lecturer authentication before allowing marks entry or modification

### Requirement 9: Grade Calculation

**User Story:** As a system, I want to automatically calculate grades and GPA, so that academic performance is consistently evaluated.

#### Acceptance Criteria

1. WHEN coursework marks (0.0 to 100.0) and examination marks (0.0 to 100.0) are entered, THE System SHALL calculate total marks as coursework marks plus examination marks
2. IF coursework marks or examination marks are missing, non-numeric, less than 0.0, or greater than 100.0, THEN THE System SHALL reject the entry with an error message indicating the invalid field and valid range
3. IF total marks exceed 100.0, THEN THE System SHALL cap total marks at 100.0 before grade assignment
4. WHEN total marks are calculated, THE System SHALL assign letter grades based on total marks: A (80.0-100.0), B (70.0-79.9), C (60.0-69.9), D (50.0-59.9), F (0.0-49.9)
5. WHEN a letter grade is assigned, THE System SHALL assign grade points: A equals 4.0, B equals 3.0, C equals 2.0, D equals 1.0, F equals 0.0
6. IF credit hours for a course are missing, non-numeric, less than 0.5, or greater than 6.0, THEN THE System SHALL reject the entry with an error message indicating valid credit hour range is 0.5 to 6.0
7. WHEN grade points and credit hours are available for all courses in a semester, THE System SHALL calculate semester GPA as the sum of (grade points multiplied by credit hours) divided by total credit hours for that semester
8. IF total credit hours for a semester equal zero, THEN THE System SHALL set semester GPA to 0.0
9. WHEN a semester GPA is calculated and all final grades for that semester are recorded, THE System SHALL mark that semester as completed
10. WHEN at least one semester is marked as completed, THE System SHALL calculate cumulative GPA as the sum of (grade points multiplied by credit hours) across all completed semesters divided by total credit hours across all completed semesters
11. IF total credit hours across all completed semesters equal zero, THEN THE System SHALL set cumulative GPA to 0.0
12. WHEN GPA or cumulative GPA is calculated, THE System SHALL round the result to two decimal places using round-half-up method
13. WHEN marks are updated, THE System SHALL recalculate the affected course grade, the semester GPA for that course's semester, and the cumulative GPA within 5 seconds

### Requirement 10: Transcript Generation

**User Story:** As a Student, I want to generate and download my academic transcript, so that I can share my academic record.

#### Acceptance Criteria

1. WHEN a Student with student identifier S requests a transcript, THE System SHALL generate a PDF document containing student information for student S, all courses taken by student S, grades for those courses, and cumulative GPA for student S
2. IF a Student has completed zero courses when requesting a transcript, THEN THE System SHALL generate a transcript showing student information and a message indicating no completed courses
3. THE System SHALL organize transcript by academic year identifier and semester identifier in chronological ascending order
4. THE System SHALL include on each transcript the institution name, student first name, student last name, student identifier, program name, and generation date in YYYY-MM-DD format
5. THE System SHALL display on the transcript credit hours earned per semester rounded to 1 decimal place and cumulative credit hours rounded to 1 decimal place
6. THE System SHALL display cumulative GPA rounded to 2 decimal places and individual course grades as letter grades
7. WHEN a transcript is generated, THE System SHALL create a unique alphanumeric transcript identifier of 16 characters for verification
8. WHEN a Student requests transcript download, THE System SHALL provide the PDF file for download
9. IF transcript generation fails, THEN THE System SHALL display an error message indicating transcript generation failed
10. WHEN an Administrator with administrator identifier A generates a transcript for a student identifier S, THE System SHALL include an official seal image and digital signature associated with administrator A
11. IF an Administrator attempts to generate a transcript for a non-existent student identifier, THEN THE System SHALL reject the request and display an error message indicating invalid student identifier
12. THE System SHALL complete transcript generation within 10 seconds for students with up to 100 completed courses

### Requirement 11: Performance Monitoring

**User Story:** As a Lecturer, I want to view student performance in my courses, so that I can identify students who need additional support.

#### Acceptance Criteria

1. WHEN a Lecturer views course performance, THE System SHALL display a list of enrolled students with each student's overall mark and grade calculated from all completed assessments
2. WHEN a Lecturer views course performance, THE System SHALL calculate and display the arithmetic mean of overall marks as class average, the highest overall mark, and the lowest overall mark
3. WHEN a Lecturer views course performance, THE System SHALL display students with overall marks below 50 with a visual indicator labeled "At-Risk"
4. IF no students are enrolled in the course, THEN THE System SHALL display a message indicating no performance data is available
5. IF a student has no completed assessments, THEN THE System SHALL display "No marks" for that student and exclude them from class average calculations
6. WHEN a Lecturer filters by performance level, THE System SHALL display students matching the selected level where levels are "At-Risk" (marks below 50), "Passing" (marks 50 to 74), and "Distinction" (marks 75 and above)
7. WHEN a Lecturer views course performance, THE System SHALL display each student's percentage change between their most recent assessment mark and their overall mark as a performance trend
8. WHEN a Lecturer initiates a performance data export, THE System SHALL generate a CSV file containing student name, student ID, overall mark, grade, and at-risk status for all enrolled students

### Requirement 12: Academic Reporting

**User Story:** As an Administrator, I want to generate comprehensive academic reports, so that institutional performance can be analyzed.

#### Acceptance Criteria

1. WHEN an Administrator requests an enrollment report, THE System SHALL generate statistics showing student enrollment count grouped by program name, department name, and semester name
2. IF no enrollment data exists for the selected filters, THEN THE System SHALL display a message indicating no enrollment data available for the selected criteria
3. WHEN an Administrator requests a performance report, THE System SHALL generate statistics showing arithmetic mean GPA rounded to 2 decimal places grouped by program name and semester name
4. IF no GPA data exists for the selected filters, THEN THE System SHALL display a message indicating no performance data available for the selected criteria
5. WHEN an Administrator requests a lecturer workload report, THE System SHALL display course assignments showing lecturer name, course name, course code, and enrolled student count per lecturer
6. WHEN an Administrator requests an attendance report, THE System SHALL generate statistics showing arithmetic mean attendance percentage rounded to 2 decimal places grouped by course name and program name
7. THE System SHALL allow report filtering by academic year identifier, semester identifier, department identifier, and program identifier with multiple filters applied using AND logic
8. WHEN an Administrator requests report export in PDF format, THE System SHALL generate a PDF file containing the report data
9. WHEN an Administrator requests report export in CSV format, THE System SHALL generate a CSV file containing the report data
10. IF report generation or export fails, THEN THE System SHALL display an error message indicating the failure reason
11. WHEN a report is generated, THE System SHALL complete processing within 10 seconds for datasets up to 10,000 records

### Requirement 13: Dashboard Interfaces

**User Story:** As a user, I want a role-specific dashboard, so that I can quickly access relevant information and actions.

#### Acceptance Criteria

1. WHEN an Administrator logs in, THE System SHALL display a dashboard showing count of total students, count of total lecturers, count of active courses, and list of activities from the most recent 30 days
2. WHEN a Lecturer logs in, THE System SHALL display assigned courses for the current semester, classes scheduled within the next 7 days, courses with pending marks entry, and attendance submissions from the most recent 14 days
3. WHEN a Student logs in, THE System SHALL display enrolled courses for the current semester, current semester GPA rounded to 2 decimal places, attendance summary showing percentage per course, and assessments scheduled within the next 14 days
4. WHEN the user navigates back to the dashboard page, THE System SHALL refresh all dashboard data with current values
5. THE System SHALL display quick action buttons where Administrator dashboard shows "Add Student" and "Generate Report", Lecturer dashboard shows "Mark Attendance" and "Enter Marks", and Student dashboard shows "Register Courses" and "View Transcript"
6. THE System SHALL load dashboard content within 3 seconds of authentication completion
7. IF dashboard data retrieval fails for any component, THEN THE System SHALL display an error message for that component while showing successfully retrieved components

### Requirement 14: Data Validation and Integrity

**User Story:** As a system, I want to enforce data validation rules, so that data integrity is maintained throughout the application.

#### Acceptance Criteria

1. WHEN creating or updating any entity, THE System SHALL validate that all fields marked as required contain non-null and non-empty values
2. IF any required field is null or empty, THEN THE System SHALL reject the operation and display an error message listing all missing required fields
3. THE System SHALL enforce referential integrity between related entities using foreign key constraints where child records cannot exist without valid parent records
4. WHEN a user attempts to delete a record with dependent child records, THE System SHALL prevent deletion and display an error message indicating the count and type of dependent records
5. WHEN validating date fields, THE System SHALL accept only dates in YYYY-MM-DD format
6. IF a date field does not match YYYY-MM-DD format, THEN THE System SHALL reject the operation and display an error message indicating invalid date format
7. WHEN comparing start date and end date fields, THE System SHALL verify that end date is chronologically after start date
8. IF end date is not after start date, THEN THE System SHALL reject the operation and display an error message indicating invalid date range
9. THE System SHALL validate numeric fields to ensure they contain only numeric characters, optional decimal point, and optional negative sign
10. THE System SHALL validate that numeric field values fall within the minimum and maximum bounds defined for that field
11. IF a numeric field value is outside the valid range, THEN THE System SHALL reject the operation and display an error message indicating the field name and valid range
12. WHEN processing text input fields, THE System SHALL trim leading and trailing whitespace before validation and storage
13. WHEN validation fails for any field, THE System SHALL display an error message containing the field name and the specific validation rule that failed

### Requirement 15: User Account Management

**User Story:** As an Administrator, I want to manage user accounts, so that system access is controlled and maintained.

#### Acceptance Criteria

1. WHEN an Administrator creates a user account, THE System SHALL store username of 3 to 50 characters, password hash using a secure hashing algorithm, role from enumeration Administrator or Lecturer or Student, and associated entity identifier (student identifier or lecturer identifier)
2. IF an Administrator attempts to create a user account with a username that already exists, THEN THE System SHALL reject the creation and display an error message indicating duplicate username
3. THE System SHALL enforce unique usernames across all accounts by preventing creation or modification that would create duplicate usernames
4. WHEN an Administrator resets a password for a user account, THE System SHALL generate a temporary password of 12 characters containing uppercase letters, lowercase letters, and numeric digits
5. WHEN a user logs in with a temporary password, THE System SHALL require immediate password change before granting access to other functions
6. WHEN an Administrator activates a user account, THE System SHALL set account status to active and allow login attempts for that username
7. WHEN an Administrator deactivates a user account, THE System SHALL set account status to inactive and prevent future login attempts for that username
8. IF a user attempts to log in with a deactivated account, THEN THE System SHALL prevent login and display an error message indicating account is deactivated
9. WHEN an Administrator creates, modifies, or deletes a user account, THE System SHALL create an audit log entry containing administrator identifier, action type, affected username, timestamp in ISO 8601 format, and changed field names
10. WHEN associating a Student role account, THE System SHALL validate that the student identifier exists in the student records
11. WHEN associating a Lecturer role account, THE System SHALL validate that the lecturer identifier exists in the lecturer records
12. IF the associated entity identifier does not exist, THEN THE System SHALL reject the account creation and display an error message indicating invalid entity identifier

### Requirement 16: Audit Logging

**User Story:** As an Administrator, I want to track system activities, so that security and compliance can be monitored.

#### Acceptance Criteria

1. WHEN a user successfully logs in, THE System SHALL create an audit log entry with username, IP address, and timestamp in ISO 8601 format
2. WHEN a user login attempt fails, THE System SHALL create an audit log entry with attempted username, IP address, failure reason, and timestamp in ISO 8601 format
3. WHEN marks are entered or modified, THE System SHALL log the action with lecturer identifier, student identifier, course identifier, previous value, new value, and timestamp in ISO 8601 format
4. WHEN user accounts are created, modified, or deleted, THE System SHALL log the action with administrator identifier, affected user identifier, action type, changed fields, previous values, new values, and timestamp in ISO 8601 format
5. THE System SHALL store audit logs for at least 365 days
6. WHEN audit logs exceed 365 days old, THE System SHALL retain the logs until explicitly archived or deleted by an Administrator
7. WHEN an Administrator views audit logs, THE System SHALL display filterable logs by user identifier, action type, and date range within 5 seconds for result sets up to 10,000 entries
8. WHEN an Administrator requests audit logs exceeding 10,000 entries, THE System SHALL paginate results with maximum 1,000 entries per page
9. IF a non-administrator user attempts to modify or delete audit logs, THEN THE System SHALL deny the operation and log the unauthorized attempt with user identifier and timestamp in ISO 8601 format
10. THE System SHALL prevent administrators from modifying audit log entries after creation

### Requirement 17: Data Export and Backup

**User Story:** As an Administrator, I want to export and backup system data, so that data can be preserved and analyzed externally.

#### Acceptance Criteria

1. WHEN an Administrator requests a data export, THE System SHALL allow the Administrator to select either CSV or JSON format
2. WHEN an Administrator requests a data export in a selected format, THE System SHALL generate one or more files containing student records, course data, attendance records, and assessment data in the selected format within 300 seconds
3. IF a data export operation exceeds 300 seconds, THEN THE System SHALL terminate the export operation and notify the Administrator with an error message indicating timeout
4. IF a data export operation fails for any reason other than timeout, THEN THE System SHALL notify the Administrator with an error message indicating the failure reason and discard any partially generated files
5. WHEN an Administrator initiates a database backup, THE System SHALL create a complete backup file containing all student records, course data, attendance records, assessment data, and system configuration within 600 seconds
6. WHEN a backup file is created, THE System SHALL append a timestamp to the filename in the format YYYY-MM-DD-HH-MM-SS
7. IF a database backup operation exceeds 600 seconds, THEN THE System SHALL terminate the backup operation and notify the Administrator with an error message indicating timeout
8. THE System SHALL allow an Administrator to select a previously created backup file for restoration
9. WHEN an Administrator selects a backup file for restoration, THE System SHALL validate the backup file integrity before proceeding with restoration
10. IF backup file validation detects corruption or incompatibility, THEN THE System SHALL prevent restoration and notify the Administrator with an error message indicating validation failure
11. WHEN backup file validation succeeds, THE System SHALL restore all data from the backup file within 600 seconds
12. IF a restoration operation fails or exceeds 600 seconds, THEN THE System SHALL preserve the existing database state and notify the Administrator with an error message indicating restoration failure
13. WHEN a backup restoration completes, THE System SHALL notify the Administrator with a success message and the count of restored records
14. WHEN a backup file is created, THE System SHALL notify the Administrator with the backup file location and file size in megabytes
15. THE System SHALL encrypt all backup files using the encryption standard configured in the system

### Requirement 18: Search and Filter Functionality

**User Story:** As a user, I want to search and filter records efficiently, so that I can quickly find specific information.

#### Acceptance Criteria

1. WHEN a user enters a search term of 1 to 100 characters, THE System SHALL return matching records within 2 seconds using case-insensitive substring matching
2. IF a user enters a search term with less than 1 character or more than 100 characters, THEN THE System SHALL reject the search and display an error message indicating search term must be 1 to 100 characters
3. THE System SHALL support search by student first name, student last name, student identifier, student email, and program name
4. THE System SHALL support search by course name, course code, and department name
5. THE System SHALL allow filtering by academic year identifier, semester identifier, department identifier, and program identifier
6. WHEN multiple filters are applied, THE System SHALL combine them using AND logic where results match all selected filters
7. THE System SHALL display search results in paginated format with 25 records per page
8. THE System SHALL order search results alphabetically by the primary name field (student last name for students, course name for courses)
9. THE System SHALL display the total count of matching records above the paginated results
10. IF search or filter produces more than 10,000 results, THEN THE System SHALL display only the first 10,000 results with a message indicating result limit reached
11. THE System SHALL highlight search terms in result displays using bold text
12. WHEN no results match the search criteria, THE System SHALL display a message indicating no records found matching your search
13. IF a user submits an empty search term, THEN THE System SHALL display all records subject to current filter selections up to pagination limits
14. WHEN a user navigates between result pages, THE System SHALL maintain the current search term and filter selections
15. THE System SHALL allow users to clear all filters and search terms using a "Clear All" button
16. IF a user clears all filters and search terms, THEN THE System SHALL display all records from the first page
17. WHEN a user applies a filter with zero matching records, THE System SHALL display a message indicating no records match the selected filters

### Requirement 19: Notification System

**User Story:** As a user, I want to receive notifications about important events, so that I stay informed about academic activities.

#### Acceptance Criteria

1. WHEN a Student registers for one or more courses, THE System SHALL send a confirmation notification to that Student within 10 seconds
2. IF a Student's attendance percentage is below 75 percent, THEN THE System SHALL send a warning notification to that Student within 24 hours of the attendance record update
3. WHEN marks are published for a course, THE System SHALL notify all Students enrolled in that course within 10 seconds
4. WHEN a Lecturer is assigned to a course, THE System SHALL send an assignment notification to that Lecturer within 10 seconds
5. THE System SHALL display up to 100 most recent unread notifications in the user dashboard, ordered by creation time with newest first
6. THE System SHALL allow users to mark notifications as read individually or mark all as read
7. THE System SHALL retain notifications for 90 days from the notification creation timestamp before automatic deletion
8. IF notification delivery fails after 3 retry attempts within 60 seconds, THEN THE System SHALL log the failure and mark the notification as undeliverable
9. THE System SHALL include in each notification the event type, timestamp, and a description of the triggering event

### Requirement 20: System Configuration and Settings

**User Story:** As an Administrator, I want to configure system settings, so that the system behavior matches institutional policies.

#### Acceptance Criteria

1. WHEN an Administrator submits a maximum credit hours per semester value, THE System SHALL accept integer values between 1 and 30 inclusive
2. WHEN an Administrator submits a minimum attendance percentage threshold value, THE System SHALL accept numeric values between 0.00 and 100.00 inclusive
3. WHEN an Administrator submits grade boundaries for letter grades, THE System SHALL accept numeric values between 0.00 and 100.00 inclusive for each grade boundary
4. WHEN an Administrator submits a course registration deadline, THE System SHALL accept date and time values that are between 1 and 365 days relative to the semester start date
5. WHEN an Administrator submits a session timeout duration, THE System SHALL accept integer values between 1 and 1440 minutes inclusive
6. WHEN configuration changes are saved, THE System SHALL apply them to all subsequent operations within 5 seconds
7. IF a submitted configuration value is outside the acceptable range for that setting, THEN THE System SHALL reject the value and display an error message indicating the acceptable range
8. WHEN an Administrator requests to view system settings, THE System SHALL display the current values for all configurable settings
9. WHEN an Administrator successfully saves configuration changes, THE System SHALL display a confirmation message indicating the changes were applied
