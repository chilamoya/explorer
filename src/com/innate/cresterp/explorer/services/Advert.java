/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innate.cresterp.explorer.services;

import java.io.Serializable;

/**
 *
 * @author Tafadzwa
 */
public class Advert implements Serializable {

    private int id;
    private String name;
    private String description;
    private String image1;
    private String companyID ;
    private String company ;
    private String website ;
    private String mobile ;
    private String longitude ;
    private String latitude ;
    

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = "http://movert.cresterp.com/api/images/uploads/"+image1;
    }

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
 
    
    public String createHTML() {
        return "<p><strong>Title: </strong>" + getName() + "</p>"
                + ""
                + "<p><strong>Description:</strong> " + getDescription() + "</p>"
                + "<p><strong>Visual:</strong> </p>"
                + "<p> <img src=\""+getImage1()+"\" alt=\"some_text\"> </p>"
                + "<p><strong>Company:</strong> " + getCompany()+ "</p>"
                + "<p><strong>Website:</strong> " + getWebsite() + "</p>"
                + ""
                + "<p><strong>Mobile:</strong> " + getMobile() + "</p>"
            
                + "<p>&nbsp;</p>"
                + ""
                + "<p>&nbsp;</p>";

    }


}
