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