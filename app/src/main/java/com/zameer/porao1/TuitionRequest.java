package com.zameer.porao1;

public class TuitionRequest {
    //LatLng position;
    double lat;
    double lng;
    String contact;
    int userID;
    String courseTitle;
    String problemDescription;
    int counterid;


    public TuitionRequest() {
    }

    public TuitionRequest(double lat, double lng, String contact, String courseTitle, String problemDescription, int counterid,int userID) {
        this.lat = lat;
        this.lng = lng;
        this.contact = contact;
        this.courseTitle = courseTitle;
        this.problemDescription = problemDescription;
        this.counterid = counterid;
        this.userID = userID;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getProblemDescription() {
        return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    public int getCounterid() {
        return counterid;
    }

    public void setCounterid(int counterid) {
        this.counterid = counterid;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "TuitionRequest{" +
                "lat=" + lat +
                ", lng=" + lng +
                ", contact='" + contact + '\'' +
                ", userID=" + userID +
                ", courseTitle='" + courseTitle + '\'' +
                ", problemDescription='" + problemDescription + '\'' +
                ", counterid=" + counterid +
                '}';
    }
}
