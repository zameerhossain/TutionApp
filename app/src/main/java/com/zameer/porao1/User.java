package com.zameer.porao1;

public class User {
    private String name;
    private String phone;
    private String email;
    private String password;
    private int id;

    private double student_rating;
    private double teacher_rating;
    private int student_rating_counter;
    private int teacher_rating_counter;


    public User() {
    }

    public User(String name, String phone, String email,String password) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        student_rating=3;
        teacher_rating=3;
        student_rating_counter=1;
        teacher_rating_counter=1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getStudent_rating() {
        return student_rating;
    }

    public void setStudent_rating(double student_rating) {
        this.student_rating = student_rating;
    }

    public double getTeacher_rating() {
        return teacher_rating;
    }

    public void setTeacher_rating(double teacher_rating) {
        this.teacher_rating = teacher_rating;
    }

    public int getStudent_rating_counter() {
        return student_rating_counter;
    }

    public void setStudent_rating_counter(int student_rating_counter) {
        this.student_rating_counter = student_rating_counter;
    }

    public int getTeacher_rating_counter() {
        return teacher_rating_counter;
    }

    public void setTeacher_rating_counter(int teacher_rating_counter) {
        this.teacher_rating_counter = teacher_rating_counter;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", id=" + id +
                ", student_rating=" + student_rating +
                ", teacher_rating=" + teacher_rating +
                ", student_rating_counter=" + student_rating_counter +
                ", teacher_rating_counter=" + teacher_rating_counter +
                '}';
    }

    public String toStringForShowInfo(){
        return "Name = " + name +
                "\nPhone = " + phone +
                "\nEmail = " + email +
                "\nStudent Rating = " + student_rating +
                "\nTeacher Rating = " + teacher_rating;
    }
}
