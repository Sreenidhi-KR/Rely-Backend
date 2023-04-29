package com.example.backend.Bean;

import javax.persistence.OrderColumn;

public class DocumentDetails {
    private Integer id;
    private String name;

    private boolean isAvailible;

    public DocumentDetails (Integer id,String name, boolean isAvailible){
        this.id=id;
        this.name=name;
        this.isAvailible=isAvailible;
    }

    public Integer getId() {
        return id;
    }

    public void setId (Integer id) {
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
