-- View to show information about students
CREATE OR REPLACE VIEW studentds_info AS
    SELECT S.StudentID, S.PersonalPhoto, S.FullName, D.DepartmentName, S.CGPA, S.TotalCreditHours
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

-- View to show information about lecturers, their departments, and number of courses they teach
CREATE OR REPLACE VIEW lects_info AS
    SELECT L.FullName, L.Salary, L.ContactEmail, L.OfficeRoom, D.DepartmentName, COUNT(C.CourseID) AS numberOfCourses
    FROM Lecturers L
    INNER JOIN Departments D
        ON L.DepartmentID = D.DepartmentID
    INNER JOIN Courses C
        ON L.LecturerID = C.LecturerID
    GROUP BY L.FullName, L.Salary, L.ContactEmail, L.OfficeRoom, D.DepartmentName
    ORDER BY L.FullName;

-- View to show the number of students enrolled in each department
CREATE OR REPLACE VIEW students_in_departments AS
    SELECT D.DepartmentName, COUNT(S.StudentID) AS numberOfStudents
    FROM Departments D
    LEFT JOIN Students S
        ON D.DepartmentID = S.DepartmentID
    GROUP BY S.DepartmentID, D.DepartmentName
    ORDER BY numberOfStudents DESC;