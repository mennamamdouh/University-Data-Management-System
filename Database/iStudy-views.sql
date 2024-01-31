-- View to show information about students
CREATE OR REPLACE VIEW studentds_info AS
    SELECT S.PersonalPhoto, S.FullName, D.DepartmentName, S.CGPA, S.TotalCreditHours
    FROM Students S
    INNER JOIN Departments D
        ON S.DepartmentID = D.DepartmentID
    ORDER BY CGPA, S.FullName;

-- View to show information about courses, their departments, and number of students enrolled in each course
CREATE OR REPLACE VIEW courses_info AS
    SELECT C.Title, C.CreditHours, D.DepartmentName, L.FullName, COUNT(DISTINCT E.StudentID) AS numberOfStudents
    FROM Courses C
    INNER JOIN Departments D
        ON C.DepartmentID = D.DepartmentID
    INNER JOIN Lecturers L
        ON C.LecturerID = L.LecturerID
    INNER JOIN Enrollments E
        ON C.CourseID = E.CourseID
    GROUP BY C.Title, C.CreditHours, D.DepartmentName, L.FullName;

-- View to show the number of students enrolled in each department
CREATE OR REPLACE VIEW students_in_departments AS
    SELECT D.DepartmentName, COUNT(S.StudentID) AS numberOfStudents
    FROM Departments D
    INNER JOIN Students S
        ON D.DepartmentID = S.DepartmentID
    GROUP BY S.DepartmentID, D.DepartmentName;

-- View to show the number of courses related to each department
CREATE OR REPLACE VIEW courses_in_departments AS
    SELECT D.DepartmentName, COUNT(C.CourseID) AS numberOfCourses
    FROM Departments D
    INNER JOIN Courses C
        ON D.DepartmentID = C.DepartmentID
    GROUP BY C.DepartmentID, D.DepartmentName;

-- View to show the number of lecturers work in each department
CREATE OR REPLACE VIEW lecturers_in_departments AS
    SELECT D.DepartmentName, COUNT(L.LecturerID) AS numberOfLecturers
    FROM Departments D
    INNER JOIN Lecturers L
        ON D.DepartmentID = L.DepartmentID
    GROUP BY L.DepartmentID, D.DepartmentName;

-- View to show number of students enrolled on a course in each semester for each year
CREATE OR REPLACE VIEW courses_per_semester AS
    SELECT Term, Year, COUNT(StudentID) AS numberOfStudents
    FROM Enrollments
    GROUP BY Term, Year;