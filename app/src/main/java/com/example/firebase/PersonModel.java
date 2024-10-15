package com.example.firebase;

public class PersonModel {
    public String name,course,email,pic;

    public String mobileno;

    public PersonModel() {


    }

    public PersonModel(String name, String course, String email, String mobileno, String pic) {
        this.name = name;
        this.course = course;
        this.email = email;
        this.mobileno = mobileno;
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
