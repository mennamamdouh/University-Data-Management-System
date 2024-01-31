/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

public class StudentDept {
    private String fullName;
    private String personalPhoto;
    private double cgpa;
    private int totalCreditHours;
    private String deptName;

    public StudentDept(String fullName, String personalPhoto, double cgpa, int totalCreditHours, String deptName) {
        this.fullName = fullName;
        this.personalPhoto = personalPhoto;
        this.cgpa = cgpa;
        this.totalCreditHours = totalCreditHours;
        this.deptName = deptName;
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
    
}