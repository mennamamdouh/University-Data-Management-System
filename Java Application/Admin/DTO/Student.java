/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.sql.Date;

public class Student {
    private int studentId;
    private String fullName;
    private String email;
    private String password;
    private String gender;
    private Date dateOfBirth;
    private String personalPhoto;
    private String contactNumber;
    private double cgpa;
    private int totalCreditHours;
    private int deptId;

    public Student(int studentId, String fullName, String email, String password, String gender, Date dateOfBirth, String personalPhoto, String contactNumber, double cgpa, int totalCreditHours, int deptId) {
        this.studentId = studentId;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.personalPhoto = personalPhoto;
        this.contactNumber = contactNumber;
        this.cgpa = cgpa;
        this.totalCreditHours = totalCreditHours;
        this.deptId = deptId;
    }

    public Student(String fullName, String gender, Date dateOfBirth, String personalPhoto, String contactNumber, int deptId) {
        this.fullName = fullName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.personalPhoto = personalPhoto;
        this.contactNumber = contactNumber;
        this.deptId = deptId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPersonalPhoto() {
        return personalPhoto;
    }

    public void setPersonalPhoto(String personalPhoto) {
        this.personalPhoto = personalPhoto;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
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

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }
    
}
