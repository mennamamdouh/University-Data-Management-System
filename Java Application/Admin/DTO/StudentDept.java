/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

public class StudentDept {
    private int studentId;
    private String fullName;
    private String personalPhoto;
    private double cgpa;
    private int totalCreditHours;
    private String deptName;
    private int numberOfStudents;

    public StudentDept(int studentId, String personalPhoto, String fullName,String deptName, double cgpa, int totalCreditHours) {
        this.studentId = studentId;
        this.personalPhoto = personalPhoto;
        this.fullName = fullName;
        this.deptName = deptName;
        this.cgpa = cgpa;
        this.totalCreditHours = totalCreditHours;
    }

    public StudentDept(int studentId, String deptName) {
        this.studentId = studentId;
        this.deptName = deptName;
    }

    public StudentDept(String deptName, int numberOfStudents) {
        this.deptName = deptName;
        this.numberOfStudents = numberOfStudents;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPersonalPhoto() {
        return personalPhoto;
    }

    public void setPersonalPhoto(String personalPhoto) {
        this.personalPhoto = personalPhoto;
    }

    public double getCgpa() {
        return cgpa;
    }

    public void setCgpa(double cgpa) {
        this.cgpa = cgpa;
    }

    public int getTotalCreditHours() {
        return totalCreditHours;
    }

    public void setTotalCreditHours(int totalCreditHours) {
        this.totalCreditHours = totalCreditHours;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }
    
}