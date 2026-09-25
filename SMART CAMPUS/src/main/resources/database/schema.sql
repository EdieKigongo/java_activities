-- ====================================================================
-- Smart Campus Student Management and Academic Information System
-- Database Schema (DDL) - SQLite & MySQL Compatible
-- ====================================================================

-- 1. Users Table (Core Authentication with Role-Based Access)
CREATE TABLE IF NOT EXISTS users (
    id VARCHAR(50) PRIMARY KEY,
    username VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    role VARCHAR(20) NOT NULL CHECK (role IN ('ADMIN', 'LECTURER', 'STUDENT')),
    full_name VARCHAR(150) NOT NULL,
    phone VARCHAR(30),
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. Faculties Table (8 Core Faculties)
CREATE TABLE IF NOT EXISTS faculties (
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    code VARCHAR(20) UNIQUE NOT NULL,
    dean VARCHAR(150)
);

-- 3. Departments Table
CREATE TABLE IF NOT EXISTS departments (
    id VARCHAR(50) PRIMARY KEY,
    faculty_id VARCHAR(50) NOT NULL,
    name VARCHAR(200) NOT NULL,
    code VARCHAR(20) NOT NULL,
    head_of_department VARCHAR(150),
    FOREIGN KEY (faculty_id) REFERENCES faculties(id) ON DELETE CASCADE
);

-- 4. Courses Table (Undergraduate Degrees & Diplomas across faculties)
CREATE TABLE IF NOT EXISTS courses (
    id VARCHAR(50) PRIMARY KEY,
    faculty_id VARCHAR(50) NOT NULL,
    department_id VARCHAR(50),
    name VARCHAR(200) NOT NULL,
    code VARCHAR(30) UNIQUE NOT NULL,
    award_type VARCHAR(30) NOT NULL, -- Bachelor, Diploma, Master
    duration_years INT NOT NULL DEFAULT 3,
    total_credits INT NOT NULL DEFAULT 140,
    FOREIGN KEY (faculty_id) REFERENCES faculties(id) ON DELETE CASCADE
);

-- 5. Lecturers Table
CREATE TABLE IF NOT EXISTS lecturers (
    id VARCHAR(50) PRIMARY KEY,
    user_id VARCHAR(50) UNIQUE NOT NULL,
    staff_id VARCHAR(50) UNIQUE NOT NULL,
    faculty_id VARCHAR(50) NOT NULL,
    department VARCHAR(150) NOT NULL,
    title VARCHAR(30) DEFAULT 'Dr.',
    specialization VARCHAR(200),
    qualification VARCHAR(200),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (faculty_id) REFERENCES faculties(id)
);

-- 6. Course Units Table (Modules taught per semester)
CREATE TABLE IF NOT EXISTS course_units (
    id VARCHAR(50) PRIMARY KEY,
    course_id VARCHAR(50) NOT NULL,
    faculty_id VARCHAR(50) NOT NULL,
    code VARCHAR(30) NOT NULL,
    title VARCHAR(200) NOT NULL,
    credit_units INT NOT NULL DEFAULT 4,
    semester INT NOT NULL DEFAULT 1,
    year_of_study INT NOT NULL DEFAULT 1,
    lecturer_id VARCHAR(50),
    FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE,
    FOREIGN KEY (faculty_id) REFERENCES faculties(id),
    FOREIGN KEY (lecturer_id) REFERENCES lecturers(id)
);

-- 7. Students Table (500 Enrolled Students)
CREATE TABLE IF NOT EXISTS students (
    id VARCHAR(50) PRIMARY KEY,
    user_id VARCHAR(50) UNIQUE NOT NULL,
    reg_number VARCHAR(50) UNIQUE NOT NULL,
    faculty_id VARCHAR(50) NOT NULL,
    course_id VARCHAR(50) NOT NULL,
    year_of_study INT NOT NULL DEFAULT 1,
    current_semester INT NOT NULL DEFAULT 1,
    academic_year VARCHAR(20) DEFAULT '2024/2025',
    gender VARCHAR(10) CHECK (gender IN ('MALE', 'FEMALE')),
    date_of_birth DATE,
    cgpa DECIMAL(3,2) DEFAULT 0.00,
    attendance_rate DECIMAL(5,2) DEFAULT 100.00,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (faculty_id) REFERENCES faculties(id),
    FOREIGN KEY (course_id) REFERENCES courses(id)
);

-- 8. Enrollments & Grade Records (Coursework /40, Exam /60, Grade, GP)
CREATE TABLE IF NOT EXISTS enrollments (
    id VARCHAR(50) PRIMARY KEY,
    student_id VARCHAR(50) NOT NULL,
    course_unit_id VARCHAR(50) NOT NULL,
    academic_year VARCHAR(20) NOT NULL,
    semester INT NOT NULL,
    coursework_marks DECIMAL(5,2) DEFAULT 0.00,
    exam_marks DECIMAL(5,2) DEFAULT 0.00,
    total_marks DECIMAL(5,2) DEFAULT 0.00,
    grade VARCHAR(5),
    grade_point DECIMAL(3,2),
    remarks VARCHAR(50),
    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,
    FOREIGN KEY (course_unit_id) REFERENCES course_units(id) ON DELETE CASCADE
);

-- 9. Attendance Table (Tracking per lecture session)
CREATE TABLE IF NOT EXISTS attendance (
    id VARCHAR(50) PRIMARY KEY,
    course_unit_id VARCHAR(50) NOT NULL,
    student_id VARCHAR(50) NOT NULL,
    date DATE NOT NULL,
    session_topic VARCHAR(200),
    status VARCHAR(20) NOT NULL CHECK (status IN ('PRESENT', 'ABSENT', 'LATE', 'EXCUSED')),
    lecturer_id VARCHAR(50) NOT NULL,
    FOREIGN KEY (course_unit_id) REFERENCES course_units(id) ON DELETE CASCADE,
    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,
    FOREIGN KEY (lecturer_id) REFERENCES lecturers(id)
);

-- Indexes for lightning fast searching and filtering
CREATE INDEX IF NOT EXISTS idx_students_reg ON students(reg_number);
CREATE INDEX IF NOT EXISTS idx_students_faculty ON students(faculty_id);
CREATE INDEX IF NOT EXISTS idx_enrollments_student ON enrollments(student_id);
CREATE INDEX IF NOT EXISTS idx_attendance_unit ON attendance(course_unit_id);
