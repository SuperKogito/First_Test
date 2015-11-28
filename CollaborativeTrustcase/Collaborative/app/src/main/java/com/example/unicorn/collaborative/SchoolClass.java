package com.example.unicorn.collaborative;

/**
 * Created by unicorn on 28.11.15.
 */
public class SchoolClass {


    private String schoolClassName, schoolClassDescription;
    private int photoId;

    public SchoolClass(String schoolClassName, String schoolClassDescription, int photoId) {
        this.schoolClassName = schoolClassName;
        this.schoolClassDescription = schoolClassDescription;
        this.photoId = photoId;
    }

    public String getSchoolClassName() {
        return schoolClassName;
    }

    public void setSchoolClassName(String schoolClassName) {
        this.schoolClassName = schoolClassName;
    }

    public String getSchoolClassDescription() {
        return schoolClassDescription;
    }

    public void setSchoolClassDescription(String schoolClassDescription) {
        this.schoolClassDescription = schoolClassDescription;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }
}
