/* Some procedures to help the admin to modify information about students, lecturers, and courses */

CREATE OR REPLACE PROCEDURE modify_student_dept(v_student_id NUMBER, v_dept_id NUMBER)
IS
BEGIN
    UPDATE Students
    SET DepartmentID = v_dept_id
    WHERE StudentID = v_student_id;
END;

CREATE OR REPLACE PROCEDURE update_lecturer_salary(v_lecturer_id NUMBER, v_salary NUMBER)
IS
BEGIN
    UPDATE Lecturers
    SET Salary = v_salary
    WHERE LecturerID = v_lecturer_id;
END;

CREATE OR REPLACE PROCEDURE change_credit_hours(v_course_id NUMBER, v_credit_hours NUMBER)
IS
BEGIN
    UPDATE Courses
    SET CreditHours = v_credit_hours
    WHERE CourseID = v_course_id;
END;

CREATE OR REPLACE PROCEDURE change_course_lecturer(v_course_id NUMBER, v_lecturer_id NUMBER)
IS
BEGIN
    UPDATE Courses
    SET LecturerID = v_lecturer_id
    WHERE CourseID = v_course_id;
END;