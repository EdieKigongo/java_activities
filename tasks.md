# Implementation Plan: Smart Campus Student Management System

## Overview

This implementation plan provides a structured approach to building the Smart Campus Student Management and Academic Information System using Java Spring Boot. The system implements a three-tier architecture with comprehensive security, validation, and testing. Tasks are organized to ensure dependencies are satisfied before dependent components are implemented.

The implementation follows a bottom-up approach: infrastructure and data layer first, then business logic, followed by API and UI layers, with testing integrated throughout.

## Tasks

- [ ] 1. Set up project infrastructure and dependencies
  - Create Spring Boot project with Maven
  - Configure application.properties for development (H2/SQLite) and production (MySQL)
  - Add dependencies: Spring Boot Starter Web, Spring Data JPA, Spring Security, Spring Validation, Lombok, H2/SQLite, MySQL Connector, BCrypt, JWT library, iText/Apache PDFBox, Log4j2/SLF4J, QuickTheories
  - Configure logging with Log4j2 (separate files for application and audit logs)
  - Set up project package structure: controller, service, repository, model, dto, security, config, exception, util
  - Create application.yml with profiles for dev, test, and production environments
  - _Requirements: All (foundational)_

- [ ] 2. Define domain enums and value objects
  - Create enums: UserRole, AccountStatus, StudentStatus, LecturerStatus, Gender, DegreeType, EnrollmentStatus, AttendanceStatus, AssessmentType, LetterGrade, NotificationType, NotificationStatus, AuditActionType, DropRequestStatus
  - Ensure enum values match requirements specifications exactly
  - Add validation annotations where applicable
  - _Requirements: 1.1, 1.4, 2.3, 7.2, 8.1, 9.4_

- [ ] 3. Implement core JPA entities with relationships
  - [ ] 3.1 Create User entity
    - Define fields: id, username, passwordHash, role, status, failedLoginAttempts, lastLoginTime, accountLockedUntil, requirePasswordChange, createdAt, updatedAt
    - Add JPA annotations: @Entity, @Table, @Id, @GeneratedValue, @Column with constraints
    - Add relationships: @OneToOne with Student, Lecturer, Administrator
    - Add validation annotations: @NotNull, @Size, @Pattern
    - _Requirements: 1.1, 1.6, 15.1_

  - [ ] 3.2 Create Department, Program, Course, CourseUnit entities
    - Define Department: id, name, code, headOfDepartment, createdAt, updatedAt, programs
    - Define Program: id, name, code, durationYears, degreeType, department, courses, students, createdAt, updatedAt
    - Define Course: id, name, code, creditHours, description, enrollmentCapacity, program, courseUnits, prerequisites, enrollments, lecturerAssignments, createdAt, updatedAt
    - Define CourseUnit: id, name, description, course, createdAt, updatedAt
    - Add JPA relationships: @ManyToOne, @OneToMany, @ManyToMany with appropriate fetch types and cascade options
    - Add unique constraints on codes
    - _Requirements: 3.1, 3.3, 3.4, 3.5_

  - [ ] 3.3 Create Student and Lecturer entities
    - Define Student: id, firstName, lastName, dateOfBirth, gender, email, phoneNumber, enrollmentDate, status, deactivatedAt, program, user, enrollments, attendanceRecords, createdAt, updatedAt
    - Define Lecturer: id, firstName, lastName, email, phoneNumber, specialization, status, user, assignments, createdAt, updatedAt
    - Add unique constraints on email fields
    - Add validation annotations for email format, phone number length, name length
    - _Requirements: 2.1, 2.3, 2.7, 5.1_

  - [ ] 3.4 Create AcademicYear and Semester entities
    - Define AcademicYear: id, yearLabel, startDate, endDate, semesters, createdAt, updatedAt
    - Define Semester: id, name, startDate, endDate, active, academicYear, enrollments, lecturerAssignments, createdAt, updatedAt
    - Add check constraints for date validation (endDate > startDate)
    - _Requirements: 4.1, 4.3, 4.7_

  - [ ] 3.5 Create LecturerAssignment, Enrollment, DropRequest entities
    - Define LecturerAssignment: id, lecturer, course, semester, createdAt
    - Define Enrollment: id, student, course, semester, status, enrollmentDate, dropDate, lowAttendanceFlag, totalMarks, letterGrade, gradePoints, createdAt, updatedAt
    - Define DropRequest: id, enrollment, status, reason, adminNotes, reviewedBy, reviewedAt, createdAt
    - Add unique constraint on LecturerAssignment (lecturer, course, semester)
    - _Requirements: 5.1, 6.8, 6.11_

  - [ ] 3.6 Create AttendanceRecord and Assessment entities
    - Define AttendanceRecord: id, enrollment, student, course, attendanceDate, attendanceTime, status, markedBy, createdAt, updatedAt
    - Define Assessment: id, enrollment, student, course, type, marks, enteredBy, createdAt, updatedAt
    - Add unique constraint on AttendanceRecord (enrollment, attendanceDate)
    - Add check constraints for marks validation (coursework 0-40, examination 0-60)
    - _Requirements: 7.1, 7.4, 8.1, 8.2_

  - [ ] 3.7 Create Notification, AuditLog, SystemConfiguration entities
    - Define Notification: id, user, type, message, read, createdAt, readAt, deliveryAttempts, deliveryStatus
    - Define AuditLog: id, user, timestamp, actionType, entityType, entityId, ipAddress, details, previousValue, newValue, success
    - Define SystemConfiguration: id, configKey, configValue, description, updatedAt
    - Add indexes on AuditLog (user_id, timestamp, action_type)
    - Make AuditLog immutable (no update operations)
    - _Requirements: 16.1, 16.3, 19.1, 20.1_

  - [ ] 3.8 Create Administrator entity
    - Define Administrator: id, user, firstName, lastName, email, phoneNumber, createdAt, updatedAt
    - Add @OneToOne relationship with User
    - _Requirements: 15.1_

- [ ] 4. Checkpoint - Verify entity compilation and basic JPA setup
  - Ensure all entities compile without errors
  - Run Spring Boot application to verify JPA schema generation
  - Check application logs for schema creation statements
  - Verify no constraint conflicts or mapping errors

- [ ] 5. Implement repository interfaces
  - [ ] 5.1 Create base repositories
    - UserRepository extending JpaRepository<User, Long>
    - StudentRepository extending JpaRepository<Student, Long>
    - LecturerRepository extending JpaRepository<Lecturer, Long>
    - AdministratorRepository extending JpaRepository<Administrator, Long>
    - DepartmentRepository extending JpaRepository<Department, Long>
    - ProgramRepository extending JpaRepository<Program, Long>
    - CourseRepository extending JpaRepository<Course, Long>
    - CourseUnitRepository extending JpaRepository<CourseUnit, Long>
    - _Requirements: All (data access foundation)_

  - [ ] 5.2 Create operational repositories
    - AcademicYearRepository extending JpaRepository<AcademicYear, Long>
    - SemesterRepository extending JpaRepository<Semester, Long>
    - LecturerAssignmentRepository extending JpaRepository<LecturerAssignment, Long>
    - EnrollmentRepository extending JpaRepository<Enrollment, Long>
    - DropRequestRepository extending JpaRepository<DropRequest, Long>
    - AttendanceRecordRepository extending JpaRepository<AttendanceRecord, Long>
    - AssessmentRepository extending JpaRepository<Assessment, Long>
    - NotificationRepository extending JpaRepository<Notification, Long>
    - AuditLogRepository extending JpaRepository<AuditLog, Long>
    - SystemConfigurationRepository extending JpaRepository<SystemConfiguration, Long>
    - _Requirements: All (data access foundation)_

  - [ ] 5.3 Add custom query methods to StudentRepository
    - @Query for searchStudents with case-insensitive LIKE on firstName, lastName, email
    - findActiveStudentsByProgram method
    - findByEmail method
    - findByUser method
    - Add Pageable support for search queries
    - _Requirements: 2.5, 18.1, 18.3_

  - [ ] 5.4 Add custom query methods to EnrollmentRepository
    - @Query for findStudentEnrollments with JOIN FETCH
    - @Query for getTotalRegisteredCredits using SUM
    - findByStudentAndSemester method
    - findByCourseAndStatus method
    - countByCourseAndStatus method (for capacity checks)
    - _Requirements: 6.1, 6.7_

  - [ ] 5.5 Add custom query methods to AttendanceRecordRepository
    - @Query for countPresentOrExcused
    - @Query for countTotalAttendance
    - findByStudentAndCourse method
    - findByEnrollmentAndAttendanceDate method (for duplicate checks)
    - _Requirements: 7.5, 7.6_

  - [ ] 5.6 Add custom query methods to AssessmentRepository
    - findByEnrollmentAndType method
    - findByStudentAndCourse method
    - findByCourse method (for course summary)
    - _Requirements: 8.4, 11.1_

  - [ ] 5.7 Add custom query methods to AuditLogRepository
    - @Query for searchAuditLogs with filters on userId, actionType, and date range
    - Add Pageable support with maximum 1000 entries per page
    - _Requirements: 16.7, 16.8_

  - [ ] 5.8 Add custom query methods to UserRepository
    - findByUsername method
    - existsByUsername method
    - _Requirements: 1.1, 15.2_

  - [ ] 5.9 Add custom query methods to SemesterRepository
    - findByActiveTrue method (for current semester)
    - findByAcademicYear method
    - _Requirements: 4.7_

  - [ ] 5.10 Add custom query methods to LecturerAssignmentRepository
    - findByLecturerAndSemester method
    - findByLecturerAndCourseAndSemester method (for duplicate checks)
    - countByCourseAndSemester method (for workload)
    - _Requirements: 5.5, 12.5_

- [ ] 6. Implement DTOs (Data Transfer Objects)
  - Create request DTOs: CreateStudentRequest, UpdateStudentRequest, CreateCourseRequest, LoginRequest, MarkAttendanceRequest, etc.
  - Create response DTOs: StudentDTO, CourseDTO, EnrollmentDTO, AttendanceRecordDTO, AssessmentDTO, GradeDTO, GpaDTO, TranscriptDTO, etc.
  - Add validation annotations to request DTOs: @NotNull, @NotBlank, @Size, @Email, @Pattern, @Min, @Max, @Past, @Future
  - Add DTO mappers or use MapStruct for entity-to-DTO conversions
  - _Requirements: All (API contracts)_

- [ ] 7. Implement utility classes and validators
  - [ ] 7.1 Create PasswordValidator utility
    - Method to validate password complexity (8-128 chars, uppercase, lowercase, digit, special char)
    - Method to hash passwords using BCrypt with strength 12
    - Method to verify password against hash
    - _Requirements: 1.6_

  - [ ]* 7.2 Write property test for password validation
    - **Property 1: Password Validation Enforcement**
    - **Validates: Requirements 1.6**
    - Generate random strings with varying length and character types
    - Assert validation correctly identifies valid and invalid passwords
    - Use QuickTheories with minimum 100 iterations
    - Tag: `// Feature: smart-campus-management, Property 1: Password Validation Enforcement`

  - [ ] 7.3 Create ValidationUtils class
    - Method to validate email format (exactly one @ with chars before and after)
    - Method to validate date format YYYY-MM-DD
    - Method to validate phone number length (7-20 chars)
    - Method to validate credit hours range (0.5-6.0)
    - Method to trim whitespace from text fields
    - _Requirements: 2.7, 9.6, 14.5, 14.12_

  - [ ]* 7.4 Write property test for input field validation
    - **Property 2: Input Field Validation**
    - **Validates: Requirements 2.2, 2.3, 2.7, 7.4**
    - Generate student records with random field values including constraint violations
    - Assert validation correctly identifies all violations
    - Tag: `// Feature: smart-campus-management, Property 2: Input Field Validation`

  - [ ] 7.5 Create GradeCalculationUtils class
    - Method to calculate total marks from coursework and exam marks
    - Method to cap total marks at 100.0
    - Method to assign letter grade based on total marks
    - Method to get grade points from letter grade
    - Method to round GPA to 2 decimal places using HALF_UP
    - _Requirements: 9.1, 9.3, 9.4, 9.5, 9.12_

  - [ ]* 7.6 Write property tests for grade calculation
    - **Property 3: Marks Validation and Total Calculation**
    - **Property 4: Grade Assignment**
    - **Property 5: Credit Hours Validation**
    - **Validates: Requirements 9.1, 9.2, 9.3, 9.4, 9.5, 9.6**
    - Generate random marks and credit hours including invalid ranges
    - Assert validation, calculation, and grade assignment correctness
    - Tag with respective property numbers

  - [ ] 7.7 Create AttendanceCalculationUtils class
    - Method to calculate attendance percentage: (Present + Excused) / Total × 100
    - Method to round percentage to 2 decimal places
    - Method to determine if low attendance flag should be set (< 75%)
    - _Requirements: 7.5, 7.6_

  - [ ]* 7.8 Write property tests for attendance calculation
    - **Property 8: Attendance Percentage Calculation**
    - **Property 9: Attendance Status Validation**
    - **Validates: Requirements 7.2, 7.5, 7.6**
    - Generate random attendance status lists
    - Assert percentage calculation and status validation correctness
    - Tag with respective property numbers

- [ ] 8. Checkpoint - Verify utility compilation and property tests
  - Ensure all utility classes compile without errors
  - Run property-based tests and verify all pass
  - Check test coverage for utility classes (target 100%)

- [ ] 9. Implement core service interfaces and implementations
  - [ ] 9.1 Create AuthenticationService interface and implementation
    - Method authenticate(LoginRequest): validate credentials, check account status, handle failed attempts
    - Method logout(String username): invalidate session
    - Method validateToken(String token): verify JWT token
    - Method lockAccount(String username): set account locked status
    - Method unlockAccount(String username): clear account locked status
    - Use BCrypt for password verification
    - Use JWT for token generation with HMAC-SHA256
    - Add @Service and @Transactional annotations
    - Inject UserRepository and PasswordEncoder
    - _Requirements: 1.1, 1.2, 1.3, 15.8_

  - [ ]* 9.2 Write unit tests for AuthenticationService
    - Test valid credentials authenticate successfully
    - Test invalid credentials return failure
    - Test account locks after 5 failed attempts
    - Test locked account prevents login
    - Test session creation and expiration

  - [ ] 9.3 Create AuditService interface and implementation
    - Method logAuthentication(String username, String ipAddress, boolean success)
    - Method logAccountModification(Long adminId, Long userId, String action, Map<String, Object> changes)
    - Method logMarksModification(Long lecturerId, Long assessmentId, BigDecimal oldValue, BigDecimal newValue)
    - Method searchAuditLogs(AuditSearchCriteria, Pageable): return paginated logs
    - Method archiveOldLogs(LocalDate beforeDate): archive logs older than date
    - Inject AuditLogRepository
    - Create immutable audit log entries
    - _Requirements: 16.1, 16.2, 16.3, 16.4, 16.7_

  - [ ]* 9.4 Write unit tests for AuditService
    - Test audit log creation for various event types
    - Test audit log search with filters
    - Test pagination limits (1000 per page)
    - Test immutability (no updates allowed)

- [ ] 10. Implement student and user management services
  - [ ] 10.1 Create StudentService interface and implementation
    - Method createStudent(CreateStudentRequest): validate, create student and user account
    - Method updateStudent(Long studentId, UpdateStudentRequest): validate, update student
    - Method getStudent(Long studentId): retrieve student by ID
    - Method searchStudents(StudentSearchCriteria, Pageable): search with filters
    - Method deactivateStudent(Long studentId): set status to inactive
    - Method getEnrolledCourses(Long studentId, Long semesterId): retrieve enrollments
    - Enforce email uniqueness validation
    - Enforce date of birth in past validation
    - Inject StudentRepository, ProgramRepository, ValidationService, AuditService
    - _Requirements: 2.1, 2.2, 2.4, 2.5, 2.9, 2.10_

  - [ ]* 10.2 Write unit tests for StudentService
    - Test student creation with valid data
    - Test duplicate email rejection
    - Test missing required fields rejection
    - Test student search and filtering
    - Test deactivation sets timestamp

  - [ ] 10.3 Create UserAccountService interface and implementation
    - Method createUserAccount(CreateUserRequest): validate username uniqueness, hash password, create account
    - Method resetPassword(Long userId): generate temporary password, set requirePasswordChange flag
    - Method changePassword(Long userId, String newPassword): validate complexity, hash, update
    - Method activateAccount(Long userId): set status to active
    - Method deactivateAccount(Long userId): set status to inactive
    - Method validateUserCredentials(String username, String password): check password against hash
    - Inject UserRepository, PasswordValidator, AuditService
    - _Requirements: 15.1, 15.2, 15.4, 15.6, 15.7, 15.9_

  - [ ]* 10.4 Write unit tests for UserAccountService
    - Test user creation with unique username
    - Test duplicate username rejection
    - Test password reset generates temporary password
    - Test account activation and deactivation
    - Test audit log entries created

- [ ] 11. Implement academic structure management services
  - [ ] 11.1 Create DepartmentService and ProgramService
    - DepartmentService: create, update, delete (with dependency checks), getById, getAll
    - ProgramService: create, update, delete (with dependency checks), getById, getAll, getByDepartment
    - Validate unique codes
    - Prevent deletion with dependent entities
    - Inject respective repositories
    - _Requirements: 3.1, 3.3, 3.9, 3.10_

  - [ ]* 11.2 Write unit tests for DepartmentService and ProgramService
    - Test creation with unique codes
    - Test duplicate code rejection
    - Test deletion prevention with dependents
    - Test referential integrity

  - [ ] 11.3 Create CourseService interface and implementation
    - Method createCourse(CreateCourseRequest): validate credit hours, create course
    - Method updateCourse(Long courseId, UpdateCourseRequest): validate, update course
    - Method getCourse(Long courseId): retrieve course by ID
    - Method getCoursesByProgram(Long programId): retrieve courses for program
    - Method assignLecturer(Long courseId, Long lecturerId, Long semesterId): create assignment
    - Method removeLecturerAssignment(Long assignmentId): validate no dependencies, delete
    - Method canDeleteCourse(Long courseId): check for active enrollments
    - Validate credit hours between 0.5 and 6.0
    - Prevent duplicate lecturer assignments
    - Inject CourseRepository, LecturerAssignmentRepository, EnrollmentRepository
    - _Requirements: 3.4, 5.1, 5.5, 5.7, 3.11_

  - [ ]* 11.4 Write unit tests for CourseService
    - Test course creation with valid credit hours
    - Test credit hours validation (0.5-6.0)
    - Test lecturer assignment creation
    - Test duplicate assignment prevention
    - Test course deletion prevention with enrollments

  - [ ] 11.5 Create SemesterService interface and implementation
    - Method createAcademicYear(CreateAcademicYearRequest): validate date range, create
    - Method createSemester(CreateSemesterRequest): validate dates within academic year, check overlaps, create
    - Method setActiveSemester(Long semesterId): deactivate current, activate new
    - Method getActiveSemester(): retrieve current active semester
    - Validate end date after start date
    - Validate semester dates within academic year
    - Prevent overlapping semesters
    - Inject AcademicYearRepository, SemesterRepository
    - _Requirements: 4.1, 4.2, 4.4, 4.5, 4.6, 4.7, 4.8_

  - [ ]* 11.6 Write unit tests for SemesterService
    - Test academic year creation with valid dates
    - Test date range validation
    - Test semester overlap prevention
    - Test active semester management (only one active)

- [ ] 12. Checkpoint - Verify core services and unit tests
  - Run all unit tests and verify passing
  - Check service layer code coverage (target 80%+)
  - Verify transaction boundaries with @Transactional

- [ ] 13. Implement enrollment and course registration services
  - [ ] 13.1 Create EnrollmentService interface and implementation
    - Method registerForCourse(Long studentId, Long courseId): validate prerequisites, capacity, credit limit, registration period, create enrollment
    - Method dropCourse(Long enrollmentId): validate drop period, update enrollment status
    - Method requestLateDrop(Long enrollmentId): create drop request with pending status
    - Method approveDropRequest(Long requestId, Long adminId): set enrollment to dropped, notify student
    - Method denyDropRequest(Long requestId, Long adminId): restore enrollment status, notify student
    - Method getStudentEnrollments(Long studentId, Long semesterId): retrieve enrollments
    - Method getEnrollmentSummary(Long studentId, Long semesterId): calculate total credits, list courses
    - Validate registration period is active
    - Check prerequisites satisfied
    - Check course capacity not exceeded
    - Check total credit hours not exceeding 24
    - Prevent duplicate registrations
    - Inject EnrollmentRepository, CourseRepository, StudentRepository, SemesterRepository, NotificationService
    - _Requirements: 6.1, 6.2, 6.3, 6.4, 6.5, 6.6, 6.7, 6.8, 6.9, 6.10, 6.11, 6.12, 6.13_

  - [ ]* 13.2 Write property test for credit hours sum validation
    - **Property 10: Credit Hours Sum Validation**
    - **Validates: Requirements 6.7**
    - Generate random lists of course credit hours
    - Assert sum calculation correct and registration rejected if total > 24
    - Tag: `// Feature: smart-campus-management, Property 10: Credit Hours Sum Validation`

  - [ ]* 13.3 Write unit tests for EnrollmentService
    - Test successful course registration
    - Test registration period validation
    - Test prerequisite validation
    - Test capacity enforcement
    - Test credit limit enforcement (complementing property test)
    - Test duplicate registration prevention
    - Test early vs late drop logic
    - Test drop request approval/denial

- [ ] 14. Implement attendance tracking service
  - [ ] 14.1 Create AttendanceService interface and implementation
    - Method markAttendance(MarkAttendanceRequest): create attendance records for all enrolled students, validate no duplicate
    - Method updateAttendanceStatus(Long recordId, AttendanceStatus status): validate 24-hour window, update status
    - Method calculateAttendancePercentage(Long studentId, Long courseId): use (Present + Excused) / Total × 100
    - Method getStudentAttendance(Long studentId, Long courseId): retrieve records
    - Method getCourseAttendanceSummary(Long courseId): aggregate statistics
    - Validate attendance status enum values (Present, Absent, Excused)
    - Prevent duplicate attendance records for same date
    - Enforce 24-hour modification window
    - Set low attendance flag when percentage < 75%
    - Trigger notification when flag is set
    - Inject AttendanceRecordRepository, EnrollmentRepository, NotificationService
    - _Requirements: 7.1, 7.2, 7.3, 7.4, 7.5, 7.6, 7.7, 7.8, 7.9, 7.10, 7.11_

  - [ ]* 14.2 Write unit tests for AttendanceService
    - Test attendance record creation for all enrolled students
    - Test duplicate attendance prevention
    - Test 24-hour modification window enforcement
    - Test low attendance flag setting at 74.9%
    - Test flag removal at 75.0%
    - Test notification triggered for low attendance

- [ ] 15. Implement assessment and grade calculation services
  - [ ] 15.1 Create AssessmentService interface and implementation
    - Method enterCourseworkMarks(Long enrollmentId, BigDecimal marks): validate 0.0-40.0 range, create assessment
    - Method enterExaminationMarks(Long enrollmentId, BigDecimal marks): validate 0.0-60.0 range, create assessment
    - Method updateMarks(Long assessmentId, BigDecimal marks): validate 48-hour window, validate range, update assessment
    - Method getStudentAssessments(Long studentId, Long courseId): retrieve assessments
    - Method getCourseAssessmentSummary(Long courseId): aggregate marks
    - Method canModifyMarks(Long assessmentId): check 48-hour window
    - Validate marks ranges (coursework 0-40, exam 0-60)
    - Validate one decimal place
    - Enforce 48-hour modification deadline
    - Validate student enrollment before marks entry
    - Trigger grade calculation when both marks entered
    - Inject AssessmentRepository, EnrollmentRepository, GradeCalculationService
    - _Requirements: 8.1, 8.2, 8.3, 8.4, 8.5, 8.6, 8.7, 8.8, 8.9, 8.10_

  - [ ]* 15.2 Write unit tests for AssessmentService
    - Test coursework marks validation (0-40)
    - Test examination marks validation (0-60)
    - Test 48-hour modification window
    - Test student enrollment validation
    - Test invalid decimal places rejection

  - [ ] 15.3 Create GradeCalculationService interface and implementation
    - Method calculateCourseGrade(Long enrollmentId): retrieve assessments, calculate total, assign grade, update enrollment
    - Method calculateSemesterGpa(Long studentId, Long semesterId): retrieve enrollments, calculate Σ(points × credits) / Σ(credits)
    - Method calculateCumulativeGpa(Long studentId): retrieve all completed semesters, calculate cumulative GPA
    - Method recalculateGrades(Long studentId): recalculate all grades and GPAs
    - Method getLetterGrade(BigDecimal totalMarks): assign A/B/C/D/F based on ranges
    - Method getGradePoints(LetterGrade grade): return 4.0/3.0/2.0/1.0/0.0
    - Calculate total marks: coursework + examination
    - Cap total marks at 100.0 if exceeded
    - Round GPA to 2 decimal places using HALF_UP
    - Handle zero credit hours (return 0.0)
    - Complete within 5 seconds for recalculation
    - Inject AssessmentRepository, EnrollmentRepository
    - _Requirements: 9.1, 9.2, 9.3, 9.4, 9.5, 9.6, 9.7, 9.8, 9.9, 9.10, 9.11, 9.12, 9.13_

  - [ ]* 15.4 Write property tests for GPA calculation
    - **Property 6: Semester GPA Calculation**
    - **Property 7: Cumulative GPA Calculation**
    - **Validates: Requirements 9.7, 9.8, 9.10, 9.11, 9.12**
    - Generate random lists of grades and credit hours
    - Assert GPA calculations correct with proper rounding
    - Tag with respective property numbers

  - [ ]* 15.5 Write unit tests for GradeCalculationService
    - Test total marks calculation and capping at 100
    - Test grade boundary cases (79.9 → B, 80.0 → A)
    - Test GPA rounding edge cases (3.445 → 3.45, 3.444 → 3.44)
    - Test zero credit hours handling

- [ ] 16. Checkpoint - Verify enrollment, attendance, and assessment services
  - Run all property-based tests and verify passing
  - Run all unit tests and verify passing
  - Check service integration points
  - Verify notification triggers working

- [ ] 17. Implement reporting and transcript services
  - [ ] 17.1 Create ReportService interface and implementation
    - Method generateEnrollmentReport(ReportCriteria): aggregate enrollment counts by program/department/semester
    - Method generatePerformanceReport(ReportCriteria): calculate average GPA by program/semester
    - Method generateLecturerWorkloadReport(Long lecturerId): aggregate course assignments and enrollment counts
    - Method generateAttendanceReport(ReportCriteria): calculate average attendance by course/program
    - Method exportReportToPdf(Long reportId): generate PDF using iText/PDFBox
    - Method exportReportToCsv(Long reportId): generate CSV file
    - Use database aggregation queries for efficiency
    - Implement pagination for large result sets (max 10,000 records)
    - Complete within 10 seconds for datasets up to 10,000 records
    - Inject EnrollmentRepository, AssessmentRepository, AttendanceRecordRepository, LecturerAssignmentRepository
    - _Requirements: 12.1, 12.2, 12.3, 12.4, 12.5, 12.6, 12.7, 12.8, 12.9, 12.10, 12.11_

  - [ ]* 17.2 Write unit tests for ReportService
    - Test enrollment report aggregation
    - Test performance report GPA calculations
    - Test workload report aggregation
    - Test attendance report aggregation
    - Test filtering logic with multiple filters
    - Test empty result handling

  - [ ] 17.3 Create TranscriptService interface and implementation
    - Method generateTranscript(Long studentId): retrieve student info, enrollments, grades, cumulative GPA
    - Method generateOfficialTranscript(Long studentId, Long adminId): add digital signature and seal
    - Method generateTranscriptPdf(Long studentId): create PDF with proper formatting using iText/PDFBox
    - Method verifyTranscript(String transcriptId): validate transcript authenticity
    - Organize courses by academic year and semester in chronological order
    - Include institution name, student info, courses, grades, GPA, generation date
    - Generate unique transcript ID (16 alphanumeric characters)
    - Complete within 10 seconds for students with up to 100 courses
    - Use JOIN FETCH to avoid N+1 query problems
    - Inject StudentRepository, EnrollmentRepository, AssessmentRepository, GradeCalculationService
    - _Requirements: 10.1, 10.2, 10.3, 10.4, 10.5, 10.6, 10.7, 10.8, 10.9, 10.10, 10.11, 10.12_

  - [ ]* 17.4 Write unit tests for TranscriptService
    - Test transcript generation with courses
    - Test empty transcript for students with no courses
    - Test course organization by year and semester
    - Test unique transcript ID generation
    - Test official transcript includes signature

- [ ] 18. Implement notification and system configuration services
  - [ ] 18.1 Create NotificationService interface and implementation
    - Method sendCourseRegistrationConfirmation(Long studentId, Long courseId): create notification within 10 seconds
    - Method sendLowAttendanceWarning(Long studentId, Long courseId, BigDecimal percentage): create notification within 24 hours
    - Method sendMarksPublishedNotification(Long courseId): notify all enrolled students within 10 seconds
    - Method sendLecturerAssignmentNotification(Long lecturerId, Long courseId): create notification within 10 seconds
    - Method getUserNotifications(Long userId, boolean unreadOnly): retrieve notifications
    - Method markAsRead(Long notificationId): update notification status
    - Method markAllAsRead(Long userId): update all user notifications
    - Implement retry logic (3 attempts within 60 seconds)
    - Mark as undeliverable after failed retries
    - Auto-delete notifications older than 90 days
    - Inject NotificationRepository, UserRepository
    - _Requirements: 19.1, 19.2, 19.3, 19.4, 19.5, 19.6, 19.7, 19.8, 19.9_

  - [ ]* 18.2 Write unit tests for NotificationService
    - Test notification creation and delivery
    - Test retry logic on failure (3 attempts)
    - Test undeliverable marking after failed retries
    - Test mark as read functionality

  - [ ] 18.3 Create SystemConfigurationService interface and implementation
    - Method getConfiguration(): retrieve all configuration settings
    - Method updateMaxCreditHours(Integer maxCredits): validate 1-30, update config
    - Method updateMinimumAttendanceThreshold(BigDecimal threshold): validate 0.00-100.00, update config
    - Method updateGradeBoundaries(Map<LetterGrade, BigDecimal>): validate 0.00-100.00, update boundaries
    - Method updateRegistrationDeadline(Integer daysBeforeSemester): validate 1-365, update config
    - Method updateSessionTimeout(Integer minutes): validate 1-1440, update config
    - Apply configuration changes within 5 seconds
    - Cache configuration for performance
    - Log configuration changes via AuditService
    - Inject SystemConfigurationRepository, AuditService
    - _Requirements: 20.1, 20.2, 20.3, 20.4, 20.5, 20.6, 20.7, 20.8, 20.9_

  - [ ]* 18.4 Write unit tests for SystemConfigurationService
    - Test configuration retrieval
    - Test configuration updates with valid values
    - Test validation of configuration ranges
    - Test configuration change application

- [ ] 19. Implement data export and backup services
  - [ ] 19.1 Create DataExportService interface and implementation
    - Method exportData(ExportFormat format): generate CSV or JSON files containing students, courses, attendance, assessments
    - Complete within 300 seconds
    - Inject all relevant repositories
    - _Requirements: 17.1, 17.2, 17.3, 17.4_

  - [ ] 19.2 Create BackupService interface and implementation
    - Method createBackup(): create complete database backup with timestamp filename
    - Method validateBackup(File backupFile): validate backup file integrity
    - Method restoreBackup(File backupFile): restore database from backup file
    - Backup creation within 600 seconds
    - Restoration within 600 seconds
    - Preserve database state if restoration fails
    - Encrypt backup files using configured encryption standard
    - Inject DataSource for database operations
    - _Requirements: 17.5, 17.6, 17.7, 17.8, 17.9, 17.10, 17.11, 17.12, 17.13, 17.14, 17.15_

  - [ ]* 19.3 Write unit tests for BackupService
    - Test backup file creation with timestamp
    - Test backup file validation
    - Test restoration success notification
    - Test restoration failure rollback

- [ ] 20. Checkpoint - Verify all service implementations complete
  - Run all unit tests and verify passing
  - Run all property-based tests and verify passing
  - Check service layer code coverage (target 80%+)
  - Verify all business rules enforced

- [ ] 21. Implement Spring Security configuration
  - [ ] 21.1 Create JWT token provider utility
    - Method generateToken(Authentication): create JWT with user ID, username, role
    - Method validateToken(String token): verify token signature and expiration
    - Method getUsernameFromToken(String token): extract username claim
    - Method getRoleFromToken(String token): extract role claim
    - Use HMAC-SHA256 algorithm
    - Set token expiration to 30 minutes
    - _Requirements: 1.1, 1.7_

  - [ ] 21.2 Create JWT authentication filter
    - Extend OncePerRequestFilter
    - Extract JWT from Authorization header (Bearer token)
    - Validate token using JwtTokenProvider
    - Populate SecurityContext with authentication
    - _Requirements: 1.1, 1.7_

  - [ ] 21.3 Create SecurityConfig class
    - Configure HttpSecurity with JWT filter
    - Define endpoint access rules: /api/auth/** permitAll, all other /api/** authenticated
    - Configure role-based authorization for endpoints
    - Disable CSRF for stateless API
    - Configure CORS if needed
    - Configure password encoder bean (BCrypt strength 12)
    - _Requirements: 1.4, 1.5_

  - [ ] 21.4 Add method-level security annotations
    - Add @PreAuthorize annotations to service methods
    - Administrator: @PreAuthorize("hasRole('ADMINISTRATOR')")
    - Lecturer: @PreAuthorize("hasRole('LECTURER')")
    - Student: @PreAuthorize("hasRole('STUDENT')")
    - Add SecurityContextHolder usage for current user retrieval
    - _Requirements: 1.4_

  - [ ]* 21.5 Write integration tests for security
    - Test authentication success with valid credentials
    - Test authentication failure with invalid credentials
    - Test account lockout after 5 failed attempts
    - Test JWT token validation
    - Test role-based authorization for each endpoint
    - Test unauthorized access returns 403

- [ ] 22. Implement REST API controllers
  - [ ] 22.1 Create AuthController
    - POST /api/v1/auth/login: authenticate user, return JWT token
    - POST /api/v1/auth/logout: invalidate session
    - POST /api/v1/auth/refresh-token: refresh JWT token
    - POST /api/v1/auth/forgot-password: initiate password reset
    - POST /api/v1/auth/reset-password: complete password reset
    - Add @RestController and @RequestMapping annotations
    - Add @Valid on request parameters
    - Return standardized response format with success, data, message, timestamp
    - _Requirements: 1.1, 1.9_

  - [ ] 22.2 Create StudentController
    - POST /api/v1/students [ADMIN]: create student
    - GET /api/v1/students/{id} [ADMIN, LECTURER, STUDENT(self)]: get student
    - PUT /api/v1/students/{id} [ADMIN]: update student
    - PATCH /api/v1/students/{id}/deactivate [ADMIN]: deactivate student
    - GET /api/v1/students [ADMIN, LECTURER]: list students
    - GET /api/v1/students/search [ADMIN, LECTURER]: search students
    - Add role-based authorization
    - Add pagination support for list/search
    - _Requirements: 2.1, 2.4, 2.5, 2.9_

  - [ ] 22.3 Create CourseController
    - POST /api/v1/courses [ADMIN]: create course
    - GET /api/v1/courses/{id} [ALL]: get course
    - PUT /api/v1/courses/{id} [ADMIN]: update course
    - DELETE /api/v1/courses/{id} [ADMIN]: delete course
    - GET /api/v1/courses [ALL]: list courses
    - GET /api/v1/courses/program/{programId} [ALL]: get courses by program
    - POST /api/v1/courses/{id}/assign-lecturer [ADMIN]: assign lecturer
    - DELETE /api/v1/courses/assignments/{id} [ADMIN]: remove lecturer assignment
    - _Requirements: 3.4, 5.1_

  - [ ] 22.4 Create EnrollmentController
    - POST /api/v1/enrollments [STUDENT]: register for course
    - GET /api/v1/enrollments/student/{id} [ADMIN, LECTURER, STUDENT(self)]: get enrollments
    - POST /api/v1/enrollments/{id}/drop [STUDENT]: drop course
    - POST /api/v1/enrollments/{id}/drop-request [STUDENT]: request late drop
    - POST /api/v1/enrollments/drop-requests/{id}/approve [ADMIN]: approve drop
    - POST /api/v1/enrollments/drop-requests/{id}/deny [ADMIN]: deny drop
    - _Requirements: 6.8, 6.10, 6.11, 6.12, 6.13_

  - [ ] 22.5 Create AttendanceController
    - POST /api/v1/attendance/mark [LECTURER]: mark attendance
    - PUT /api/v1/attendance/{id} [LECTURER]: update attendance
    - GET /api/v1/attendance/student/{studentId}/course/{courseId} [LECTURER, STUDENT(self)]: get attendance
    - GET /api/v1/attendance/course/{courseId}/summary [LECTURER]: get course summary
    - GET /api/v1/attendance/student/{studentId}/percentage [LECTURER, STUDENT(self)]: get percentage
    - _Requirements: 7.1, 7.7, 7.8_

  - [ ] 22.6 Create AssessmentController
    - POST /api/v1/assessments/coursework [LECTURER]: enter coursework marks
    - POST /api/v1/assessments/examination [LECTURER]: enter examination marks
    - PUT /api/v1/assessments/{id} [LECTURER]: update marks
    - GET /api/v1/assessments/student/{studentId}/course/{courseId} [LECTURER, STUDENT(self)]: get assessments
    - GET /api/v1/assessments/course/{courseId}/summary [LECTURER]: get course summary
    - _Requirements: 8.1, 8.2, 8.5_

  - [ ] 22.7 Create GradeController
    - GET /api/v1/grades/student/{studentId}/semester/{semesterId} [LECTURER, STUDENT(self)]: get semester grades
    - GET /api/v1/grades/student/{studentId}/cumulative [ADMIN, LECTURER, STUDENT(self)]: get cumulative GPA
    - POST /api/v1/grades/recalculate/{studentId} [ADMIN]: recalculate grades
    - _Requirements: 9.7, 9.10, 9.13_

  - [ ] 22.8 Create ReportController
    - POST /api/v1/reports/enrollment [ADMIN]: generate enrollment report
    - POST /api/v1/reports/performance [ADMIN]: generate performance report
    - POST /api/v1/reports/lecturer-workload [ADMIN]: generate workload report
    - POST /api/v1/reports/attendance [ADMIN]: generate attendance report
    - GET /api/v1/reports/{id}/export/pdf [ADMIN]: export report to PDF
    - GET /api/v1/reports/{id}/export/csv [ADMIN]: export report to CSV
    - _Requirements: 12.1, 12.3, 12.5, 12.6, 12.8, 12.9_

  - [ ] 22.9 Create TranscriptController
    - GET /api/v1/transcripts/student/{studentId} [ADMIN, STUDENT(self)]: get transcript
    - GET /api/v1/transcripts/student/{studentId}/pdf [ADMIN, STUDENT(self)]: download PDF
    - POST /api/v1/transcripts/official/{studentId} [ADMIN]: generate official transcript
    - GET /api/v1/transcripts/verify/{transcriptId} [PUBLIC]: verify transcript
    - _Requirements: 10.1, 10.8, 10.10_

  - [ ] 22.10 Create NotificationController
    - GET /api/v1/notifications [ALL]: get notifications
    - GET /api/v1/notifications/unread [ALL]: get unread notifications
    - PUT /api/v1/notifications/{id}/read [ALL]: mark as read
    - PUT /api/v1/notifications/read-all [ALL]: mark all as read
    - _Requirements: 19.5, 19.6_

  - [ ] 22.11 Create DashboardController
    - GET /api/v1/dashboard/admin [ADMIN]: get admin dashboard data
    - GET /api/v1/dashboard/lecturer [LECTURER]: get lecturer dashboard data
    - GET /api/v1/dashboard/student [STUDENT]: get student dashboard data
    - _Requirements: 13.1, 13.2, 13.3, 13.6_

  - [ ] 22.12 Create UserController
    - POST /api/v1/users [ADMIN]: create user account
    - GET /api/v1/users/{id} [ADMIN]: get user
    - PUT /api/v1/users/{id} [ADMIN]: update user
    - DELETE /api/v1/users/{id} [ADMIN]: delete user
    - POST /api/v1/users/{id}/reset-password [ADMIN]: reset password
    - PATCH /api/v1/users/{id}/activate [ADMIN]: activate account
    - PATCH /api/v1/users/{id}/deactivate [ADMIN]: deactivate account
    - _Requirements: 15.1, 15.4, 15.6, 15.7_

  - [ ] 22.13 Create AuditLogController
    - GET /api/v1/audit-logs [ADMIN]: get audit logs
    - GET /api/v1/audit-logs/search [ADMIN]: search audit logs with filters
    - POST /api/v1/audit-logs/archive [ADMIN]: archive old logs
    - _Requirements: 16.7_

  - [ ] 22.14 Create DataExportController
    - POST /api/v1/export/data [ADMIN]: export data
    - POST /api/v1/backup/create [ADMIN]: create backup
    - POST /api/v1/backup/restore [ADMIN]: restore backup
    - GET /api/v1/backup/list [ADMIN]: list backups
    - _Requirements: 17.1, 17.5, 17.8_

  - [ ] 22.15 Create SystemConfigurationController
    - GET /api/v1/config [ADMIN]: get configuration
    - PUT /api/v1/config [ADMIN]: update configuration
    - _Requirements: 20.8, 20.9_

  - [ ] 22.16 Create DepartmentController and ProgramController
    - DepartmentController: CRUD operations for departments [ADMIN]
    - ProgramController: CRUD operations for programs [ADMIN]
    - _Requirements: 3.1, 3.3_

  - [ ] 22.17 Create AcademicCalendarController
    - POST /api/v1/academic-years [ADMIN]: create academic year
    - POST /api/v1/semesters [ADMIN]: create semester
    - PATCH /api/v1/semesters/{id}/activate [ADMIN]: set active semester
    - GET /api/v1/semesters/active [ALL]: get active semester
    - _Requirements: 4.1, 4.3, 4.7_

- [ ] 23. Implement global exception handler
  - Create @ControllerAdvice class
  - Handle EntityNotFoundException (404)
  - Handle DuplicateEntityException (409)
  - Handle ValidationException (400)
  - Handle MethodArgumentNotValidException (400)
  - Handle AuthorizationException (403)
  - Handle generic Exception (500)
  - Return standardized error response with success=false, error code, message, details, timestamp
  - Log exceptions with appropriate severity
  - _Requirements: 14.2, 14.13_

- [ ] 24. Checkpoint - Verify API layer complete
  - Test each controller endpoint manually or with Postman
  - Verify request/response formats
  - Verify error handling returns proper status codes
  - Verify role-based authorization working

- [ ] 25. Implement frontend UI pages
  - [ ] 25.1 Create login page
    - Username and password input fields
    - Login button that calls /api/v1/auth/login
    - Display error messages for failed login
    - Store JWT token in session storage on success
    - Redirect to role-specific dashboard
    - _Requirements: 1.1, 1.2_

  - [ ] 25.2 Create admin dashboard page
    - Display total students, lecturers, active courses
    - Display recent activities (last 30 days)
    - Quick action buttons: "Add Student", "Generate Report"
    - Load data from /api/v1/dashboard/admin
    - _Requirements: 13.1, 13.5_

  - [ ] 25.3 Create lecturer dashboard page
    - Display assigned courses for current semester
    - Display upcoming classes (next 7 days)
    - Display courses with pending marks
    - Display recent attendance submissions (last 14 days)
    - Quick action buttons: "Mark Attendance", "Enter Marks"
    - Load data from /api/v1/dashboard/lecturer
    - _Requirements: 13.2, 13.5_

  - [ ] 25.4 Create student dashboard page
    - Display enrolled courses for current semester
    - Display current semester GPA (rounded to 2 decimals)
    - Display attendance summary with percentage per course
    - Display upcoming assessments (next 14 days)
    - Quick action buttons: "Register Courses", "View Transcript"
    - Load data from /api/v1/dashboard/student
    - _Requirements: 13.3, 13.5_

  - [ ] 25.5 Create student management pages (Admin)
    - Student list page with search and pagination
    - Create student form with validation
    - Edit student form
    - View student details page
    - Deactivate student button
    - Call respective /api/v1/students endpoints
    - _Requirements: 2.1, 2.4, 2.5, 2.9_

  - [ ] 25.6 Create course management pages (Admin)
    - Course list page with filters by program
    - Create course form with credit hours validation
    - Edit course form
    - Lecturer assignment form
    - Call respective /api/v1/courses endpoints
    - _Requirements: 3.4, 5.1_

  - [ ] 25.7 Create course registration page (Student)
    - Display available courses filtered by student's program
    - Show course details (name, code, credit hours, prerequisites)
    - Register button that calls /api/v1/enrollments
    - Display error messages for validation failures
    - Display current enrolled courses with drop button
    - _Requirements: 6.1, 6.8, 6.10_

  - [ ] 25.8 Create attendance marking page (Lecturer)
    - Select course and date
    - Display list of enrolled students
    - Mark attendance status (Present/Absent/Excused) for each student
    - Submit button that calls /api/v1/attendance/mark
    - Display success/error messages
    - _Requirements: 7.1, 7.2_

  - [ ] 25.9 Create marks entry page (Lecturer)
    - Select course
    - Display list of enrolled students
    - Input fields for coursework (0-40) and examination (0-60) marks
    - Submit button that calls /api/v1/assessments/coursework and /api/v1/assessments/examination
    - Display validation errors
    - Display grades after marks entered
    - _Requirements: 8.1, 8.2, 11.1_

  - [ ] 25.10 Create performance monitoring page (Lecturer)
    - Display enrolled students with overall marks and grades
    - Display class average, highest, lowest marks
    - Highlight at-risk students (marks < 50)
    - Filter by performance level
    - Display performance trends
    - Export button that calls /api/v1/reports/performance CSV export
    - _Requirements: 11.1, 11.2, 11.3, 11.4, 11.5, 11.6, 11.7, 11.8_

  - [ ] 25.11 Create transcript viewing page (Student)
    - Display student information
    - Display courses organized by year and semester
    - Display grades and credit hours
    - Display cumulative GPA
    - Download PDF button that calls /api/v1/transcripts/student/{id}/pdf
    - _Requirements: 10.1, 10.3, 10.5, 10.6, 10.8_

  - [ ] 25.12 Create report generation pages (Admin)
    - Enrollment report form with filters
    - Performance report form with filters
    - Lecturer workload report form
    - Attendance report form with filters
    - Export buttons for PDF and CSV
    - Display report results in tables
    - Call respective /api/v1/reports endpoints
    - _Requirements: 12.1, 12.3, 12.5, 12.6, 12.7, 12.8, 12.9_

  - [ ] 25.13 Create user account management pages (Admin)
    - User list page
    - Create user form with role selection
    - Reset password button
    - Activate/deactivate account buttons
    - Call respective /api/v1/users endpoints
    - _Requirements: 15.1, 15.4, 15.6, 15.7_

  - [ ] 25.14 Create audit log viewing page (Admin)
    - Display audit logs with filters (user, action type, date range)
    - Pagination support
    - Display log details (user, action, timestamp, changes)
    - Call /api/v1/audit-logs/search endpoint
    - _Requirements: 16.7_

  - [ ] 25.15 Create system configuration page (Admin)
    - Display current configuration settings
    - Edit forms for each setting with validation
    - Save button that calls /api/v1/config PUT endpoint
    - Display success/error messages
    - _Requirements: 20.8, 20.9_

  - [ ] 25.16 Create notification panel component
    - Display unread notifications with badge count
    - Mark as read functionality
    - Mark all as read button
    - Call respective /api/v1/notifications endpoints
    - Include in all dashboard pages
    - _Requirements: 19.5, 19.6_

  - [ ] 25.17 Create search functionality component
    - Search input with 1-100 character validation
    - Search results with pagination (25 per page)
    - Highlight search terms in results
    - Display total count
    - Clear filters button
    - Reusable across student, course, and other search pages
    - _Requirements: 18.1, 18.2, 18.7, 18.8, 18.9, 18.11, 18.12, 18.13, 18.15, 18.16_

  - [ ] 25.18 Add responsive design with Bootstrap
    - Apply Bootstrap grid system
    - Add Bootstrap components (forms, buttons, tables, cards, alerts)
    - Ensure mobile responsiveness
    - Add consistent navigation bar with role-based menu items
    - Add footer with system information
    - _Requirements: All UI-related_

- [ ] 26. Checkpoint - Verify frontend UI complete
  - Test all pages manually
  - Verify form validations working
  - Verify API integration working
  - Test responsive design on different screen sizes

- [ ] 27. Write integration tests
  - [ ] 27.1 Set up integration test configuration
    - Create test application.properties with H2 database
    - Create @SpringBootTest test base class
    - Set up test data with @Sql scripts
    - Configure MockMvc for controller testing
    - _Requirements: All (testing foundation)_

  - [ ]* 27.2 Write authentication integration tests
    - Test login flow with valid credentials
    - Test login flow with invalid credentials
    - Test account lockout after 5 failed attempts
    - Test JWT token validation
    - Test session timeout
    - _Requirements: 1.1, 1.2, 1.3, 1.7_

  - [ ]* 27.3 Write student registration integration tests
    - Test student creation with valid data
    - Test duplicate email rejection
    - Test referential integrity with program
    - Test student search with filters
    - Test deactivation
    - _Requirements: 2.1, 2.2, 2.5, 2.9, 2.10_

  - [ ]* 27.4 Write course enrollment integration tests
    - Test student course registration
    - Test prerequisite validation
    - Test capacity enforcement
    - Test credit limit enforcement
    - Test duplicate registration prevention
    - Test drop requests
    - _Requirements: 6.1, 6.3, 6.4, 6.5, 6.6, 6.7, 6.11_

  - [ ]* 27.5 Write attendance tracking integration tests
    - Test attendance marking for all enrolled students
    - Test duplicate attendance prevention
    - Test attendance percentage calculation
    - Test low attendance flag setting
    - Test notification triggered
    - _Requirements: 7.1, 7.3, 7.5, 7.10_

  - [ ]* 27.6 Write assessment and grade integration tests
    - Test marks entry with validation
    - Test grade calculation cascade
    - Test semester GPA calculation
    - Test cumulative GPA calculation
    - Test grade recalculation
    - _Requirements: 8.1, 9.1, 9.7, 9.10, 9.13_

  - [ ]* 27.7 Write transcript generation integration tests
    - Test transcript generation with courses
    - Test empty transcript
    - Test course organization by year/semester
    - Test PDF generation
    - Test official transcript with signature
    - _Requirements: 10.1, 10.2, 10.3, 10.8, 10.10_

  - [ ]* 27.8 Write report generation integration tests
    - Test enrollment report with filters
    - Test performance report with GPA calculations
    - Test lecturer workload report
    - Test attendance report
    - Test PDF and CSV export
    - _Requirements: 12.1, 12.3, 12.5, 12.6, 12.8, 12.9_

  - [ ]* 27.9 Write authorization integration tests
    - Test admin can access all endpoints
    - Test lecturer can access teaching endpoints
    - Test student can access own data only
    - Test unauthorized access returns 403
    - Test cross-user access prevention (student accessing another student's data)
    - _Requirements: 1.4, 1.5_

  - [ ]* 27.10 Write audit logging integration tests
    - Test audit log creation for authentication
    - Test audit log creation for account modifications
    - Test audit log creation for marks modifications
    - Test audit log immutability
    - Test audit log search with filters
    - _Requirements: 16.1, 16.2, 16.3, 16.7, 16.10_

  - [ ]* 27.11 Write notification integration tests
    - Test registration confirmation notification
    - Test low attendance warning notification
    - Test marks published notification
    - Test lecturer assignment notification
    - Test notification delivery and retry logic
    - _Requirements: 19.1, 19.2, 19.3, 19.4, 19.8_

  - [ ]* 27.12 Write validation integration tests
    - Test required field validation
    - Test referential integrity enforcement
    - Test date format validation
    - Test date range validation
    - Test numeric field validation
    - _Requirements: 14.1, 14.2, 14.3, 14.5, 14.6, 14.7, 14.8, 14.9, 14.10, 14.11_

- [ ] 28. Checkpoint - Verify all integration tests passing
  - Run all integration tests
  - Check integration test coverage
  - Verify database transactions working correctly
  - Fix any failing tests

- [ ] 29. Implement performance optimizations
  - Add database indexes on frequently queried columns
  - Configure second-level cache with Ehcache for Department, Program entities
  - Add @Cacheable annotations on SystemConfigurationService methods
  - Configure connection pooling with HikariCP
  - Add pagination to all list endpoints
  - Optimize queries with JOIN FETCH to prevent N+1 problems
  - Add query result caching for reports
  - _Requirements: 11.11, 12.11, 10.12_

- [ ] 30. Implement monitoring and health checks
  - Configure Spring Boot Actuator
  - Enable health endpoint /actuator/health
  - Enable metrics endpoint /actuator/metrics
  - Add custom health indicators for database and external services
  - Configure logging levels for production
  - Set up structured logging with JSON format for audit logs
  - _Requirements: All (operational)_

- [ ] 31. Write API documentation
  - Add Springdoc OpenAPI dependency
  - Add @Operation annotations to controller methods
  - Add @Schema annotations to DTOs
  - Add API examples for request/response
  - Configure Swagger UI at /swagger-ui.html
  - Document authentication requirements
  - Document error response formats
  - _Requirements: All API endpoints_

- [ ] 32. Create database migration scripts
  - Set up Flyway or Liquibase for database migrations
  - Create V1__initial_schema.sql with all table definitions
  - Create V2__add_indexes.sql with performance indexes
  - Create V3__seed_data.sql with initial configuration data
  - Test migration on clean database
  - Test migration rollback
  - _Requirements: All (database schema)_

- [ ] 33. Create deployment documentation
  - Write README.md with project overview
  - Document prerequisites (Java 17+, Maven, MySQL)
  - Document build instructions (mvn clean install)
  - Document run instructions (java -jar or mvn spring-boot:run)
  - Document configuration (application.properties)
  - Document database setup instructions
  - Document default admin account creation
  - Create DEPLOYMENT.md with production deployment steps
  - Document environment variables
  - Document backup and restore procedures
  - _Requirements: All (deployment)_

- [ ] 34. Create user documentation
  - Write USER_GUIDE.md with role-specific instructions
  - Document administrator workflows (student management, report generation)
  - Document lecturer workflows (attendance marking, marks entry)
  - Document student workflows (course registration, transcript viewing)
  - Add screenshots for key pages
  - Document common error messages and resolutions
  - _Requirements: All (user guidance)_

- [ ] 35. Final system testing and verification
  - Run complete end-to-end test scenarios for all three roles
  - Verify all 20 requirements satisfied
  - Verify all 10 correctness properties validated by tests
  - Run full test suite (unit + property + integration)
  - Verify code coverage meets 80%+ target
  - Perform security scan with OWASP Dependency Check
  - Test with production-like data volumes
  - Test concurrent user scenarios
  - Verify performance benchmarks met
  - _Requirements: All_

## Notes

- Tasks marked with `*` are optional test tasks and can be skipped for faster MVP delivery
- Each implementation task references specific requirements for traceability
- Property-based tests validate universal correctness properties from the design document
- Unit tests validate specific examples and edge cases
- Integration tests validate end-to-end workflows and system integration
- Checkpoints ensure incremental validation and early error detection
- The task list follows dependency order: infrastructure → data → business → API → UI → testing

## Task Dependency Graph

```json
{
  "waves": [
    { "id": 0, "tasks": ["1", "2"] },
    { "id": 1, "tasks": ["3.1", "3.2", "3.3", "3.4", "3.5", "3.6", "3.7", "3.8"] },
    { "id": 2, "tasks": ["5.1", "5.2", "6"] },
    { "id": 3, "tasks": ["5.3", "5.4", "5.5", "5.6", "5.7", "5.8", "5.9", "5.10", "7.1", "7.3", "7.5", "7.7"] },
    { "id": 4, "tasks": ["7.2", "7.4", "7.6", "7.8"] },
    { "id": 5, "tasks": ["9.1", "9.3"] },
    { "id": 6, "tasks": ["9.2", "9.4", "10.1", "10.3"] },
    { "id": 7, "tasks": ["10.2", "10.4", "11.1", "11.3", "11.5"] },
    { "id": 8, "tasks": ["11.2", "11.4", "11.6", "13.1"] },
    { "id": 9, "tasks": ["13.2", "13.3", "14.1", "15.1", "15.3"] },
    { "id": 10, "tasks": ["14.2", "15.2", "15.4", "15.5"] },
    { "id": 11, "tasks": ["17.1", "17.3", "18.1", "18.3", "19.1", "19.2"] },
    { "id": 12, "tasks": ["17.2", "17.4", "18.2", "18.4", "19.3"] },
    { "id": 13, "tasks": ["21.1", "21.2", "21.3", "21.4"] },
    { "id": 14, "tasks": ["21.5", "22.1", "22.2", "22.3", "22.4", "22.5", "22.6", "22.7", "22.8", "22.9", "22.10", "22.11", "22.12", "22.13", "22.14", "22.15", "22.16", "22.17", "23"] },
    { "id": 15, "tasks": ["25.1", "25.2", "25.3", "25.4", "25.5", "25.6", "25.7", "25.8", "25.9", "25.10", "25.11", "25.12", "25.13", "25.14", "25.15", "25.16", "25.17"] },
    { "id": 16, "tasks": ["25.18", "27.1"] },
    { "id": 17, "tasks": ["27.2", "27.3", "27.4", "27.5", "27.6", "27.7", "27.8", "27.9", "27.10", "27.11", "27.12"] },
    { "id": 18, "tasks": ["29", "30", "31", "32"] },
    { "id": 19, "tasks": ["33", "34"] },
    { "id": 20, "tasks": ["35"] }
  ]
}
```
