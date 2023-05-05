package com.example.backend.Bean;

import javax.persistence.OrderColumn;

public class DocumentDetails {
    private Integer id;
    private String name;

    private boolean isAvailible;

    private String document_type;

    public DocumentDetails (Integer id,String name, boolean isAvailible,String document_type){
        this.id=id;
        this.name=name;
        this.isAvailible=isAvailible;
        this.document_type=document_type;
    }

    public Integer getId() {
        return id;
    }

    public String getDocument_type(){return this.document_type;}

    public void setDocument_type(String document_type){this.document_type=document_type;}

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
