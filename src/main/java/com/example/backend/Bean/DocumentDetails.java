package com.example.backend.Bean;

import javax.persistence.OrderColumn;

public class DocumentDetails {
    private int id;
    private String name;

    private boolean isAvailible;

    public DocumentDetails(int id,String name, boolean isAvailible){
        this.id=id;
        this.name=name;
        this.isAvailible=isAvailible;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public boolean getIsAvailible(){return  isAvailible;}

    public void setIsAvailible(boolean isAvailible){
        this.isAvailible=isAvailible;
    }

    public DocumentDetails(){ }

}
