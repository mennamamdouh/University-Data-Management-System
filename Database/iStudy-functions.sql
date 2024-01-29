-- Function to calculate the student's cgpa

CREATE OR REPLACE FUNCTION calculate_cgpa(v_student_id IN NUMBER)
RETURN NUMBER
IS
    total_credit_hours NUMBER(3);
    sum_of_credits NUMBER(6, 3);
    student_cgpa NUMBER(3, 2);
BEGIN
    SELECT SUM(G.GPA * C.CreditHours) INTO sum_of_credits
    FROM Grades G
    INNER JOIN Enrollments E
        ON G.Grade = E.Grade
    INNER JOIN Courses C
        ON E.CourseID = C.CourseID
    WHERE E.StudentID = v_student_id AND E.Grade IS NOT NULL
    GROUP BY E.StudentID;

    SELECT SUM(C.CreditHours) INTO total_credit_hours
    FROM Courses C
    INNER JOIN Enrollments E
        ON C.CourseID = E.CourseID
    WHERE E.StudentID = v_student_id AND E.Grade IS NOT NULL;

    student_cgpa := sum_of_credits / total_credit_hours;

    RETURN student_cgpa;
END;