-- View to show the number of courses related to each department
CREATE OR REPLACE VIEW courses_in_departments AS
    SELECT D.DepartmentName, COUNT(C.CourseID) AS number_of_courses
    FROM Departments D
    INNER JOIN Courses C
        ON D.DepartmentID = C.DepartmentID
    GROUP BY D.DepartmentName
    ORDER BY COUNT(C.CourseID) DESC;

-- View to show number of students registered in each semester
CREATE OR REPLACE VIEW students_per_semester AS
    SELECT E.Term, COUNT(S.StudentID) AS number_of_students
    FROM Enrollments E
    INNER JOIN Students S
        ON E.StudentID = S.StudentID
    GROUP BY E.Term
    ORDER BY COUNT(S.StudentID) DESC;

-- View to show the averga GPA for each course
CREATE OR REPLACE VIEW average_gpa_per_course AS
    SELECT C.Title, AVG(G.GPA) AS average_gpa
    FROM Enrollments E
    INNER JOIN Grades G
        ON E.Grade = G.Grade
    INNER JOIN Courses C
        ON E.CourseID = C.CourseID
    GROUP BY C.Title;

-- View to show the enrolled students of a specific course
CREATE OR REPLACE VIEW enrolled_students AS
    SELECT DISTINCT S.StudentID, S.PersonalPhoto, S.FullName, C.CourseID
    FROM Students S
    INNER JOIN Enrollments E
        ON S.StudentID = E.StudentID
    INNER JOIN Courses C
        ON E.CourseID = C.CourseID;