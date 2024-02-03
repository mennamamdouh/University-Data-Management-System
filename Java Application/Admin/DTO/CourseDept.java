/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

public class CourseDept {
    private int courseId;
    private String title;
    private int creditHours;
    private String deptName;
    private int lectId;
    private String lectName;
    private int numberOfStudents;

    public CourseDept(int courseId, String title, int creditHours, String deptName, int lectId, String lectName, int numberOfStudents) {
        this.courseId = courseId;
        this.title = title;
        this.creditHours = creditHours;
        this.deptName = deptName;
        this.lectId = lectId;
        this.lectName = lectName;
        this.numberOfStudents = numberOfStudents;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(int creditHours) {
        this.creditHours = creditHours;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public int getLectId() {
        return lectId;
    }

    public void setLectId(int lectId) {
        this.lectId = lectId;
    }
    
    public String getLectName() {
        return lectName;
    }

    public void setLectName(String lectName) {
        this.lectName = lectName;
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }
    
}
