/* Departments table creation */

CREATE TABLE Departments(
    DepartmentID NUMBER(2) NOT NULL,
    DepartmentName VARCHAR2(100),

    CONSTRAINT departments_pk PRIMARY KEY(DepartmentID)
);

CREATE SEQUENCE departments_sequence;

CREATE OR REPLACE TRIGGER departments_on_insert
    BEFORE INSERT ON Departments
    FOR EACH ROW
BEGIN
    SELECT departments_sequence.NEXTVAL INTO :NEW.DepartmentID
    FROM DUAL;
END;

------------------------------------------------------------------------------------------------------------------------------------------------

/* Students table creation */

CREATE TABLE Students(
    StudentID NUMBER(5) NOT NULL,
    FullName VARCHAR2(30),
    Email VARCHAR2(50) UNIQUE,
    Password VARCHAR2(20),
    Gender VARCHAR2(10),
    DoB DATE,
    PersonalPhoto VARCHAR2(150),
    ContactNumber VARCHAR2(15),
    CGPA NUMBER(3, 2),
    DepartmentID NUMBER(2) NOT NULL,

    CONSTRAINT students_pk PRIMARY KEY(StudentID),
    CONSTRAINT students_dept_fk FOREIGN KEY(DepartmentID) REFERENCES Departments(DepartmentID),
    CONSTRAINT check_gender CHECK(Gender IN ('Female', 'Male'))
);

CREATE SEQUENCE students_sequence;

CREATE OR REPLACE TRIGGER students_on_insert
    BEFORE INSERT ON Students
    FOR EACH ROW
BEGIN
    SELECT students_sequence.NEXTVAL INTO :NEW.StudentID
    FROM DUAL;
END;

------------------------------------------------------------------------------------------------------------------------------------------------

/* Lecturers table creation */

CREATE TABLE Lecturers(
    LecturerID NUMBER(5) NOT NULL,
    FullName VARCHAR2(30),
    Salary NUMBER(6),
    ContactEmail VARCHAR2(50),
    OfficeRoom VARCHAR2(5),
    DepartmentID NUMBER(2) NOT NULL,

    CONSTRAINT lecturers_pk PRIMARY KEY(LecturerID),
    CONSTRAINT lecturers_dept_fk FOREIGN KEY(DepartmentID) REFERENCES Departments(DepartmentID)
);

CREATE SEQUENCE lecturers_sequence;

CREATE OR REPLACE TRIGGER lecturers_on_insert
    BEFORE INSERT ON Lecturers
    FOR EACH ROW
BEGIN
    SELECT lecturers_sequence.NEXTVAL INTO :NEW.LecturerID
    FROM DUAL;
END;

------------------------------------------------------------------------------------------------------------------------------------------------

/* Courses table creation */

CREATE TABLE Courses(
    CourseID NUMBER(5) NOT NULL,
    Title VARCHAR2(100),
    CreditHours NUMBER(3),
    DepartmentID NUMBER(2) NOT NULL,
    LecturerID NUMBER(5) NOT NULL,

    CONSTRAINT courses_pk PRIMARY KEY(CourseID),
    CONSTRAINT courses_dept_fk FOREIGN KEY(DepartmentID) REFERENCES Departments(DepartmentID),
    CONSTRAINT courses_lect_fk FOREIGN KEY(LecturerID) REFERENCES Lecturers(LecturerID)  
);

CREATE SEQUENCE courses_sequence;

CREATE OR REPLACE TRIGGER courses_on_insert
    BEFORE INSERT ON Courses
    FOR EACH ROW
BEGIN
    SELECT courses_sequence.NEXTVAL INTO :NEW.CourseID
    FROM DUAL;
END;

------------------------------------------------------------------------------------------------------------------------------------------------

/* Grades table creation */

CREATE TABLE Grades(
    Grade VARCHAR2(2) NOT NULL,
    GPA NUMBER(2, 1),

    CONSTRAINT grades_pk PRIMARY KEY(Grade)
);

------------------------------------------------------------------------------------------------------------------------------------------------

/* Enrollments table creation */

CREATE TABLE Enrollments(
    EnrollmentID NUMBER(5) NOT NULL,
    StudentID NUMBER(5) NOT NULL,
    CourseID NUMBER(5) NOT NULL,
    Term VARCHAR2(10),
    Year DATE,
    State VARCHAR2(20),
    Grade VARCHAR2(2) NOT NULL,

    CONSTRAINT enrollments_pk PRIMARY KEY(EnrollmentID),
    CONSTRAINT enrollments_student_fk FOREIGN KEY(StudentID) REFERENCES Students(StudentID),
    CONSTRAINT enrollments_course_fk FOREIGN KEY(CourseID) REFERENCES Courses(CourseID),
    CONSTRAINT enrollments_grade_fk FOREIGN KEY(Grade) REFERENCES Grades(Grade),
    CONSTRAINT check_term CHECK(Term IN ('Spring', 'Autumn', 'Summer')),
    CONSTRAINT check_state CHECK(State IN ('In progress', 'Passed'))
);

CREATE SEQUENCE enrollments_sequence;

CREATE OR REPLACE TRIGGER enrollments_on_insert
    BEFORE INSERT ON Enrollments
    FOR EACH ROW
BEGIN
    SELECT enrollments_sequence.NEXTVAL INTO :NEW.EnrollmentID
    FROM DUAL;
END;

------------------------------------------------------------------------------------------------------------------------------------------------