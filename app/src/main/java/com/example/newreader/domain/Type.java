package com.example.newreader.domain;

import lombok.Data;

@Data
public class Type {

    private String type;

    public Type(){}

    public Type(String type){
        this.type=type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
