/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

public class CourseDept {
    private String title;
    private int creditHours;
    private String deptName;
    private String lectName;
    private int numberOfStudents;

    public CourseDept(String title, int creditHours, String deptName, String lectName, int numberOfStudents) {
        this.title = title;
        this.creditHours = creditHours;
        this.deptName = deptName;
        this.lectName = lectName;
        this.numberOfStudents = numberOfStudents;
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
