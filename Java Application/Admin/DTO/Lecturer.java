/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

public class Lecturer {
    private int lectId;
    private String fullName;
    private int salary;
    private String contactEmail;
    private String officeRoom;
    private int deptId;

    public Lecturer(int lectId, String fullName, int salary, String contactEmail, String officeRoom, int deptId) {
        this.lectId = lectId;
        this.fullName = fullName;
        this.salary = salary;
        this.contactEmail = contactEmail;
        this.officeRoom = officeRoom;
        this.deptId = deptId;
    }

    public int getLectId() {
        return lectId;
    }

    public void setLectId(int lectId) {
        this.lectId = lectId;
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

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }
    
}
