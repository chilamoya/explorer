/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innate.cresterp.explorer.db;

/**
 *
 * @author Tafadzwa
 */
public class Company {

    String id;
    String name;
    String description;
    String category;
    String website;
    String mobile;
    String email;
    String address;
    String latitude;
    String longitude;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Company)) {
            return false;
        }
        Company other = (Company) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.innate.cresterp.explorer.db [ id=" + id + " ]";
    }

    public String getParameters() {
        return "?action=1&name=" + name + "&description=" + description + "&category=" + category
                + "&website=" + website + "&mobile=" + mobile + "&email=" + email + "&address=" + address
                + "&longitude=" + longitude + "&latitude=" + latitude + "&loclon=" + longitude.substring(0, 6)
                + "&loclat=" + latitude.substring(0, 6);
    }

    public String createHTML() {
        return "<p><strong>Name: </strong>" + getName() + "</p>"
                + ""
                + "<p><strong>Description:</strong> " + getDescription() + "</p>"
                + ""
                + "<p><strong>Website:</strong> " + getWebsite() + "</p>"
                + ""
                + "<p><strong>Mobile:</strong> " + getMobile() + "</p>"
                + ""
                + "<p><strong>Address:</strong> " + getAddress() + "</p>"
                + ""
                + "<p><strong>Address:</strong> " + getAddress() + "</p>"
                + ""
                + "<p>&nbsp;</p>"
                + ""
                + "<p>&nbsp;</p>";

    }

    public String mapTest() {
        return "<!DOCTYPE html>\n"
                + "<html>\n"
                + "  <head>\n"
                + "    <title>Google Maps JavaScript API v3 Example: Map Simple</title>\n"
                + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\">\n"
                + "    <meta charset=\"utf-8\">\n"
                + "    <style>\n"
                + "      html, body, #map_canvas {\n"
                + "        margin: 0;\n"
                + "        padding: 0;\n"
                + "        height: 100%;\n"
                + "      }\n"
                + "    </style>\n"
                + "    <script src=\"https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false\"></script>\n"
                + "    <script>\n"
                + "      var map;\n"
                + "      function initialize() {\n"
                + "        var mapOptions = {\n"
                + "          zoom: 8,\n"
                + "          center: new google.maps.LatLng(-34.397, 150.644),\n"
                + "          mapTypeId: google.maps.MapTypeId.ROADMAP\n"
                + "        };\n"
                + "        map = new google.maps.Map(document.getElementById('map_canvas'),\n"
                + "            mapOptions);\n"
                + "      }\n"
                + "\n"
                + "      google.maps.event.addDomListener(window, 'load', initialize);\n"
                + "    </script>\n"
                + "  </head>\n"
                + "  <body>\n"
                + "    <div id=\"map_canvas\"></div>\n"
                + "  </body>\n"
                + "</html>";
    }

}
