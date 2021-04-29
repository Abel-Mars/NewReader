package com.example.newreader.bean;

import com.example.newreader.domain.BookList;

import java.util.ArrayList;
import java.util.List;

public class TitleBean extends ExampleBaseBean{
    public TitleBean() {
        this.titles = new ArrayList<>();
    }
    public List<BookList> getTitles() {
        return titles;
    }

    public void setTitles(List<BookList> titles) {
        this.titles = titles;
    }

    private List<BookList> titles;
}
