# University-Data-Management-System | Database

## About the database ##

Our database stores information about the university's students, departments, grading system, the courses each department provides, the lecturers that are working on the courses, and and enrollments of the students on the courses. Besides, it provides some functionalities such as calculating each student's CGPA or GPA for a specific term or year.

## Database Design ##

### <u>Entities</u>: ###

- __Students__

  | <u>Student ID</u>  | Full Name | Email | Password | Gender | DoB | Personal Photo | Contact Number | CGPA | Total Credit Hours |
  | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |

- __Courses__

  | <u>Course ID</u> | Title | Credit Hours |
  | --- | --- | --- |

- __Grades__

  | <u>Grade</u> | GPA |
  | --- | --- |

- __Departments__

  | <u>Department ID</u> | Department Name |
  | --- | --- |

- __Lecturers__

  | <u>Lecturer ID</u> | Full Name | Salary | Contact Email | Office Room |
  | --- | --- | --- | --- | --- |

- __Notifications__

  | <u>Notification ID</u> | DateTime | Anomaly Type | Icon | Message |
  | --- | --- | --- | --- | --- |

---

### <u>Entity Relationship Diagram</u>: ###

<div align="center">
  <img src="database-design-ERD.png" alt="Image">
  <p><em>University Database Entity Relationship Diagram</em></p>
</div>

---

### <u>Relations</u>: ###

- __Student & Course__
    - A student can take `MANY` courses & a course can be assigned to `MANY` students.
    - A student `MAY` take the course & the course `MAY` be assigned to a student.
    - Attributes:
        - At which term (Spring - Autumn - Summer)
        - Year
        - State (In progress - Completed)
        - Grade (if completed - default `NULL`)
- __Course & Department__
    - A course is assigned to `ONE` department but the department can assign `MANY` courses.
    - A course `MUST` be assigned to a department but the department `MAY` have courses or not.
- __Student & Department__
    - A student enrolls in `ONE` department but the department can have `MANY` students.
    - A student `MUST` enroll in a department and the department `MAY` have students.
- __Lecturer & Department__
    - A lecturer works at `ONE` department but the department can have `MANY` lecturers.
    - A lecturer `MUST` be assigned to a department but the department `MAY` have lecturers or not.
- __Lecturer & Course__
    - A lecturer can teaches `MANY` courses and a course is taught by `ONE` lecturer.
    - A lecturer `MAY` teach a course but the course `MUST` be taught by a lecturer.

---

### <u>Mapping entities to actual tables</u>: ###

- __Departments__
    
    | <u>Department ID</u> | Department Name |
    | --- | --- |

- __Students__ (`enrolls`)
    
    | <u>Student ID</u> | Full Name | Email | Password | Gender | DoB | Personal Photo | Contact Number | CGPA | Total Credit Hours | *Department ID [FK]* |
    | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |

- __Lecturers__ (`works at`)
    
    | <u>Lecturer ID</u> | Full Name | Salary | Contact Email | Office Room | *Department ID [FK]* |
    | --- | --- | --- | --- | --- | --- |

- __Courses__ (`belongs` & `teaches`)
    
    | <u>Course ID</u> | Title | Credit Hours | *Department ID [FK]* | *Lecturer ID [FK]* |
    | --- | --- | --- | --- | --- |

- __Grades__
    
    | <u>Grade</u> | GPA |
    | --- | --- |

- __Enrollments__ (`takes`)
    
    | <u>Enrollment ID</u> | *Student ID [FK]* | *Course ID [FK]* | Term | Year | State | *Grade [FK]* |
    | --- | --- | --- | --- | --- | --- | --- |

---

## SQL & PL/SQL Implementation ##

The database with its tables that are described above are implemented on __Oracle DBMS__. Tables that their `PK` is a `NUMBER` data type has a `sequence-trigger pair` to be auto-incremental. In the following section I'll describe the order of implementation of the tables, the data types of each table's fields, and the constraints forced on each table.

Then, the `PL/SQL Program Units` of _procedures_, _triggers_, and _views_ will be explained in detail.

### <u>Tables and Attributes</u>: ###

1. __Departments__

    | _Field_ | _Type_ | _Null_ | _Key_ | _Default_ | _Extra_ |
    | --- | --- | --- | --- | --- | --- |
    | DepartmentID | `NUMBER(2)` | `NO` | `PK` |  | `Auto Increment` |
    | DepartmentName | `VARCHAR2(100)` |  |  | `NULL` |  |


2. __Students__

    | _Field_ | _Type_ | _Null_ | _Key_ | _Default_ | _Extra_ |
    | --- | --- | --- | --- | --- | --- |
    | StudentID | `NUMBER(5)` | `NO` | `PK` |  | `Auto Increment` |
    | FullName | `VARCHAR2(30)` |  |  | `NULL` |  |
    | Email | `VARCHAR2(50)` |  |  | `NULL` | `UNIQUE` |
    | Password | `VARCHAR2(20)` |  |  | `NULL` |  |
    | Gender | `VARCHAR2(10)` |  |  | `NULL` | `CHECK(’Female’, ‘Male’)` |
    | DoB | `DATE` |  |  | `NULL` |  |
    | PersonalPhoto | `VARCHAR2(150)` |  |  | `NULL` |  |
    | ContactNumber | `VARCHAR2(15)` |  |  | `NULL` |  |
    | CGPA | `NUMBER(3, 2)` |  |  | `NULL` |  |
    | TotalCreditHours | `NUMBER(3)` |  |  | `NULL` |  |
    | DepartmentID | `NUMBER(2)` | `NO` | `FK` |  |  |


3. __Lecturers__

    | _Field_ | _Type_ | _Null_ | _Key_ | _Default_ | _Extra_ |
    | --- | --- | --- | --- | --- | --- |
    | LecturerID | `NUMBER(5)` | `NO` | `PK` |  | `Auto Increment` |
    | FullName | `VARCHAR2(30)` |  |  | `NULL` |  |
    | Salary | `NUMBER(6)` |  |  | `NULL` |  |
    | ContactEmail | `VARCHAR2(50)` |  |  | `NULL` | `UNIQUE` |
    | OfficeRoom | `VARCHAR2(5)` |  |  | `NULL` | `UNIQUE` |
    | DepartmentID | `NUMBER(2)` | `NO` | `FK` |  |  |

   
4. __Courses__

    | _Field_ | _Type_ | _Null_ | _Key_ | _Default_ | _Extra_ |
    | --- | --- | --- | --- | --- | --- |
    | CourseID | `NUMBER(5)` | `NO` | `PK` |  | `Auto Increment` |
    | Title | `VARCHAR2(100)` |  |  | `NULL` |  |
    | CreditHours | `NUMBER(3)` |  |  | `NULL` |  |
    | DepartmentID | `NUMBER(2)` | `NO` | `FK` |  |  |
    | LecturerID | `NUMBER(5)` | `NO` | `FK` |  |  |


5. __Grades__

    | _Field_ | _Type_ | _Null_ | _Key_ | _Default_ | _Extra_ |
    | --- | --- | --- | --- | --- | --- |
    | Grade | `VARCHAR2(2)` | `NO` | `PK` |  |  |
    | GPA | `NUMBER(2, 1)` |  |  | `NULL` |  |


6. __Enrollments__

    | _Field_ | _Type_ | _Null_ | _Key_ | _Default_ | _Extra_ |
    | --- | --- | --- | --- | --- | --- |
    | EnrollmentID | `NUMBER(5)` | `NO` | `PK` |  | `Auto Increment` |
    | StudentID | `NUMBER(5)` | `NO` | `FK` |  |  |
    | CourseID | `NUMBER(5)` | `NO` | `FK` |  |  |
    | Term | `VARCHAR2(10)` |  |  | `NULL` | `CHECK(’Spring’, ‘Autumn’, ‘Summer’)` |
    | Year | `DATE` |  |  | `NULL` |  |
    | State | `VARCHAR2(20)` |  |  | `NULL` | `CHECK(’In progress’, ‘Passed’, ‘Failed’)` |
    | Grade | `VARCHAR2(2)` |  | `FK` | `NULL` |  |

7. __Notifications__

    | _Field_ | _Type_ | _Null_ | _Key_ | _Default_ | _Extra_ |
    | --- | --- | --- | --- | --- | --- |
    | NotificationID | `NUMBER(5)` | `NO` | `PK` |  | `Auto Increment` |
    | DateTime | `TIMESTAMP` |  |  | `NULL` |  |
    | AnomalyType | `VARCHAR2(100)` |  |  | `NULL` | `CHECK(’Disk Usage’, ‘CPU Usage’, ‘Memory Usage’)` |
    | Icon | `VARCHAR2(100)` |  |  | `NULL` |  |
    | Message | `VARCHAR2(255)` |  |  | `NULL` |  |

---

### <u>PL/SQL Program Units</u>: ###

Mainly, `PL/SQL Program Units` are used in this project to facilitate some calculations and actions to the admin. Besides, it makes the process of retreiving the data from the database easier at the backend of the application.

- __Procedures__

    There're 4 procedures implemented on the database:
    1. `modify_student_dept` &rarr; For changing a student's department.
    2. `update_lecturer_salary` &rarr; For updating a lecturer's salary.
    3. `change_course_lecturer` &rarr; For changing the lecturer of a course.

- __Triggers__

    There're 3 triggers implemented on the database
    1. `update_course_state` &rarr; This trigger is implemented on `Enrollments` table. It gets executed _before_ the grade of any student gets updated. It checks the new grade, if it's `F` then the state will be 'Failed', and otherwise the state will be 'Passed'.
    2. `update_cgpa_and_total_hours` &rarr; This trigger is implemented on the `Enrollments` table too but it gets executed _after_ the grade is updated. It mainly updates the total credit hours and CGPA of the student who gets a new grade.
    3. `delete_student` &rarr; This trigger is implemented on the `Students` table. It gets executed _before_ deleting any student from the database as the student already enrolls in some courses. So it cascades the process of deletion from the `Enrollments` table first, then the `Students` table.

- __Views__

    _I've divided the views of this project into 2 categories:_
    1. <u>Views to show some information about any entity</u>:

        a. `studentds_info` &rarr; It shows information about each student. His/her name, photo department, CGPA, and total credit hours he/she earned.
        
        b. `courses_info` &rarr; It shows some information about each course. Its title, credit hours, the department that provides this course, the lecturer who teached this course, and the number of students enrolls in this course.

        c. `lects_info` &rarr; It shows some information about each lecturer. His/her name, salary, contact email, office room, the department he/she works at, and the number of courses he/she teaches.

        d. `departments_info` &rarr; It shows each department's name, and the number of students that are enrolled in this department.

    2. <u>Views to show some summary statistics about the whole data</u>:
       
        a. `courses_in_departments` &rarr; It retreives each department's name, and the number of courses it provides.

        b. `students_per_semester` &rarr; It retreives each semester and how many students are registered in it.

        c. `average_gpa_per_course` &rarr; It retreives all the courses and the average GPA of all the students that took this course.

        d. `enrolled_students` &rarr; It retreives the names, and photos of enrolled students of each course.

---