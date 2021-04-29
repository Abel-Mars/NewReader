package com.example.newreader.domain;

import java.util.Date;

public class BookComment {
    private int cid;
    private String title;
    private String comment;
    private String username;
    private double score;
    private String time;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    public double getScore() {
        return score;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setScore(double score) {
        this.score = score;
    }



}
