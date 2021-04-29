package com.example.newreader.domain;

import lombok.Data;

@Data
public class BookList {
    private int id;
    private String title;
    private String author;
    private String intro;
    private String type;

    public int getIdbook() {
        return id;
    }

    public void setIdbook(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString(){
        return "Book{"+"id"+id+"}";
    }
}
