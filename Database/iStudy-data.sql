/* Departments data */

INSERT INTO Departments(DepartmentName)
    VALUES('Data Management');
INSERT INTO Departments(DepartmentName)
    VALUES('System Administration');
INSERT INTO Departments(DepartmentName)
    VALUES('Cyber Security');
INSERT INTO Departments(DepartmentName)
    VALUES('Open Source');
INSERT INTO Departments(DepartmentName)
    VALUES('Artificial Intelligence');

------------------------------------------------------------------------------------------------------------------------------------------------

/* Students data */

-- Data Management Students

INSERT INTO Students(FullName, Email, Password, Gender, DoB, PersonalPhoto, ContactNumber, DepartmentID)
    VALUES('Mennatallah Mamdouh', 'mennamamdouh@gmail.com', 'mennamamdouh', 'Female', TO_DATE('2000-07-01', 'YYYY-mm-dd'), 'resources/menna.jpg', '0123456789', 1);
INSERT INTO Students(FullName, Email, Password, Gender, DoB, PersonalPhoto, ContactNumber, DepartmentID)
    VALUES('Shaun Banks', 'shaunbanks@gmail.com', 'shaunbanks', 'Male', NULL, 'resources/male.jpg', '0123456789', 1);
INSERT INTO Students(FullName, Email, Password, Gender, DoB, PersonalPhoto, ContactNumber, DepartmentID)
    VALUES('Keon Hammond', 'keonhammond@gmail.com', 'keonhammond', 'Male', NULL, 'resources/male.jpg', '0123456789', 1);
INSERT INTO Students(FullName, Email, Password, Gender, DoB, PersonalPhoto, ContactNumber, DepartmentID)
    VALUES('Todd Boyer', 'toddboyer@gmail.com', 'toddboyer', 'Male', NULL, 'resources/male.jpg', '0123456789', 1);
INSERT INTO Students(FullName, Email, Password, Gender, DoB, PersonalPhoto, ContactNumber, DepartmentID)
    VALUES('Brody Ochoa', 'brodyochoa@gmail.com', 'brodyochoa', 'Female', NULL, 'resources/female.jpg', '0123456789', 1);
INSERT INTO Students(FullName, Email, Password, Gender, DoB, PersonalPhoto, ContactNumber, DepartmentID)
    VALUES('Rose Brady', 'rosebrady@gmail.com', 'rosebrady', 'Female', NULL, 'resources/female.jpg', '0123456789', 1);


-- System Administration Students

INSERT INTO Students(FullName, Email, Password, Gender, DoB, PersonalPhoto, ContactNumber, DepartmentID)
    VALUES('Cheyenne Patton', 'cheyennepatton@gmail.com', 'cheyennepatton', 'Male', NULL, 'resources/male.jpg', '0123456789', 2);
INSERT INTO Students(FullName, Email, Password, Gender, DoB, PersonalPhoto, ContactNumber, DepartmentID)
    VALUES('Wayne Wood', 'waynewood@gmail.com', 'waynewood', 'Female', NULL, 'resources/female.jpg', '0123456789', 2);
INSERT INTO Students(FullName, Email, Password, Gender, DoB, PersonalPhoto, ContactNumber, DepartmentID)
    VALUES('Madelyn Kline', 'madelynkline@gmail.com', 'madelynkline', 'Female', NULL, 'resources/female.jpg', '0123456789', 2);
INSERT INTO Students(FullName, Email, Password, Gender, DoB, PersonalPhoto, ContactNumber, DepartmentID)
    VALUES('Dalia Mcdowell', 'daliamcdowell@gmail.com', 'daliamcdowell', 'Female', NULL, 'resources/female.jpg', '0123456789', 2);
INSERT INTO Students(FullName, Email, Password, Gender, DoB, PersonalPhoto, ContactNumber, DepartmentID)
    VALUES('Nicholas Anderson', 'nicholasanderson@gmail.com', 'nicholasanderson', 'Male', NULL, 'resources/male.jpg', '0123456789', 2);

-- Cyber Security Students

INSERT INTO Students(FullName, Email, Password, Gender, DoB, PersonalPhoto, ContactNumber, DepartmentID)
    VALUES('Marin Perkins', 'marinperkins@gmail.com', 'marinperkins', 'Male', NULL, 'resources/male.jpg', '0123456789', 3);
INSERT INTO Students(FullName, Email, Password, Gender, DoB, PersonalPhoto, ContactNumber, DepartmentID)
    VALUES('Milagros Rasmussen', 'milagrosrasmussen@gmail.com', 'milagrosrasmussen', 'Male', NULL, 'resources/male.jpg', '0123456789', 3);
INSERT INTO Students(FullName, Email, Password, Gender, DoB, PersonalPhoto, ContactNumber, DepartmentID)
    VALUES('Joy Lane', 'joylane@gmail.com', 'joylane', 'Male', NULL, 'resources/male.jpg', '0123456789', 3);
INSERT INTO Students(FullName, Email, Password, Gender, DoB, PersonalPhoto, ContactNumber, DepartmentID)
    VALUES('Yaretzi Wong', 'yaretziwong@gmail.com', 'yaretziwong', 'Male', NULL, 'resources/male.jpg', '0123456789', 3);

-- Open Source Students

INSERT INTO Students(FullName, Email, Password, Gender, DoB, PersonalPhoto, ContactNumber, DepartmentID)
    VALUES('Carlee Velez', 'carleevelez@gmail.com', 'carleevelez', 'Female', NULL, 'resources/female.jpg', '0123456789', 4);
INSERT INTO Students(FullName, Email, Password, Gender, DoB, PersonalPhoto, ContactNumber, DepartmentID)
    VALUES('Isaac Branch', 'isaacbranch@gmail.com', 'isaacbranch', 'Male', NULL, 'resources/male.jpg', '0123456789', 4);
INSERT INTO Students(FullName, Email, Password, Gender, DoB, PersonalPhoto, ContactNumber, DepartmentID)
    VALUES('Omar Burch', 'omarburch@gmail.com', 'omarburch', 'Male', NULL, 'resources/male.jpg', '0123456789', 4);
INSERT INTO Students(FullName, Email, Password, Gender, DoB, PersonalPhoto, ContactNumber, DepartmentID)
    VALUES('Quinton Quinn', 'quintonquinn@gmail.com', 'quintonquinn', 'Female', NULL, 'resources/female.jpg', '0123456789', 4);
INSERT INTO Students(FullName, Email, Password, Gender, DoB, PersonalPhoto, ContactNumber, DepartmentID)
    VALUES('Keegan Neal', 'keeganneal@gmail.com', 'keeganneal', 'Female', NULL, 'resources/female.jpg', '0123456789', 4);
INSERT INTO Students(FullName, Email, Password, Gender, DoB, PersonalPhoto, ContactNumber, DepartmentID)
    VALUES('Amina Brooks', 'aminabrooks@gmail.com', 'aminabrooks', 'Female', NULL, 'resources/female.jpg', '0123456789', 4);

-- Artificial Intelligence Students

INSERT INTO Students(FullName, Email, Password, Gender, DoB, PersonalPhoto, ContactNumber, DepartmentID)
    VALUES('Grant Gutierrez', 'grantgutierrez@gmail.com', 'grantgutierrez', 'Male', NULL, 'resources/male.jpg', '0123456789', 5);
INSERT INTO Students(FullName, Email, Password, Gender, DoB, PersonalPhoto, ContactNumber, DepartmentID)
    VALUES('Deangelo Byrd', 'deangelobyrd@gmail.com', 'deangelobyrd', 'Male', NULL, 'resources/male.jpg', '0123456789', 5);
INSERT INTO Students(FullName, Email, Password, Gender, DoB, PersonalPhoto, ContactNumber, DepartmentID)
    VALUES('Angelo Blankenship', 'angeloblankenship@gmail.com', 'angeloblankenship', 'Male', NULL, 'resources/male.jpg', '0123456789', 5);
INSERT INTO Students(FullName, Email, Password, Gender, DoB, PersonalPhoto, ContactNumber, DepartmentID)
    VALUES('Livia Smith', 'liviasmith@gmail.com', 'liviasmith', 'Female', NULL, 'resources/female.jpg', '0123456789', 5);
INSERT INTO Students(FullName, Email, Password, Gender, DoB, PersonalPhoto, ContactNumber, DepartmentID)
    VALUES('Rodolfo Lang', 'rodolfolang@gmail.com', 'rodolfolang', 'Male', NULL, 'resources/male.jpg', '0123456789', 5);
INSERT INTO Students(FullName, Email, Password, Gender, DoB, PersonalPhoto, ContactNumber, DepartmentID)
    VALUES('Joe Rios', 'joerios@gmail.com', 'joerios', 'Male', NULL, 'resources/male.jpg', '0123456789', 5);
INSERT INTO Students(FullName, Email, Password, Gender, DoB, PersonalPhoto, ContactNumber, DepartmentID)
    VALUES('Magdalena Barton', 'magdalenabarton@gmail.com', 'magdalenabarton', 'Female', NULL, 'resources/female.jpg', '0123456789', 5);

------------------------------------------------------------------------------------------------------------------------------------------------

/* Lecturers data */

INSERT INTO Lecturers(FullName, Salary, ContactEmail, OfficeRoom, DepartmentID)
    VALUES('Axel Salazar', 5000, 'axelsalazar@gmail.com', 'C1', 3); -- computer networks -> cyber
INSERT INTO Lecturers(FullName, Salary, ContactEmail, OfficeRoom, DepartmentID)
    VALUES('Mark Massey', 3000, 'markmassey@gmail.com', 'S1', 2); -- redhat & bash -> system
INSERT INTO Lecturers(FullName, Salary, ContactEmail, OfficeRoom, DepartmentID)
    VALUES('Dale Ward', 5000, 'daleward@gmail.com', 'D1', 1); -- oracle -> data
INSERT INTO Lecturers(FullName, Salary, ContactEmail, OfficeRoom, DepartmentID)
    VALUES('Davion Kirby', 10000, 'davionkirby@gmail.com', 'O1', 4); -- OOP -> open source
INSERT INTO Lecturers(FullName, Salary, ContactEmail, OfficeRoom, DepartmentID)
    VALUES('Annabella Liu', 10000, 'annabellaliu@gmail.com', 'D2', 1); -- database -> data
INSERT INTO Lecturers(FullName, Salary, ContactEmail, OfficeRoom, DepartmentID)
    VALUES('Yesenia Parker', 10000, 'yeseniaparker@gmail.com', 'A1', 5); -- machine learning & linear algebra -> AI

------------------------------------------------------------------------------------------------------------------------------------------------

/* Courses data */

INSERT INTO Courses(Title, CreditHours, DepartmentID, LecturerID)
    VALUES('Computer Networks', 2, 2, 1);
INSERT INTO Courses(Title, CreditHours, DepartmentID, LecturerID)
    VALUES('Object Oriented Programming', 4, 1, 4);
INSERT INTO Courses(Title, CreditHours, DepartmentID, LecturerID)
    VALUES('Oracle PL/SQL', 4, 1, 3);
INSERT INTO Courses(Title, CreditHours, DepartmentID, LecturerID)
    VALUES('System Admin - Redhat', 3, 2, 2);
INSERT INTO Courses(Title, CreditHours, DepartmentID, LecturerID)
    VALUES('Bash Scripting', 2, 1, 2);
INSERT INTO Courses(Title, CreditHours, DepartmentID, LecturerID)
    VALUES('Database Fundamentals', 3, 1, 5);
INSERT INTO Courses(Title, CreditHours, DepartmentID, LecturerID)
    VALUES('Machine Learning', 3, 5, 6);
INSERT INTO Courses(Title, CreditHours, DepartmentID, LecturerID)
    VALUES('Linear Algebra', 3, 5, 6);
INSERT INTO Courses(Title, CreditHours, DepartmentID, LecturerID)
    VALUES('Data Warehousing', 4, 1, 5);

------------------------------------------------------------------------------------------------------------------------------------------------

/* Grades data */

INSERT INTO Grades
    VALUES('A', 4);
INSERT INTO Grades
    VALUES('A-', 3.7);
INSERT INTO Grades
    VALUES('B+', 3.3);
INSERT INTO Grades
    VALUES('B', 3);
INSERT INTO Grades
    VALUES('B-', 2.7);
INSERT INTO Grades
    VALUES('C+', 2.3);
INSERT INTO Grades
    VALUES('C', 2);
INSERT INTO Grades
    VALUES('C-', 1.7);
INSERT INTO Grades
    VALUES('D+', 1.3);
INSERT INTO Grades
    VALUES('D', 1);
INSERT INTO Grades
    VALUES('F', 0);

------------------------------------------------------------------------------------------------------------------------------------------------

/* Enrollments data */

INSERT INTO Enrollments(StudentID, CourseID, Term, Year, State, Grade)
    VALUES(1, 1, 'Summer', TO_DATE('2023', 'YYYY'), 'Passed', 'B');
INSERT INTO Enrollments(StudentID, CourseID, Term, Year, State, Grade)
    VALUES(1, 3, 'Autumn', TO_DATE('2023', 'YYYY'), 'Passed', 'A');
INSERT INTO Enrollments(StudentID, CourseID, Term, Year, State, Grade)
    VALUES(1, 5, 'Autumn', TO_DATE('2023', 'YYYY'), 'Passed', 'B+');
INSERT INTO Enrollments(StudentID, CourseID, Term, Year, State, Grade)
    VALUES(1, 6, 'Autumn', TO_DATE('2023', 'YYYY'), 'Passed', 'B+');
INSERT INTO Enrollments(StudentID, CourseID, Term, Year, State)
    VALUES(1, 9, 'Autumn', TO_DATE('2023', 'YYYY'), 'In progress');
INSERT INTO Enrollments(StudentID, CourseID, Term, Year, State, Grade)
    VALUES(1, 2, 'Spring', TO_DATE('2024', 'YYYY'), 'Passed', 'A-');

------------------------------------------------------------------------------------------------------------------------------------------------