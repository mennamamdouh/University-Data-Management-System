-- View to show information about students
CREATE OR REPLACE VIEW studentds_info AS
    SELECT S.StudentID, S.PersonalPhoto, S.FullName, D.DepartmentName, S.CGPA, S.TotalCreditHours
    FROM Students S
    INNER JOIN Departments D
        ON S.DepartmentID = D.DepartmentID
    ORDER BY CGPA, S.FullName;

-- View to show information about courses, their departments, and number of students enrolled in each course
CREATE OR REPLACE VIEW courses_info AS
    SELECT C.CourseID, C.Title, C.CreditHours, D.DepartmentName, L.LecturerID, L.FullName, COUNT(DISTINCT E.StudentID) AS numberOfStudents
    FROM Courses C
    INNER JOIN Departments D
        ON C.DepartmentID = D.DepartmentID
    INNER JOIN Lecturers L
        ON C.LecturerID = L.LecturerID
    LEFT JOIN Enrollments E
        ON C.CourseID = E.CourseID
    GROUP BY C.CourseID, C.Title, C.CreditHours, D.DepartmentName, L.LecturerID, L.FullName;

-- View to show information about lecturers, their departments, and number of courses they teach
CREATE OR REPLACE VIEW lects_info AS
    SELECT L.LecturerID, L.FullName, L.Salary, L.ContactEmail, L.OfficeRoom, D.DepartmentName, COUNT(C.CourseID) AS numberOfCourses
    FROM Lecturers L
    INNER JOIN Departments D
        ON L.DepartmentID = D.DepartmentID
    LEFT JOIN Courses C
        ON L.LecturerID = C.LecturerID
    GROUP BY L.LecturerID, L.FullName, L.Salary, L.ContactEmail, L.OfficeRoom, D.DepartmentName
    ORDER BY L.FullName;

-- View to show some information about each department
CREATE OR REPLACE VIEW departments_info AS
    SELECT D.DepartmentID, D.DepartmentName, COUNT(S.StudentID) AS numberOfStudents
    FROM Departments D
    LEFT JOIN Students S
        ON D.DepartmentID = S.DepartmentID
    GROUP BY D.DepartmentID, D.DepartmentName
    ORDER BY numberOfStudents DESC;