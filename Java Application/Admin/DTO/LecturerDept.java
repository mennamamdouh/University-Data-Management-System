/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

public class LecturerDept {
    private String fullName;
    private int salary;
    private String contactEmail;
    private String officeRoom;
    private String deptName;
    private int numberOfCourses;

    public LecturerDept(String fullName, int salary, String contactEmail, String officeRoom, String deptName, int numberOfCourses) {
        this.fullName = fullName;
        this.salary = salary;
        this.contactEmail = contactEmail;
        this.officeRoom = officeRoom;
        this.deptName = deptName;
        this.numberOfCourses = numberOfCourses;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getOfficeRoom() {
        return officeRoom;
    }

    public void setOfficeRoom(String officeRoom) {
        this.officeRoom = officeRoom;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public int getNumberOfCourses() {
        return numberOfCourses;
    }

    public void setNumberOfCourses(int numberOfCourses) {
        this.numberOfCourses = numberOfCourses;
    }
    
}
