/* Once a student gets a grade of a course that he/she took */

-- A trigger to update the state of that enrollment according to the grade that this student gets
CREATE OR REPLACE TRIGGER update_course_state
    BEFORE UPDATE OF Grade ON Enrollments
    FOR EACH ROW
BEGIN
    IF :NEW.Grade = 'F' THEN
        :NEW.State := 'Failed';
    ELSE
        :NEW.State := 'Passed';
    END IF;
END;

-- A trigger to update the cgpa and total credit hours of that student
CREATE OR REPLACE TRIGGER update_cgpa_and_total_hours
    AFTER UPDATE OF Grade ON Enrollments
    FOR EACH ROW
DECLARE
    student_cgpa_before NUMBER(3, 2);
    student_credit_hours_before NUMBER(3);
    
    student_cgpa_after NUMBER(3, 2);
    student_credit_hours_after NUMBER(3);

    course_credits NUMBER(3);
    course_gpa NUMBER(2, 1);
BEGIN
    SELECT NVL(CGPA, 0) INTO student_cgpa_before FROM Students WHERE StudentID = :NEW.StudentID;
    SELECT NVL(TotalCreditHours, 0) INTO student_credit_hours_before FROM Students WHERE StudentID = :NEW.StudentID;

    SELECT CreditHours INTO course_credits FROM Courses WHERE CourseID = :NEW.CourseID;
    SELECT GPA INTO course_gpa FROM Grades WHERE Grade = :NEW.Grade;

    student_credit_hours_after := student_credit_hours_before + course_credits;
    student_cgpa_after := ((student_cgpa_before * student_credit_hours_before) + (course_credits * course_gpa)) / student_credit_hours_after;

    UPDATE Students
    SET CGPA = student_cgpa_after,
        TotalCreditHours = student_credit_hours_after
    WHERE StudentID = :NEW.StudentID;
END;

------------------------------------------------------------------------------------------------------------------------------------------------

/* To delete a student */

CREATE OR REPLACE TRIGGER delete_student
    BEFORE DELETE ON Students
    FOR EACH ROW
BEGIN
    DELETE FROM Enrollments WHERE StudentID = :OLD.StudentID;
END;