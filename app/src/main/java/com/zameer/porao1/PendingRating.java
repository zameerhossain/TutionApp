package com.zameer.porao1;

public class PendingRating{
    private int myid;
    private int teachersId;
    private int ratingId;
    private TuitionRequest tuitionRequest;

    public PendingRating() {
    }

    public PendingRating(int myid, int teachersId, int ratingId, TuitionRequest tuitionRequest) {
        this.myid = myid;
        this.teachersId = teachersId;
        this.ratingId = ratingId;
        this.tuitionRequest = tuitionRequest;
    }

    public int getMyid() {
        return myid;
    }

    public void setMyid(int myid) {
        this.myid = myid;
    }

    public int getTeachersId() {
        return teachersId;
    }

    public void setTeachersId(int teachersId) {
        this.teachersId = teachersId;
    }

    public int getRatingId() {
        return ratingId;
    }

    public void setRatingId(int ratingId) {
        this.ratingId = ratingId;
    }

    public TuitionRequest getTuitionRequest() {
        return tuitionRequest;
    }

    public void setTuitionRequest(TuitionRequest tuitionRequest) {
        this.tuitionRequest = tuitionRequest;
    }

    @Override
    public String toString() {
        return "PendingRating{" +
                "myid=" + myid +
                ", teachersId=" + teachersId +
                ", ratingId=" + ratingId +
                ", tuitionRequest=" + tuitionRequest +
                '}';
    }

    public String prText(){

        User user = new User();
        for(User u : MainActivity.allUserList){
            if(u.getId()==teachersId){
                user = u;
                break;
            }
        }

        return  "Contact = " + tuitionRequest.getContact() +
                "\nCourseTitle = " + tuitionRequest.getCourseTitle() +
                "\nProblemDescription = " + tuitionRequest.getProblemDescription() +
                "\n\nUser info:"+
                "\nName = "+user.getName()+
                "\nPhone = " + user.getPhone() +
                "\nEmail = " + user.getEmail() +
                "\nStudent Rating = " + user.getStudent_rating() +
                "\nTeacher Rating = " + user.getTeacher_rating();

    }
}
