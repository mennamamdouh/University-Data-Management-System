/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

public class Course {
    private int courseId;
    private String title;
    private int creditHours;
    private int deptId;
    private int lectId;

    public Course(int courseId, String title, int creditHours, int deptId, int lectId) {
        this.courseId = courseId;
        this.title = title;
        this.creditHours = creditHours;
        this.deptId = deptId;
        this.lectId = lectId;
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

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public int getLectId() {
        return lectId;
    }

    public void setLectId(int lectId) {
        this.lectId = lectId;
    }
    
}
