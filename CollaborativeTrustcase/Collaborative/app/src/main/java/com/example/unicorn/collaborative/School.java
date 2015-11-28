package com.example.unicorn.collaborative;

/**
 * Created by unicorn on 28.11.15.
 */
public class School {


    private String schoolName, schoolDesription;
    private int photoId;

    public School(String schoolName, String schoolDesription, int photoId) {
        this.schoolName = schoolName;
        this.schoolDesription = schoolDesription;
        this.photoId = photoId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolDesription() {
        return schoolDesription;
    }

    public void setSchoolDesription(String schoolDesription) {
        this.schoolDesription = schoolDesription;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }
}
