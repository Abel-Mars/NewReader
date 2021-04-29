package com.example.newreader.bean;

import com.example.newreader.domain.BookList;

import java.util.ArrayList;
import java.util.List;

public class BodyBean extends ExampleBaseBean{

    private List<BookList> books;

    public List<BookList> getBooks() {
        return books;
    }

    public void setBooks(List<BookList> books) {
        this.books = books;
    }
}
